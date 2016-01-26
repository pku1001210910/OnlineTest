package com.fivestars.websites.onlinetest.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fivestars.websites.onlinetest.constant.CategoryConst;
import com.fivestars.websites.onlinetest.constant.QuizConst;
import com.fivestars.websites.onlinetest.dao.QuizCategoryDAO;
import com.fivestars.websites.onlinetest.dao.QuizDAO;
import com.fivestars.websites.onlinetest.dao.QuizSubjectDAO;
import com.fivestars.websites.onlinetest.dao.SubjectItemDAO;
import com.fivestars.websites.onlinetest.model.Quiz;
import com.fivestars.websites.onlinetest.model.QuizCategory;
import com.fivestars.websites.onlinetest.model.QuizSubject;
import com.fivestars.websites.onlinetest.model.SubjectItem;
import com.fivestars.websites.onlinetest.service.QuizService;

@Transactional
@Service("quizService")
public class QuizServiceImpl implements QuizService {

	private static final Logger LOGGER = LoggerFactory.getLogger(QuizServiceImpl.class);
	
	@Autowired
	private QuizDAO quizDao;
	@Autowired
	private QuizSubjectDAO subjectDao;
	@Autowired
	private SubjectItemDAO itemDao;
	@Autowired
	private QuizCategoryDAO categoryDao;
	
	@Override
	public Integer createQuiz(Quiz quiz) {
		Integer quizId =  quizDao.save(quiz);
		LOGGER.info("[QuizService]Successfully created quiz of id " + quizId);
		return quizId;
	}

	@Override
	public List<Quiz> loadAllQuiz() {
		return quizDao.listAll();
	}
	

	@Override
	public List<Quiz> loadAllQuizTitles() {
		List<Quiz> all = quizDao.listAll();
		List<Quiz> ret = new ArrayList<>();
		for(Quiz each : all) {
			ret.add(new Quiz(each));
		}
		return ret;
	}
	
	@Override
	public List<Quiz> loadAllSubmittedQuiz(Integer categoryId, String userName, int curPageNum, int pageSize) {
		// TODO consider user name 
		DetachedCriteria resultCriteria = DetachedCriteria.forClass(Quiz.class);
		Criterion statusEq = Restrictions.eq("status", QuizConst.STATUS_SUBMITTED);
		
		resultCriteria.add(statusEq);
		if(categoryId != CategoryConst.TYPE_ALL) {
			Criterion categoryEq = Restrictions.eq("category", categoryId);
			resultCriteria.add(categoryEq);
		
		}
		resultCriteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		resultCriteria.setFetchMode("subjectItems", FetchMode.SELECT);
		resultCriteria.setFetchMode("quizSubjects", FetchMode.SELECT); 
		List<Quiz> quizList = quizDao.pagedQuery(resultCriteria, curPageNum, pageSize);
		LOGGER.info("[QuizService]Successfully load all submitted quiz, categoryId = {}, useName = {}", categoryId, userName);
		return quizList;
	}

	@Override
	public void deleteQuiz(Integer quizId) {
		quizDao.delete(quizId);
		LOGGER.info("[QuizService]Successfully deleted quiz of id " + quizId);
	}

	@Override
	public void updateQuiz(Quiz quiz) {
		quizDao.saveOrUpdate(quiz);
		LOGGER.info("[QuizService]Successfully updated quiz of id " + quiz.getQuizId());
	}

	@Override
	public Quiz loadQuizById(Integer quizId) {
		return quizDao.get(quizId);
	}

	@Override
	public void addSubjectToQuiz(Integer quizId, QuizSubject subject) {
		Quiz quiz = quizDao.get(quizId);
		Set<QuizSubject> subjectSet = quiz.getQuizSubjects();
		// maintain the subject order
		int maxOrder = -1;
		for (QuizSubject subjectInSet : subjectSet) {
			if (subjectInSet.getSubjectOrder() > maxOrder) {
				maxOrder = subjectInSet.getSubjectOrder();
			}
		}
		subject.setSubjectOrder(maxOrder + 1);
		subjectSet.add(subject);
		quizDao.saveOrUpdate(quiz);
	}

	@Override
	public void addSubjectsToQuiz(Integer quizId, List<QuizSubject> subjectList) {
		Quiz quiz = quizDao.get(quizId);
		Set<QuizSubject> subjectSet = quiz.getQuizSubjects();
		// maintain the subject order
		int maxOrder = -1;
		for (QuizSubject subjectInSet : subjectSet) {
			if (subjectInSet.getSubjectOrder() > maxOrder) {
				maxOrder = subjectInSet.getSubjectOrder();
			}
		}
		for (QuizSubject subjectInList : subjectList) {
			subjectInList.setSubjectOrder(++maxOrder);
		}
		subjectSet.addAll(subjectList);
		quizDao.saveOrUpdate(quiz);
	}

	@Override
	public void deleteSubjectFromQuiz(Integer quizId, Integer subjectId) {
		QuizSubject subject = subjectDao.get(subjectId);
		if (subject == null) {
			LOGGER.warn("[QuizService]Cannot delete subject with subjectId " + subjectId + " because it does not exist.");
			return;
		}
		int deleteOrder = subject.getSubjectOrder();
		
		// first rearrange other subjects order in the same quiz
		Quiz quiz = quizDao.load(quizId);
		Set<QuizSubject> subjectSet = quiz.getQuizSubjects();
		for (QuizSubject subjectInDB : subjectSet) {
			if (subjectInDB.getSubjectOrder() > deleteOrder) {
				subjectInDB.setSubjectOrder(subjectInDB.getSubjectOrder() - 1);
			}
		}
		subjectSet.remove(subject);
		quizDao.saveOrUpdate(quiz);
		// then delete subject, belonging subjectItems will be deleted automatically because of cascade
		subjectDao.delete(subjectId);
	}

	@Override
	public void insertSubjectAt(Integer quizId, QuizSubject subject, int order) {
		Quiz quiz = quizDao.get(quizId);
		Set<QuizSubject> subjectSet = quiz.getQuizSubjects();
		// maintain the subject order
		for (QuizSubject subjectInSet : subjectSet) {
			if (subjectInSet.getSubjectOrder() >= order) {
				subjectInSet.setSubjectOrder(subjectInSet.getSubjectOrder() + 1);
			}
		}
		subject.setSubjectOrder(order);
		subjectSet.add(subject);
		quizDao.saveOrUpdate(quiz);
	}

	@Override
	public void shiftSubjectUp(Integer quizId, Integer subjectId) {
		Quiz quiz = quizDao.load(quizId);
		QuizSubject subjectToShift = subjectDao.get(subjectId);
		if (subjectToShift == null) {
			LOGGER.warn("[QuizService]Cannot shift subject with subjectId " + subjectId + " because it does not exist.");
			return;
		}
		int order = subjectToShift.getSubjectOrder();
		subjectToShift.setSubjectOrder(--order);
		for (QuizSubject subject : quiz.getQuizSubjects()) {
			if (subject.getSubjectOrder().equals(order) && !subject.getSubjectId().equals(subjectId)) {
				subject.setSubjectOrder(++order);
				break;
			}
		}
		quizDao.saveOrUpdate(quiz);
	}

	@Override
	public void shiftSubjectDown(Integer quizId, Integer subjectId) {
		Quiz quiz = quizDao.load(quizId);
		QuizSubject subjectToShift = subjectDao.get(subjectId);
		if (subjectToShift == null) {
			LOGGER.warn("[QuizService]Cannot shift subject with subjectId " + subjectId + " because it does not exist.");
			return;
		}
		int order = subjectToShift.getSubjectOrder();
		subjectToShift.setSubjectOrder(++order);
		for (QuizSubject subject : quiz.getQuizSubjects()) {
			if (subject.getSubjectOrder().equals(order)  && !subject.getSubjectId().equals(subjectId)) {
				subject.setSubjectOrder(--order);
				break;
			}
		}
		quizDao.saveOrUpdate(quiz);
	}

	@Override
	public void updateQuizSubject(QuizSubject subject) {
		subjectDao.saveOrUpdate(subject);
		LOGGER.info("[QuizService]Successfully updated subject of id " + subject.getSubjectId());
	}

	@Override
	public QuizSubject loadQuizSubjectById(Integer subjectId) {
		return subjectDao.get(subjectId);
	}

	@Override
	public List<QuizSubject> loadAllSubjects() {
		return subjectDao.listAll();
	}

	@Override
	public void addItemToSubject(Integer subjectId, SubjectItem item) {
		QuizSubject subject = subjectDao.get(subjectId);
		Set<SubjectItem> itemSet = subject.getSubjectItems();
		// maintain the item order
		int maxOrder = -1;
		for (SubjectItem itemInSet : itemSet) {
			if (itemInSet.getItemOrder() > maxOrder) {
				maxOrder = itemInSet.getItemOrder();
			}
		}
		item.setItemOrder(maxOrder + 1);
		itemSet.add(item);
		subjectDao.saveOrUpdate(subject);
	}

	@Override
	public void addItemsToQuiz(Integer subjectId, List<SubjectItem> items) {
		QuizSubject subject = subjectDao.get(subjectId);
		Set<SubjectItem> itemSet = subject.getSubjectItems();
		// maintain the item order
		int maxOrder = -1;
		for (SubjectItem itemInSet : itemSet) {
			if (itemInSet.getItemOrder() > maxOrder) {
				maxOrder = itemInSet.getItemOrder();
			}
		} 
		for (SubjectItem itemInList : items) {
			itemInList.setItemOrder(++maxOrder);
		}
		itemSet.addAll(items);
		subjectDao.saveOrUpdate(subject);
	}

	@Override
	public void deleteItemFromSubject(Integer subjectId, Integer itemId) {
		SubjectItem item = itemDao.get(itemId);
		if (item == null) {
			LOGGER.warn("[QuizService]Cannot delete item with itemId " + itemId + " because it does not exist.");
			return;
		}
		int deleteOrder = item.getItemOrder();
		
		// first rearrange other items order in the same subject
		QuizSubject subject = subjectDao.load(subjectId);
		Set<SubjectItem> itemSet = subject.getSubjectItems();
		for (SubjectItem itemInDB : itemSet) {
			if (itemInDB.getItemOrder() > deleteOrder) {
				itemInDB.setItemOrder(itemInDB.getItemOrder() - 1);
			}
		}
		itemSet.remove(item);
		subjectDao.saveOrUpdate(subject);
		// then delete item
		itemDao.delete(itemId);
	}

	@Override
	public void insertItemAt(Integer subjectId, SubjectItem item, int order) {
		QuizSubject subject = subjectDao.get(subjectId);
		Set<SubjectItem> itemSet = subject.getSubjectItems();
		// maintain the item order
		for (SubjectItem itemInSet : itemSet) {
			if (itemInSet.getItemOrder() >= order) {
				itemInSet.setItemOrder(itemInSet.getItemOrder() + 1);
			}
		}
		item.setItemOrder(order);
		itemSet.add(item);
		subjectDao.saveOrUpdate(subject);
	}

	@Override
	public void shiftItemUp(Integer subjectId, Integer itemId) {
		QuizSubject subject = subjectDao.load(subjectId);
		SubjectItem itemToShift = itemDao.load(itemId);
		if (itemToShift == null) {
			LOGGER.warn("[QuizService]Cannot shift item with itemId " + itemId + " because it does not exist.");
			return;
		}
		int order = itemToShift.getItemOrder();
		for (SubjectItem item : subject.getSubjectItems()) {
			if (item.getItemOrder().equals(order - 1)) {
				item.setItemOrder(order);
				break;
			}
		}
		itemToShift.setItemOrder(order - 1);
		subjectDao.saveOrUpdate(subject);
	}

	@Override
	public void shiftItemDown(Integer subjectId, Integer itemId) {
		QuizSubject subject = subjectDao.load(subjectId);
		SubjectItem itemToShift = itemDao.load(itemId);
		if (itemToShift == null) {
			LOGGER.warn("[QuizService]Cannot shift item with itemId " + itemId + " because it does not exist.");
			return;
		}
		int order = itemToShift.getItemOrder();
		for (SubjectItem item : subject.getSubjectItems()) {
			if (item.getItemOrder().equals(order + 1)) {
				item.setItemOrder(order);
				break;
			}
		}
		itemToShift.setItemOrder(order + 1);
		subjectDao.saveOrUpdate(subject);
	}

	@Override
	public SubjectItem loadSubjectItemById(Integer itemId) {
		return itemDao.get(itemId);
	}

	@Override
	public void updateSubjectItem(SubjectItem item) {
		itemDao.saveOrUpdate(item);
		LOGGER.info("[QuizService]Successfully updated item of id " + item.getItemId());
	}

	@Override
	public List<QuizCategory> getAllQuizCategories() {
		return categoryDao.listAll();
	}

	@Override
	public List<Quiz> getQuizByCategory(Integer categoryId) {
		DetachedCriteria categoryCriteria = DetachedCriteria.forClass(Quiz.class);
		Criterion categoryEq = Restrictions.eq("category", categoryId);
		categoryCriteria.add(categoryEq);
		categoryCriteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return quizDao.listSome(categoryCriteria);
	}
	
	@Override
	public List<Quiz> getSubmittedQuizByCategory(Integer categoryId, Integer curPageNum, Integer pageSize) {
		DetachedCriteria categoryCriteria = DetachedCriteria.forClass(Quiz.class);
		Criterion categoryEq = Restrictions.eq("category", categoryId);
		Criterion submittedEq = Restrictions.eq("status", QuizConst.STATUS_SUBMITTED);
		categoryCriteria.add(categoryEq);
		categoryCriteria.add(submittedEq);
		categoryCriteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return quizDao.pagedQuery(categoryCriteria, curPageNum, pageSize);
	}
	

	@Override
	public Integer createQuizCategory(QuizCategory category) {
		Integer categoryId = categoryDao.save(category);
		LOGGER.info("[QuizService]Successfully created quiz category of id " + categoryId);
		return categoryId;
	}

	@Override
	public void deleteQuizCategory(Integer categoryId) {
		categoryDao.delete(categoryId);
		LOGGER.info("[QuizService]Successfully deleted quiz category of id " + categoryId);
	}

	@Override
	public QuizCategory getQuizCategoryById(Integer categoryId) {
		return categoryDao.get(categoryId);
	}
	
	@Override
	public QuizCategory loadQuizCategoryById(Integer quizCategoryId) {
		QuizCategory quizCategory = categoryDao.get(quizCategoryId);
		LOGGER.info("[QuizService]Successfully load quizCategory of id " + quizCategoryId);
		return quizCategory;
	}
	
	@Override
	public Integer getAllQuizSize() {
		Integer allQuizSize = quizDao.countAll();
		LOGGER.info("[QuizService]Successfully load all quiz size");
		return allQuizSize;
	}
	
	@Override
	public Integer getAllSubmittedQuizSize(Integer categoryId, String userName) {
		// TODO consider userName
		DetachedCriteria resultCriteria = DetachedCriteria.forClass(Quiz.class);
		Criterion statusEq = Restrictions.eq("status", QuizConst.STATUS_SUBMITTED);
		resultCriteria.add(statusEq);
		if(categoryId != CategoryConst.TYPE_ALL) {
			Criterion categoryEq = Restrictions.eq("category", categoryId);
			resultCriteria.add(categoryEq);
		}
		resultCriteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		Integer allQuizSize = quizDao.countSome(resultCriteria);
		return allQuizSize;
	}

	@Override
	public Integer getAllSubmittedQuizSizeByCategoryId(Integer categoryId) {
		DetachedCriteria resultCriteria = DetachedCriteria.forClass(Quiz.class);
		Criterion statusEq = Restrictions.eq("status", QuizConst.STATUS_SUBMITTED);
		Criterion categoryEq = Restrictions.eq("category", categoryId);
		resultCriteria.add(statusEq);
		resultCriteria.add(categoryEq);
		resultCriteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		Integer allQuizSize = quizDao.countSome(resultCriteria);
		return allQuizSize;
	}
}

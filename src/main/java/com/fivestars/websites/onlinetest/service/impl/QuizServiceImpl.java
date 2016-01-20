package com.fivestars.websites.onlinetest.service.impl;

import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	public Integer createQuizCategory(QuizCategory category) {
		Integer categoryId = categoryDao.save(category);
		LOGGER.info("[QuizService]Successfully created quiz category of id " + categoryId);
		return null;
	}

	@Override
	public void deleteQuizCategory(Integer categoryId) {
		categoryDao.delete(categoryId);
		LOGGER.info("[QuizService]Successfully deleted quiz category of id " + categoryId);
	}
	
}

package com.fivestars.websites.onlinetest.service.impl;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fivestars.websites.onlinetest.dao.QuizDAO;
import com.fivestars.websites.onlinetest.dao.QuizSubjectDAO;
import com.fivestars.websites.onlinetest.dao.SubjectItemDAO;
import com.fivestars.websites.onlinetest.model.Quiz;
import com.fivestars.websites.onlinetest.model.QuizSubject;
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
		QuizSubject subjectToShift = subjectDao.load(subjectId);
		int order = subjectToShift.getSubjectOrder();
		subjectToShift.setSubjectOrder(--order);
		for (QuizSubject subject : quiz.getQuizSubjects()) {
			if (subject.getSubjectOrder().equals(order)) {
				subject.setSubjectOrder(++order);
				break;
			}
		}
		quizDao.saveOrUpdate(quiz);
	}

	@Override
	public void shiftSubjectDown(Integer quizId, Integer subjectId) {
		Quiz quiz = quizDao.load(quizId);
		QuizSubject subjectToShift = subjectDao.load(subjectId);
		int order = subjectToShift.getSubjectOrder();
		subjectToShift.setSubjectOrder(++order);
		for (QuizSubject subject : quiz.getQuizSubjects()) {
			if (subject.getSubjectOrder().equals(order)) {
				subject.setSubjectOrder(--order);
				break;
			}
		}
		quizDao.saveOrUpdate(quiz);
	}

}

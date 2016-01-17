package com.fivestars.websites.onlinetest.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fivestars.websites.onlinetest.dao.QuizDAO;
import com.fivestars.websites.onlinetest.model.Quiz;
import com.fivestars.websites.onlinetest.model.QuizSubject;
import com.fivestars.websites.onlinetest.service.QuizService;

@Transactional
@Service("quizService")
public class QuizServiceImpl implements QuizService {

	private static final Logger LOGGER = LoggerFactory.getLogger(QuizServiceImpl.class);
	
	@Autowired
	private QuizDAO quizDao;
	
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
		List<QuizSubject> subjectList = quiz.getQuizSubjects();
		subjectList.add(subject);
		updateSubjectsInQuiz(quiz, subjectList);
	}

	@Override
	public void insertSubjectBefore(Integer quizId, Integer subjectId, QuizSubject subject) {
		Quiz quiz = quizDao.get(quizId);
		List<QuizSubject> newList = new ArrayList<>();
		for (QuizSubject subjectInDB : quiz.getQuizSubjects()) {
			if (subjectInDB.getSubjectId().equals(subjectId)) {
				newList.add(subject);
			}
			newList.add(subjectInDB);
		}
		if (quiz.getQuizSubjects().size() + 1 == newList.size()) {
			updateSubjectsInQuiz(quiz, newList);
		} else {
			LOGGER.warn("[QuizService]Cannot find subject " + subjectId + " in quiz " + quizId);
		}
	}

	@Override
	public void insertSubjectAfter(Integer quizId, Integer subjectId, QuizSubject subject) {
		Quiz quiz = quizDao.get(quizId);
		List<QuizSubject> newList = new ArrayList<>();
		for (QuizSubject subjectInDB : quiz.getQuizSubjects()) {
			newList.add(subjectInDB);
			if (subjectInDB.getSubjectId().equals(subjectId)) {
				newList.add(subject);
			}
		}
		if (quiz.getQuizSubjects().size() + 1 == newList.size()) {
			updateSubjectsInQuiz(quiz, newList);
		} else {
			LOGGER.warn("[QuizService]Cannot find subject " + subjectId + " in quiz " + quizId);
		}
	}

	@Override
	public void shiftSubjectUp(Integer quizId, Integer subjectId) {
		Quiz quiz = quizDao.get(quizId);
		QuizSubject toBeShiftUp = null;
		boolean found = false;
		Stack<QuizSubject> newStack = new Stack<>();
		for (int i = quiz.getQuizSubjects().size() - 1; i > -1; i--) {
			QuizSubject subjectInDB = quiz.getQuizSubjects().get(i);
			if (subjectInDB.getSubjectId().equals(subjectId)) {
				toBeShiftUp = subjectInDB;
				found = true;
				continue;
			}
			newStack.push(subjectInDB);
			if (toBeShiftUp != null) {
				newStack.push(toBeShiftUp);
				toBeShiftUp = null;
			}
		}
		if (found) {
			List<QuizSubject> newList = new ArrayList<>();
			while(!newStack.isEmpty()) {
				newList.add(newStack.pop());
			}
			newStack = null;
			updateSubjectsInQuiz(quiz, newList);
		} else {
			LOGGER.warn("[QuizService]Cannot find subject " + subjectId + " in quiz " + quizId);
		}
	}

	@Override
	public void shiftSubjectDown(Integer quizId, Integer subjectId) {
		Quiz quiz = quizDao.get(quizId);
		QuizSubject toBeShiftDown = null;
		boolean found = false;
		List<QuizSubject> newList = new ArrayList<>();
		for (QuizSubject subjectInDB : quiz.getQuizSubjects()) {
			if (subjectInDB.getSubjectId().equals(subjectId)) {
				toBeShiftDown = subjectInDB;
				found = true;
				continue;
			}
			newList.add(subjectInDB);
			if (toBeShiftDown != null) {
				newList.add(toBeShiftDown);
				toBeShiftDown = null;
			}
		}
		if (found) {
			updateSubjectsInQuiz(quiz, newList);
		} else {
			LOGGER.warn("[QuizService]Cannot find subject " + subjectId + " in quiz " + quizId);
		}
	}

	@Override
	public void addSubjectsToQuiz(Integer quizId, List<QuizSubject> subjectList) {
		Quiz quiz = quizDao.get(quizId);
		List<QuizSubject> subjects = quiz.getQuizSubjects();
		subjects.addAll(subjectList);
		updateSubjectsInQuiz(quiz, subjects);
	}

	@Override
	public void deleteSubjectFromQuiz(Integer quizId, Integer subjectId) {
		Quiz quiz = quizDao.get(quizId);
		List<QuizSubject> subjectList = quiz.getQuizSubjects();
		QuizSubject toBeDeleted = null;
		for (QuizSubject subject : subjectList) {
			if (subject.getSubjectId().equals(subjectId)) {
				toBeDeleted = subject;
				break;
			}
		}
		if (toBeDeleted != null) {
			subjectList.remove(toBeDeleted);
			updateSubjectsInQuiz(quiz, subjectList);
		} else {
			LOGGER.warn("[QuizService]Cannot find subject " + subjectId + " in quiz " + quizId);
		}
	}

	@Override
	public void updateSubjectsInQuiz(Integer quizId, List<QuizSubject> subjectList) {
		Quiz quiz = quizDao.load(quizId);
		updateSubjectsInQuiz(quiz, subjectList);
	}
	
	private void updateSubjectsInQuiz(Quiz quiz, List<QuizSubject> subjectList) {
		quiz.setQuizSubjects(subjectList);
		quizDao.saveOrUpdate(quiz);
		LOGGER.info("[QuizService]Successfully updated subject list in quiz of id " + quiz.getQuizId());
	}

}

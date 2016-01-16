package com.fivestars.websites.onlinetest.service.impl;

import java.util.List;

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
		Quiz quiz = quizDao.load(quizId);
		List<QuizSubject> subjectList = quiz.getQuizSubjects();
		subjectList.add(subject);
		updateSubjectsInQuiz(quiz, subjectList);
	}

	@Override
	public void insertSubjectBefore(Integer quizId, Integer subjectId, QuizSubject subject) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void insertSubjectAfter(Integer quizId, Integer subjectId, QuizSubject subject) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void shiftSubjectUp(Integer quizId, Integer subjectId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void shifySubjectDown(Integer quizId, Integer subjectId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addSubjectsToQuiz(Integer quizId, List<QuizSubject> subjectList) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteSubjectFromQuiz(Integer quizId, Integer subjectId) {
		// TODO Auto-generated method stub
		
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

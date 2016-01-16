package com.fivestars.websites.onlinetest.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fivestars.websites.onlinetest.dao.QuizDAO;
import com.fivestars.websites.onlinetest.model.Quiz;
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

}

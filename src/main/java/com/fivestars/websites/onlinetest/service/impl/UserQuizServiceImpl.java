package com.fivestars.websites.onlinetest.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fivestars.websites.onlinetest.dao.QuizDAO;
import com.fivestars.websites.onlinetest.dao.UserAnswerDAO;
import com.fivestars.websites.onlinetest.dao.UserQuizDAO;
import com.fivestars.websites.onlinetest.model.Quiz;
import com.fivestars.websites.onlinetest.model.UserQuiz;
import com.fivestars.websites.onlinetest.service.UserQuizService;

@Transactional
@Service("userQuizService")
public class UserQuizServiceImpl implements UserQuizService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserQuizServiceImpl.class);
	
	@Autowired
	private UserQuizDAO userQuizDao;
	@Autowired
	private QuizDAO quizDao;
	@Autowired
	private UserAnswerDAO userAnswerDao;
	
	@Override
	public List<Quiz> getUserParticipatedQuiz(String userName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer createUserQuiz(UserQuiz userQuiz) {
		Integer recordId = userQuizDao.save(userQuiz);
		LOGGER.info("[UserQuizService]Successfully created user quiz record of id " + recordId);
		return recordId;
	}

	@Override
	public void updateUserQuiz(UserQuiz userQuiz) {
		userQuizDao.saveOrUpdate(userQuiz);
		LOGGER.info("[UserQuizService]Successfully updated user quiz record of id " + userQuiz.getRecordId());
	}

	@Override
	public void deleteUserQuiz(Integer recordId) {
		userQuizDao.delete(recordId);
		LOGGER.info("[UserQuizService]Successfully delete user quiz record of id " + recordId);
	}

	@Override
	public UserQuiz loadUserQuizById(Integer recordId) {
		return userQuizDao.get(recordId);
	}
}

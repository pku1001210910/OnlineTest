package com.fivestars.websites.onlinetest.dao.impl;

import org.springframework.stereotype.Repository;

import com.fivestars.websites.onlinetest.dao.QuizDAO;
import com.fivestars.websites.onlinetest.model.Quiz;

@Repository("quizDao")
public class QuizDAOImpl extends GenericDAOImpl<Quiz, Integer> implements QuizDAO {

}

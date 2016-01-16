package com.fivestars.websites.onlinetest.dao.impl;

import org.springframework.stereotype.Repository;

import com.fivestars.websites.onlinetest.dao.QuizSubjectDAO;
import com.fivestars.websites.onlinetest.model.QuizSubject;

@Repository("quizSubjectDao")
public class QuizSubjectDAOImpl extends GenericDAOImpl<QuizSubject, Integer> implements QuizSubjectDAO {

}

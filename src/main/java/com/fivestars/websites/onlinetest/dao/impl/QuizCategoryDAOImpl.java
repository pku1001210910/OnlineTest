package com.fivestars.websites.onlinetest.dao.impl;

import org.springframework.stereotype.Repository;

import com.fivestars.websites.onlinetest.dao.QuizCategoryDAO;
import com.fivestars.websites.onlinetest.model.QuizCategory;

@Repository("quizCategoryDao")
public class QuizCategoryDAOImpl extends GenericDAOImpl<QuizCategory, Integer> implements QuizCategoryDAO {

}

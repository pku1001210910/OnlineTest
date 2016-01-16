package com.fivestars.websites.onlinetest.dao.impl;

import org.springframework.stereotype.Repository;

import com.fivestars.websites.onlinetest.dao.UserQuizDAO;
import com.fivestars.websites.onlinetest.model.UserQuiz;

@Repository("userQuizDao")
public class UserQuizDAOImpl extends GenericDAOImpl<UserQuiz, Integer> implements UserQuizDAO {

}

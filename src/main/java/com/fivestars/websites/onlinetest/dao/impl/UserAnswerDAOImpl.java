package com.fivestars.websites.onlinetest.dao.impl;

import org.springframework.stereotype.Repository;

import com.fivestars.websites.onlinetest.dao.UserAnswerDAO;
import com.fivestars.websites.onlinetest.model.UserAnswer;

@Repository("userAnswerDao")
public class UserAnswerDAOImpl extends GenericDAOImpl<UserAnswer, Integer> implements UserAnswerDAO {

}

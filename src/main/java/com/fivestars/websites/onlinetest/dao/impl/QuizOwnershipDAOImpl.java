package com.fivestars.websites.onlinetest.dao.impl;

import org.springframework.stereotype.Repository;

import com.fivestars.websites.onlinetest.dao.QuizOwnershipDAO;
import com.fivestars.websites.onlinetest.model.QuizOwnership;

@Repository("quizOwnershipDao")
public class QuizOwnershipDAOImpl extends GenericDAOImpl<QuizOwnership, Integer> implements QuizOwnershipDAO {

}

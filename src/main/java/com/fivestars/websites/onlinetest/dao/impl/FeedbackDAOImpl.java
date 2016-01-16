package com.fivestars.websites.onlinetest.dao.impl;

import org.springframework.stereotype.Repository;

import com.fivestars.websites.onlinetest.dao.FeedbackDAO;
import com.fivestars.websites.onlinetest.model.Feedback;

@Repository("feedbackDao")
public class FeedbackDAOImpl extends GenericDAOImpl<Feedback, Integer> implements FeedbackDAO {

}

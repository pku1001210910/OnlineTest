package com.fivestars.websites.onlinetest.service.impl;


import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fivestars.websites.onlinetest.dao.FeedbackDAO;
import com.fivestars.websites.onlinetest.model.Feedback;
import com.fivestars.websites.onlinetest.service.FeedbackService;

@Transactional
@Service("feedbackService")
public class FeedbackServiceImpl implements FeedbackService {

	private static final Logger LOGGER = LoggerFactory.getLogger(FeedbackServiceImpl.class);
	
	@Autowired
	private FeedbackDAO feedbackDao;
	
	@Override
	public Integer createFeedback(Feedback feedback) {
		Integer feedbackId = feedbackDao.save(feedback);
		LOGGER.info("[FeedbackService]Successfully created feedback of id " + feedbackId);
		return feedbackId;
	}

	@Override
	public Feedback getFeedbackById(Integer feedbackId) {
		return feedbackDao.get(feedbackId);
	}

	@Override
	public void updateFeedback(Feedback feedback) {
		feedbackDao.saveOrUpdate(feedback);
		LOGGER.info("[FeedbackService]Successfully updated feedback of id " + feedback.getFeedbackId());
	}

	@Override
	public void deleteFeedback(Integer feedbackId) {
		feedbackDao.delete(feedbackId);
		LOGGER.info("[FeedbackService]Successfully deleted feedback of id " + feedbackId);
	}

	@Override
	public Integer getFeedbackByScore(Integer quizId, Double score) {
		DetachedCriteria quizCriteria = DetachedCriteria.forClass(Feedback.class);
		Criterion quizEq = Restrictions.eq("quizId", quizId);
		quizCriteria.add(quizEq);
		List<Feedback> feedbacks = feedbackDao.listSome(quizCriteria);
		for (Feedback feedback : feedbacks) {
			if (feedback.getScoreFrom() <= score && score <= feedback.getScoreTo()) {
				return feedback.getFeedbackId();
			}
		}
		LOGGER.warn("[FeedbackService]Cannot find a proper feedback for the score " + score + " in quiz " + quizId);
		return feedbacks.get(0).getFeedbackId();
	}

	@Override
	public List<Feedback> getFeedbackByQuiz(Integer quizId) {
		DetachedCriteria quizCriteria = DetachedCriteria.forClass(Feedback.class);
		Criterion quizEq = Restrictions.eq("quizId", quizId);
		quizCriteria.add(quizEq);
		return feedbackDao.listSome(quizCriteria);
	}

	@Override
	public void deleteFeedbackByQuizId(Integer quizId) {
		List<Feedback> feedbacks = getFeedbackByQuiz(quizId);
		for (Feedback feedback : feedbacks) {
			feedbackDao.delete(feedback);
		}
	}
}

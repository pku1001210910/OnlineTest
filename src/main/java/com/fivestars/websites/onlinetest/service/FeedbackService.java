package com.fivestars.websites.onlinetest.service;

import java.util.List;

import com.fivestars.websites.onlinetest.model.Feedback;

public interface FeedbackService {

	/**
	 * Create a new feedback
	 * @param feedbackId
	 * @return
	 */
	public Integer createFeedback(Feedback feedback);
	
	/**
	 * Get feedback by feedbackId
	 * @param feedbackId
	 * @return
	 */
	public Feedback getFeedbackById(Integer feedbackId);
	
	/**
	 * Update a feedback
	 * @param feedback
	 */
	public void updateFeedback(Feedback feedback);
	
	/**
	 * Delete a feedback
	 * @param feedbackId
	 */
	public void deleteFeedback(Integer feedbackId);
	
	/**
	 * Get feedbackId by quizId and score
	 * @param quizId
	 * @param score
	 */
	public Integer getFeedbackByScore(Integer quizId, Double score);
	
	/**
	 * Get feedback by quizId
	 * @param quizId
	 * @return
	 */
	public List<Feedback> getFeedbackByQuiz(Integer quizId);
	
	/**
	 * Delete feedback by quizId
	 * @param quizId
	 */
	public void deleteFeedbackByQuizId(Integer quizId);
}

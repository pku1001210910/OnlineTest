package com.fivestars.websites.onlinetest.service;

import java.util.List;

import com.fivestars.websites.onlinetest.model.UserAnswer;
import com.fivestars.websites.onlinetest.model.UserQuiz;

public interface UserQuizService {

	/**
	 * Load a user quiz record by recordId
	 * @param recordId
	 * @return
	 */
	public UserQuiz loadUserQuizById(Integer recordId);
	
	/**
	 * Get user participated quiz by user name
	 * @param userName
	 * @return
	 */
	public List<UserQuiz> getUserParticipatedQuiz(String userName);
	
	/**
	 * Create a user quiz record
	 * @param userQuiz
	 * @return
	 */
	public Integer createUserQuiz(UserQuiz userQuiz);
	
	/**
	 * Update a user quiz record
	 * @param userQuiz
	 */
	public void updateUserQuiz(UserQuiz userQuiz);
	
	/**
	 * Delete a user quiz record
	 * @param recordId
	 */
	public void deleteUserQuiz(Integer recordId);
	
	/**
	 * Get the answer for specific subject
	 * @param recordId
	 * @param subjectId
	 * @return
	 */
	public UserAnswer getAnswerBySubject(Integer recordId, Integer subjectId);

	/**
	 * Update the user answer
	 * @param userAnswer
	 */
	public void updateUserAnswer(UserAnswer userAnswer);
	
	/**
	 * Grade the score of user answer
	 * @param answerId
	 * @param score
	 */
	public void gradeUserAnswer(Integer answerId, Double score);
}

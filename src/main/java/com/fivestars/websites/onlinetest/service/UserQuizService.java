package com.fivestars.websites.onlinetest.service;

import java.util.List;

import com.fivestars.websites.onlinetest.model.Quiz;
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
	public List<Quiz> getUserParticipatedQuiz(String userName);
	
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
	

}

package com.fivestars.websites.onlinetest.service;

import java.util.List;

import com.fivestars.websites.onlinetest.model.QuizOwnership;
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
	 * Get user participated quiz by user name, curPageNum, pageSize
	 * @param userName
	 * @return
	 */
	public List<UserQuiz> getUserParticipatedQuiz(String userName, int curPageNum, int pageSize);
	
	/**
	 * Get user participated quiz by user name, curPageNum, pageSize, orderProperty, asc
	 * @param userName
	 * @return
	 */
	public List<UserQuiz> getUserParticipatedQuiz(String userName, int curPageNum, int pageSize, String orderProperty, boolean asc);
	
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
	
	/**
	 * grant user ownership of a quiz
	 * @param userName
	 * @param quizId
	 */
	public Integer addUserQuizOwnership(QuizOwnership ownership);
	
	/**
	 * delete user ownership of a quiz
	 * @param ownershipId
	 */
	public void deleteUserQuizOwnership(Integer ownershipId);
	
	/**
	 * Check if user owns a quiz
	 * @param quizId
	 * @param userName
	 */
	public boolean isUserOwnQuiz(Integer quizId, String userName);

	/**
	 * Get user participate quiz size
	 * @param userName
	 * @return
	 */
	public Integer getUserParticipatedQuizSize(String userName);

	/**
	 * Get list of quizOwnership for specific userName
	 * @param userName
	 * @return
	 */
	public List<QuizOwnership> loadQuizOwnershipList(String userName);

	
}

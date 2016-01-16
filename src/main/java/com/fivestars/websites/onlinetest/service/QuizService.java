package com.fivestars.websites.onlinetest.service;

import java.util.List;

import com.fivestars.websites.onlinetest.model.Quiz;

public interface QuizService {

	/**
	 * Create a new quiz
	 * @param quiz
	 * @return the quizId of created quiz
	 */
	public Integer createQuiz(Quiz quiz);
	
	/**
	 * Load all available quiz
	 * @return the list of quiz
	 */
	public List<Quiz> loadAllQuiz();
	
	/**
	 * Delete quiz by quizId
	 * @param quizId
	 */
	public void deleteQuiz(Integer quizId);
}

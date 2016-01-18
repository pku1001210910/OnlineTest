package com.fivestars.websites.onlinetest.service;

import java.util.List;

import com.fivestars.websites.onlinetest.model.Quiz;
import com.fivestars.websites.onlinetest.model.QuizSubject;

public interface QuizService {

	/**
	 * Create a new quiz
	 * @param quiz
	 * @return the quizId of created quiz
	 */
	public Integer createQuiz(Quiz quiz);
	
	/**
	 * Update a quiz
	 * @param quiz
	 */
	public void updateQuiz(Quiz quiz);
	
	/**
	 * Load a quiz by quizId
	 * @param quizId
	 * @return
	 */
	public Quiz loadQuizById(Integer quizId);
	
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
	
	/**
	 * Add one new subject to a quiz, the order is appended to last
	 * @param quizId
	 * @param subject
	 */
	public void addSubjectToQuiz(Integer quizId, QuizSubject subject);
	
	/**
	 * Add several subjects to a quiz in bulk, the positions are appended to last.
	 * The order of new subject will be in accordance with position in list
	 * @param quizId
	 * @param subjectSet
	 */
	public void addSubjectsToQuiz(Integer quizId, List<QuizSubject> subjectList);
	
	/**
	 * Delete a subject from a quiz
	 * @param quizId
	 * @param subjectId
	 */
	public void deleteSubjectFromQuiz(Integer quizId, Integer subjectId);
	
	/**
	 * Insert a subject at a specified position
	 * @param quizId
	 * @param subject
	 * @param order
	 */
	public void insertSubjectAt(Integer quizId, QuizSubject subject, int order);
	
	/**
	 * Shift a subject to a previous position
	 * @param quizId
	 * @param subjectId
	 */
	public void shiftSubjectUp(Integer quizId, Integer subjectId);
	
	/**
	 * Shift a subject to a latter position 
	 * @param quizId
	 * @param subjectId
	 */
	public void shiftSubjectDown(Integer quizId, Integer subjectId);
	
	/**
	 * Update a subject
	 * @param subject
	 */
	public void updateQuizSubject(QuizSubject subject);
	
	/**
	 * Load a subject by subjectId
	 * @param subjectId
	 */
	public QuizSubject loadQuizSubjectById(Integer subjectId);
}

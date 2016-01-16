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
	 * Add one new subject to a quiz, the position is appended to last
	 * @param quizId
	 * @param subject
	 */
	public void addSubjectToQuiz(Integer quizId, QuizSubject subject);
	
	/**
	 * Insert a subject at the position before subject specified by subjectId
	 * @param quizId
	 * @param subjectId
	 * @param subject
	 */
	public void insertSubjectBefore(Integer quizId, Integer subjectId, QuizSubject subject);
	
	/**
	 * Insert a subject at the position after subject specified by subjectId
	 * @param quizId
	 * @param subjectId
	 * @param subject
	 */
	public void insertSubjectAfter(Integer quizId, Integer subjectId, QuizSubject subject);
	
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
	public void shifySubjectDown(Integer quizId, Integer subjectId);
	
	/**
	 * Add several subjects to a quiz in bulk, the positions are appended to last
	 * @param quizId
	 * @param subjectList
	 */
	public void addSubjectsToQuiz(Integer quizId, List<QuizSubject> subjectList);
	
	/**
	 * Delete a subject from a quiz
	 * @param quizId
	 * @param subjectId
	 */
	public void deleteSubjectFromQuiz(Integer quizId, Integer subjectId);
	
	/**
	 * Update the subjects in a quiz. Old subjects will be replaced
	 * @param quizId
	 * @param subjectList
	 */
	public void updateSubjectsInQuiz(Integer quizId, List<QuizSubject> subjectList);
}

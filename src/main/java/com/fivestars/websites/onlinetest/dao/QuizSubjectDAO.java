package com.fivestars.websites.onlinetest.dao;

import com.fivestars.websites.onlinetest.model.QuizSubject;

public interface QuizSubjectDAO extends GenericDAO<QuizSubject, Integer> {

	public Integer getSubjectByQuizIdAndOrder(Integer quizId, Integer order);
}

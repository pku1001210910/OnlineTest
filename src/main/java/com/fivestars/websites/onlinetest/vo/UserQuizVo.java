package com.fivestars.websites.onlinetest.vo;

import com.fivestars.websites.onlinetest.model.Quiz;
import com.fivestars.websites.onlinetest.model.QuizCategory;
import com.fivestars.websites.onlinetest.model.UserQuiz;

public class UserQuizVo {
	private UserQuiz userQuiz;
	private Quiz quiz;
	private QuizCategory quizCategory;
	
	public UserQuizVo(UserQuiz userQuiz, Quiz quiz) {
		this.userQuiz = userQuiz;
		this.quiz = quiz;
	}

	public UserQuiz getUserQuiz() {
		return userQuiz;
	}

	public void setUserQuiz(UserQuiz userQuiz) {
		this.userQuiz = userQuiz;
	}

	public Quiz getQuiz() {
		return quiz;
	}

	public void setQuiz(Quiz quiz) {
		this.quiz = quiz;
	}

	public QuizCategory getQuizCategory() {
		return quizCategory;
	}

	public void setQuizCategory(QuizCategory quizCategory) {
		this.quizCategory = quizCategory;
	}
	
}

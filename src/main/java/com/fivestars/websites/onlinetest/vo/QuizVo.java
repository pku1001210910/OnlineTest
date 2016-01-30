package com.fivestars.websites.onlinetest.vo;

import com.fivestars.websites.onlinetest.model.Quiz;
import com.fivestars.websites.onlinetest.model.QuizCategory;
import com.fivestars.websites.onlinetest.model.QuizOwnership;

public class QuizVo {
	private Quiz quiz;
	private QuizCategory quizCategory;
	private QuizOwnership quizOwnership;
	
	public QuizVo(Quiz quiz, QuizCategory quizCategory) {
		this.quiz = quiz;
		this.quizCategory = quizCategory;
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

	public QuizOwnership getQuizOwnership() {
		return quizOwnership;
	}

	public void setQuizOwnership(QuizOwnership quizOwnership) {
		this.quizOwnership = quizOwnership;
	}
	
	
	
}

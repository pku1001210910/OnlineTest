package com.fivestars.websites.onlinetest.vo;

import java.util.ArrayList;
import java.util.List;

import com.fivestars.websites.onlinetest.model.Feedback;
import com.fivestars.websites.onlinetest.model.UserAnswer;
import com.fivestars.websites.onlinetest.model.UserQuiz;

public class UserAnswerVo {
	private UserAnswer userAnswer;
	private String answer;
	
	public UserAnswerVo(UserAnswer userAnswer, String answer) {
		this.userAnswer = userAnswer;
		this.answer = answer;
	}

	public UserAnswer getUserAnswer() {
		return userAnswer;
	}

	public void setUserAnswer(UserAnswer userAnswer) {
		this.userAnswer = userAnswer;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
}

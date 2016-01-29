package com.fivestars.websites.onlinetest.vo;

import java.util.ArrayList;
import java.util.List;

import com.fivestars.websites.onlinetest.model.Feedback;
import com.fivestars.websites.onlinetest.model.UserQuiz;

public class UserQuizDetailVo {
	private UserQuiz userQuiz;
	private List<UserQuizSubjectVo> userQuizSubjectVoList;
	private Feedback feedBack;
	
	public UserQuizDetailVo(UserQuiz userQuiz, Feedback feedback) {
		this.userQuiz = userQuiz;
		this.feedBack = feedback;
		this.userQuizSubjectVoList = new ArrayList<UserQuizSubjectVo>();
	}

	public UserQuiz getUserQuiz() {
		return userQuiz;
	}

	public void setUserQuiz(UserQuiz userQuiz) {
		this.userQuiz = userQuiz;
	}

	public List<UserQuizSubjectVo> getUserQuizSubjectVoList() {
		return userQuizSubjectVoList;
	}

	public void setUserQuizSubjectVoList(List<UserQuizSubjectVo> userQuizSubjectVoList) {
		this.userQuizSubjectVoList = userQuizSubjectVoList;
	}

	public Feedback getFeedBack() {
		return feedBack;
	}

	public void setFeedBack(Feedback feedBack) {
		this.feedBack = feedBack;
	}
	
	
	
}

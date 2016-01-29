package com.fivestars.websites.onlinetest.vo;

import java.util.Set;

import com.fivestars.websites.onlinetest.model.QuizSubject;
import com.fivestars.websites.onlinetest.model.SubjectItem;

public class UserQuizSubjectVo {
	private Set<SubjectItem> subjectItems;
	private UserAnswerVo userAnswerVo;
	private QuizSubject quizSubject;
	
	public UserQuizSubjectVo(QuizSubject quizSubject, Set<SubjectItem> subjectItems, UserAnswerVo userAnswerVo) {
		this.quizSubject = quizSubject;
		this.subjectItems = subjectItems;
		this.userAnswerVo = userAnswerVo;
	}

	public Set<SubjectItem> getSubjectItems() {
		return subjectItems;
	}

	public void setSubjectItems(Set<SubjectItem> subjectItems) {
		this.subjectItems = subjectItems;
	}

	public UserAnswerVo getUserAnswerVo() {
		return userAnswerVo;
	}

	public void setUserAnswerVo(UserAnswerVo userAnswerVo) {
		this.userAnswerVo = userAnswerVo;
	}

	public QuizSubject getQuizSubject() {
		return quizSubject;
	}

	public void setQuizSubject(QuizSubject quizSubject) {
		this.quizSubject = quizSubject;
	}
	
}

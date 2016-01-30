package com.fivestars.websites.onlinetest.model;
// Generated Jan 15, 2016 12:20:03 AM by Hibernate Tools 4.3.1.Final

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;


/**
 * QuizSubject generated by hbm2java
 */
@Entity
@Table(name = "quiz_subject", catalog = "online_test")
public class QuizSubject implements Serializable, Comparable<QuizSubject> {

	private static final long serialVersionUID = 1L;
	
	private Integer subjectId;
	private Quiz quiz;
	private Integer type;
	private Integer resourceId;
	private Integer subjectOrder;
	private String question;
	private Set<SubjectItem> subjectItems = new HashSet<>();

	public QuizSubject() {
	}

	public QuizSubject(Quiz quiz, Integer type) {
		this.quiz = quiz;
		this.type = type;
	}

	public QuizSubject(Quiz quiz, Integer type, Integer resourceId, Integer subjectOrder, String question,
			Set<SubjectItem> subjectItems) {
		this.quiz = quiz;
		this.type = type;
		this.resourceId = resourceId;
		this.subjectOrder = subjectOrder;
		this.question = question;
		this.subjectItems = subjectItems;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "subjectId", unique = true, nullable = false)
	public Integer getSubjectId() {
		return this.subjectId;
	}

	public void setSubjectId(Integer subjectId) {
		this.subjectId = subjectId;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "quizId", nullable = false)
	public Quiz getQuiz() {
		return this.quiz;
	}

	public void setQuiz(Quiz quiz) {
		this.quiz = quiz;
	}

	@Column(name = "type", nullable = false)
	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Column(name = "resourceId")
	public Integer getResourceId() {
		return this.resourceId;
	}

	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}

	@Column(name = "subjectOrder")
	public Integer getSubjectOrder() {
		return this.subjectOrder;
	}

	public void setSubjectOrder(Integer subjectOrder) {
		this.subjectOrder = subjectOrder;
	}

	@Column(name = "question", columnDefinition="longtext")
	public String getQuestion() {
		return this.question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	@OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER, mappedBy = "quizSubject")
	public Set<SubjectItem> getSubjectItems() {
		return this.subjectItems;
	}
	
	public void setSubjectItems(Set<SubjectItem> subjectItems) {
		this.subjectItems = subjectItems;
	}
	
	@Override
	public int compareTo(QuizSubject o) {
		return this.getSubjectOrder() - o.getSubjectOrder();
	}

}

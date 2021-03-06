package com.fivestars.websites.onlinetest.model;
// Generated Jan 15, 2016 12:20:03 AM by Hibernate Tools 4.3.1.Final

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * SubjectItem generated by hbm2java
 */
@Entity
@Table(name = "subject_item", catalog = "online_test")
public class SubjectItem implements Serializable, Comparable<SubjectItem> {

	private static final long serialVersionUID = 1L;
	
	private Integer itemId;
	private QuizSubject quizSubject;
	private String choice;
	private Double score;
	private Integer nextSubjectId;
	private Integer itemOrder;

	public SubjectItem() {
	}

	public SubjectItem(QuizSubject quizSubject) {
		this.quizSubject = quizSubject;
	}

	public SubjectItem(QuizSubject quizSubject, String option, Double score, Integer nextSubjectId, Integer itemOrder) {
		this.quizSubject = quizSubject;
		this.choice = option;
		this.score = score;
		this.nextSubjectId = nextSubjectId;
		this.setItemOrder(itemOrder);
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "itemId", unique = true, nullable = false)
	public Integer getItemId() {
		return this.itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId; 
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "subjectId", nullable = false)
	public QuizSubject getQuizSubject() {
		return this.quizSubject;
	}

	public void setQuizSubject(QuizSubject quizSubject) {
		this.quizSubject = quizSubject;
	}

	@Column(name = "choice", columnDefinition="longtext")
	public String getChoice() {
		return this.choice;
	}

	public void setChoice(String choice) {
		this.choice = choice;
	}

	@Column(name = "score", precision = 22, scale = 0)
	public Double getScore() {
		return this.score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	@Column(name = "nextSubjectId")
	public Integer getNextSubjectId() {
		return this.nextSubjectId;
	}

	public void setNextSubjectId(Integer nextSubjectId) {
		this.nextSubjectId = nextSubjectId;
	}

	@Column(name = "itemOrder")
	public Integer getItemOrder() {
		return itemOrder;
	}

	public void setItemOrder(Integer itemOrder) {
		this.itemOrder = itemOrder;
	}

	@Override
	public int compareTo(SubjectItem o) {
		return this.getItemOrder() - o.getItemOrder();
	}
}

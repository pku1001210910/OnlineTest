package com.fivestars.websites.onlinetest.model;
// Generated Jan 15, 2016 12:20:03 AM by Hibernate Tools 4.3.1.Final

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * Quiz generated by hbm2java
 */
@Entity
@Table(name = "quiz", catalog = "online_test")
public class Quiz implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer quizId;
	private String title;
	private String description;
	private Integer category;
	private Byte needCharge;
	private Double price;
	private Date submitDate;
	private Byte repeatable;
	private Integer status;
	private Set<QuizSubject> quizSubjects = new HashSet<>();

	public Quiz() {
	}

	public Quiz(String title, String description, Integer category, Byte needCharge, Double price, Date submitDate,
			Byte repeatable, Integer status, Set<QuizSubject> quizSubjects) {
		this.title = title;
		this.description = description;
		this.category = category;
		this.needCharge = needCharge;
		this.price = price;
		this.submitDate = submitDate;
		this.repeatable = repeatable;
		this.status = status;
		this.quizSubjects = quizSubjects;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "quizId", unique = true, nullable = false)
	public Integer getQuizId() {
		return this.quizId;
	}

	public void setQuizId(Integer quizId) {
		this.quizId = quizId;
	}

	@Column(name = "title", length = 50)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "description", length = 65535)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "category")
	public Integer getCategory() {
		return this.category;
	}

	public void setCategory(Integer category) {
		this.category = category;
	}

	@Column(name = "needCharge")
	public Byte getNeedCharge() {
		return this.needCharge;
	}

	public void setNeedCharge(Byte needCharge) {
		this.needCharge = needCharge;
	}

	@Column(name = "price", precision = 22, scale = 0)
	public Double getPrice() {
		return this.price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "submitDate", length = 19)
	public Date getSubmitDate() {
		return this.submitDate;
	}

	public void setSubmitDate(Date submitDate) {
		this.submitDate = submitDate;
	}

	@Column(name = "repeatable")
	public Byte getRepeatable() {
		return this.repeatable;
	}

	public void setRepeatable(Byte repeatable) {
		this.repeatable = repeatable;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER, mappedBy = "quiz")
	public Set<QuizSubject> getQuizSubjects() {
		return this.quizSubjects;
	}

	public void setQuizSubjects(Set<QuizSubject> quizSubjects) {
		this.quizSubjects = quizSubjects;
	}
}

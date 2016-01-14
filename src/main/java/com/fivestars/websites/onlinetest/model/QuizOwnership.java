package com.fivestars.websites.onlinetest.model;
// Generated Jan 15, 2016 12:20:03 AM by Hibernate Tools 4.3.1.Final

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * QuizOwnership generated by hbm2java
 */
@Entity
@Table(name = "quiz_ownership", catalog = "online_test")
public class QuizOwnership implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer ownershipId;
	private String username;
	private int quizId;
	private Byte expired;
	private Date buyDate;

	public QuizOwnership() {
	}

	public QuizOwnership(String username, int quizId) {
		this.username = username;
		this.quizId = quizId;
	}

	public QuizOwnership(String username, int quizId, Byte expired, Date buyDate) {
		this.username = username;
		this.quizId = quizId;
		this.expired = expired;
		this.buyDate = buyDate;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "ownershipId", unique = true, nullable = false)
	public Integer getOwnershipId() {
		return this.ownershipId;
	}

	public void setOwnershipId(Integer ownershipId) {
		this.ownershipId = ownershipId;
	}

	@Column(name = "username", nullable = false, length = 50)
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "quizId", nullable = false)
	public int getQuizId() {
		return this.quizId;
	}

	public void setQuizId(int quizId) {
		this.quizId = quizId;
	}

	@Column(name = "expired")
	public Byte getExpired() {
		return this.expired;
	}

	public void setExpired(Byte expired) {
		this.expired = expired;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "buyDate", length = 19)
	public Date getBuyDate() {
		return this.buyDate;
	}

	public void setBuyDate(Date buyDate) {
		this.buyDate = buyDate;
	}

}

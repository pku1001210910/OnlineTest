package com.fivestars.websites.onlinetest.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "article")
public class Article implements java.io.Serializable {
	private static final long serialVersionUID = -3142763797463198341L;

	@Id
	@Column(length = 32)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable = false, length = 100)
	private String title;
	
	@Column(columnDefinition="TEXT")
	private String content;
	
	@Column(nullable = false, length = 50)
	private String date;
}

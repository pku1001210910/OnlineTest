package com.fivestars.websites.onlinetest.action.admin.mgmt;


import java.util.Date;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fivestars.websites.onlinetest.model.Article;
import com.fivestars.websites.onlinetest.service.ArticleService;
import com.opensymphony.xwork2.ActionSupport;

import lombok.Data;

@ParentPackage("adminAjax")
@Namespace("/admin/articles")
@Data
public class ArticlesAction{
	private Integer id;
	private String title;
	private String content;
	
	private String json;
	
	@Autowired
	private ArticleService articleService;

	@Action(value = "save", results = { @Result(name="success", type = "json")})
	public String saveArticle() throws JsonProcessingException {
		Article article = id != null ? articleService.loadById(id) : new Article();
		article.setTitle(title);
		article.setContent(content);
		article.setCreateDate(new Date(System.currentTimeMillis()));
		articleService.save(article);
		
		json = new ObjectMapper().writeValueAsString(article);
		return ActionSupport.SUCCESS;
	}
	
	@Action(value = "load", results = { @Result(name="success", type = "json")})
	public String loadArticle() throws JsonProcessingException {
		Article article = new Article(articleService.loadById(id));
		
		json = new ObjectMapper().writeValueAsString(article);
		return ActionSupport.SUCCESS;
	}
	
	@Action(value = "remove", results = { @Result(name="success", type = "json")})
	public String removeArticle() throws JsonProcessingException {
		articleService.delete(id);
		return ActionSupport.SUCCESS;
	}
}
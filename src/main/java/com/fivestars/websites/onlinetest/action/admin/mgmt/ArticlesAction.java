package com.fivestars.websites.onlinetest.action.admin.mgmt;


import java.io.IOException;
import java.util.Date;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fivestars.websites.onlinetest.model.Article;
import com.fivestars.websites.onlinetest.service.ArticleService;
import com.opensymphony.xwork2.ActionSupport;

@ParentPackage("admin")
@InterceptorRef(value="global")
@Namespace("/admin/articles")
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
		articleService.saveOrUpdate(article);
		
		Article ret = new Article(article);
		json = new ObjectMapper().writeValueAsString(ret);
		return ActionSupport.SUCCESS;
	}
	
	@Action(value = "load", results = { @Result(name="success", type = "json")})
	public String loadArticle() throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		Article article = new Article(articleService.loadById(id));
		json = objectMapper.writeValueAsString(article);
		return ActionSupport.SUCCESS;
	}
	
	@Action(value = "remove", results = { @Result(name="success", type = "json")})
	public String removeArticle() throws JsonProcessingException {
		articleService.delete(id);
		return ActionSupport.SUCCESS;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}
}
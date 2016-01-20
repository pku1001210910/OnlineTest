package com.fivestars.websites.onlinetest.action.admin;


import java.util.Date;
import java.util.List;

import org.apache.struts2.ServletActionContext;
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

@ParentPackage("admin")
@Namespace("/admin")
@Data
public class ArticlesAction{
	private Integer id;
	private String title;
	private String content;
	
	@Autowired
	private ArticleService articleService;
	
	@Action(value = "articles", results = { @Result(name = "success", location = "/WEB-INF/views/admin/mgmt/articles.jsp") })
	public String articles() throws JsonProcessingException {
		List<Article> articles = articleService.loadAllTitles();
		ObjectMapper json = new ObjectMapper();
		ServletActionContext.getRequest().setAttribute("articles", json.writeValueAsString(articles));
		return ActionSupport.SUCCESS;
	}

	@Action(value = "saveArticles", results = { @Result(name = "success", location = "/WEB-INF/views/admin/mgmt/articles.jsp") })
	public String saveArticle() {
		Article article = id != null ? articleService.loadById(id) : new Article();
		article.setTitle(title);
		article.setContent(content);
		article.setCreateDate(new Date(System.currentTimeMillis()));
		articleService.save(article);
		return ActionSupport.SUCCESS;
	}
}
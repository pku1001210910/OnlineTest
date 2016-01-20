package com.fivestars.websites.onlinetest.action.admin.mgmt;


import java.util.Date;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

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
	
	@Autowired
	private ArticleService articleService;

	@Action(value = "save", results = { @Result(name="success", type = "json")})
	public String saveArticle() {
		Article article = id != null ? articleService.loadById(id) : new Article();
		article.setTitle(title);
		article.setContent(content);
		article.setCreateDate(new Date(System.currentTimeMillis()));
		articleService.save(article);
		return ActionSupport.SUCCESS;
	}
}
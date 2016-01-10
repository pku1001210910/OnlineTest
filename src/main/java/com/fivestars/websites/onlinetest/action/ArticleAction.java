package com.fivestars.websites.onlinetest.action;

import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.fivestars.websites.onlinetest.model.Article;
import com.fivestars.websites.onlinetest.service.ArticleService;
import com.opensymphony.xwork2.ActionSupport;

import lombok.Data;

@ParentPackage("basePackage")
@Namespace("/article")
@Data
public class ArticleAction {
	private int id;

	@Autowired
	private ArticleService articleService;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Action(value = "all", results = { @Result(name = "success", location = "/views/article/articlelist.jsp") })
	public String articleList() {
		Map request = (Map) ServletActionContext.getContext().get("request");
		List<Article> articles = articleService.findAll();
		request.put("articles", articles);
		return ActionSupport.SUCCESS;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Action(value = "view", results = { @Result(name = "success", location = "/views/article/article.jsp") })
	public String articleView() {
		Map request = (Map) ServletActionContext.getContext().get("request");
		Article article = articleService.loadById(id);
		request.put("article", article);
		return ActionSupport.SUCCESS;
	}
}
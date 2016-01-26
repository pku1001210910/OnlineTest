package com.fivestars.websites.onlinetest.action.admin;


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
import com.fivestars.websites.onlinetest.model.Quiz;
import com.fivestars.websites.onlinetest.model.User;
import com.fivestars.websites.onlinetest.service.ArticleService;
import com.fivestars.websites.onlinetest.service.QuizService;
import com.fivestars.websites.onlinetest.service.UserService;
import com.opensymphony.xwork2.ActionSupport;

@ParentPackage("admin")
@Namespace("/admin")
public class HomeAction{
	@Autowired
	private ArticleService articleService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private QuizService quizService;
	
	@Action(value = "home", results = { @Result(name = "success", location = "/WEB-INF/views/admin/home.jsp") })
	public String home() {
		return ActionSupport.SUCCESS;
	}
	
	@Action(value = "articles", results = { @Result(name = "success", location = "/WEB-INF/views/admin/mgmt/articles.jsp") })
	public String articles() throws JsonProcessingException {
		List<Article> articles = articleService.loadAllTitles();
		ObjectMapper json = new ObjectMapper();
		ServletActionContext.getRequest().setAttribute("articles", json.writeValueAsString(articles));
		return ActionSupport.SUCCESS;
	}
	
	@Action(value = "users", results = { @Result(name = "success", location = "/WEB-INF/views/admin/mgmt/users.jsp") })
	public String users() throws JsonProcessingException {
		List<User> users = userService.loadAllUsers();
		List<Quiz> quizzes = quizService.loadAllQuizTitles();
		ObjectMapper json = new ObjectMapper();
		ServletActionContext.getRequest().setAttribute("users", json.writeValueAsString(users));
		ServletActionContext.getRequest().setAttribute("quizzes", json.writeValueAsString(quizzes));
		return ActionSupport.SUCCESS;
	}

	@Action(value = "welcome", results = { @Result(name = "success", location = "/WEB-INF/views/admin/mgmt/welcome.jsp") })
	public String welcome() {
		return ActionSupport.SUCCESS;
	}
	
	@Action(value = "quizzes", results = { @Result(name = "success", location = "/WEB-INF/views/admin/mgmt/quizzes.jsp") })
	public String quizzes() {
		return ActionSupport.SUCCESS;
	}
	
	@Action(value = "payment", results = { @Result(name = "success", location = "/WEB-INF/views/admin/mgmt/payment.jsp") })
	public String payment() {
		return ActionSupport.SUCCESS;
	}
	
}
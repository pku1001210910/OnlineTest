package com.fivestars.websites.onlinetest.action.admin;


import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import com.opensymphony.xwork2.ActionSupport;

@ParentPackage("admin")
@Namespace("/admin")
public class HomeAction{
	
	@Action(value = "home", results = { @Result(name = "success", location = "/WEB-INF/views/admin/home.jsp") })
	public String home() {
		return ActionSupport.SUCCESS;
	}

	@Action(value = "welcome", results = { @Result(name = "success", location = "/WEB-INF/views/admin/mgmt/welcome.jsp") })
	public String welcome() {
		return ActionSupport.SUCCESS;
	}

	@Action(value = "users", results = { @Result(name = "success", location = "/WEB-INF/views/admin/mgmt/users.jsp") })
	public String users() {
		return ActionSupport.SUCCESS;
	}
	
	@Action(value = "articles", results = { @Result(name = "success", location = "/WEB-INF/views/admin/mgmt/articles.jsp") })
	public String articles() {
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
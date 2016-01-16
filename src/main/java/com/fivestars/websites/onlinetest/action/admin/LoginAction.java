package com.fivestars.websites.onlinetest.action.admin;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import com.fivestars.websites.onlinetest.constant.Message;
import com.opensymphony.xwork2.ActionSupport;

import lombok.Setter;

@ParentPackage("admin")
@Namespace("/admin")
public class LoginAction extends ActionSupport{
	private static final long serialVersionUID = -1681627584146722465L;
	
	@Setter
	private String username;
	@Setter
	private String password;

	@Action(value = "login", results = { @Result(name = "success", location = "/WEB-INF/views/admin/login.jsp") })
	public String articleList() {
		return ActionSupport.SUCCESS;
	}

	@Action(value = "auth", results = { @Result(name = "success", location = "/WEB-INF/views/admin/home.jsp"),
									    @Result(name = "login", location = "/WEB-INF/views/admin/login.jsp") })
	public String Authenticate() {
		// TODO Add Authenticate logic
		if(false) {
			addActionError(Message.USER_OR_PASSWORD_WRONG);
			return LOGIN;
		}
		
		return SUCCESS;
	}
}
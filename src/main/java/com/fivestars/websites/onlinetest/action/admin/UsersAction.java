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
import com.fivestars.websites.onlinetest.model.User;
import com.fivestars.websites.onlinetest.service.UserService;
import com.opensymphony.xwork2.ActionSupport;

@ParentPackage("admin")
@Namespace("/admin")
public class UsersAction{
	@Autowired
	private UserService userService;
	
	@Action(value = "users", results = { @Result(name = "success", location = "/WEB-INF/views/admin/mgmt/users.jsp") })
	public String users() throws JsonProcessingException {
		List<User> users = userService.findAll();
		ObjectMapper json = new ObjectMapper();
		ServletActionContext.getRequest().setAttribute("users", json.writeValueAsString(users));
		return ActionSupport.SUCCESS;
	}
	
}
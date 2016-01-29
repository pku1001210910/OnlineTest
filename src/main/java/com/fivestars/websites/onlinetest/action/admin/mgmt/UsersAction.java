package com.fivestars.websites.onlinetest.action.admin.mgmt;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fivestars.websites.onlinetest.service.UserService;
import com.opensymphony.xwork2.ActionSupport;

@ParentPackage("admin")
@InterceptorRef(value="global")
@Namespace("/admin/users")
public class UsersAction{
	private String userName;
	
	@Autowired
	private UserService userService;
	
	@Action(value = "remove", results = { @Result(name="success", type = "json")})
	public String removeUser() throws JsonProcessingException {
		userService.delete(userName);
		return ActionSupport.SUCCESS;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
}
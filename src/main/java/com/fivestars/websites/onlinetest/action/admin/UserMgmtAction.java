package com.fivestars.websites.onlinetest.action.admin;


import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import com.opensymphony.xwork2.ActionSupport;

@ParentPackage("admin")
@Namespace("/admin")
public class UserMgmtAction {
	
	@Action(value = "users", results = { @Result(name = "success", location = "/WEB-INF/views/admin/users.jsp") })
	public String home() {
		return ActionSupport.SUCCESS;
	}
}
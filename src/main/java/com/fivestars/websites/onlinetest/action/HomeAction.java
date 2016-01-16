package com.fivestars.websites.onlinetest.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import com.opensymphony.xwork2.ActionSupport;

@ParentPackage("basePackage")
@Namespace("/")
@Result(name="success", location="/WEB-INF/views/index.jsp")
public class HomeAction {

	@Action("home")
	public String home() {
		return ActionSupport.SUCCESS;
	}
}
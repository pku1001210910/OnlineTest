package com.fivestars.websites.onlinetest.action.admin.mgmt;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;

import com.fivestars.websites.onlinetest.service.UserService;

@ParentPackage("adminAjax")
@Namespace("/admin")
public class UsersAction{
	@Autowired
	private UserService userService;
}
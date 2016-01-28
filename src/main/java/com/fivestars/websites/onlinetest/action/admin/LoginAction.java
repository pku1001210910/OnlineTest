package com.fivestars.websites.onlinetest.action.admin;

import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.fivestars.websites.onlinetest.constant.Message;
import com.fivestars.websites.onlinetest.constant.SessionConst;
import com.fivestars.websites.onlinetest.constant.UserConst;
import com.fivestars.websites.onlinetest.model.User;
import com.fivestars.websites.onlinetest.service.UserService;
import com.opensymphony.xwork2.ActionSupport;

import lombok.Setter;

@ParentPackage("admin")
@Namespace("/admin")
public class LoginAction extends ActionSupport {
	private static final long serialVersionUID = -1681627584146722465L;

	@Setter
	private String username;
	@Setter
	private String password;

	@Autowired
	private UserService userService;

	@Action(value = "login", results = { @Result(name = "success", location = "/WEB-INF/views/admin/login.jsp") })
	public String login() {
		return ActionSupport.SUCCESS;
	}

	@Action(value = "auth", results = { @Result(name = "success", type = "redirectAction", location = "home"),
			@Result(name = "login", location = "/WEB-INF/views/admin/login.jsp") })
	public String Authenticate() {
		if (username == null || password == null) {
			return LOGIN;
		}

		User admin = userService.loadByNameAndPwd(username, password);
		if (admin == null || admin.getIsAdmin() == null || admin.getIsAdmin() == UserConst.IS_NOT_ADMIN) {
			addActionError(Message.USER_OR_PASSWORD_WRONG);
			return LOGIN;
		}

		Map<String, Object> session = ServletActionContext.getContext().getSession();
		session.put(SessionConst.ADMIN, admin);
		return SUCCESS;
	}

	@Action(value = "logout", interceptorRefs = {@InterceptorRef(value="global")}, results = { @Result(name = "success", location = "/WEB-INF/views/admin/login.jsp") })
	public String logout() {
		Map<String, Object> session = ServletActionContext.getContext().getSession();
		session.remove(SessionConst.ADMIN);
		return SUCCESS;
	}
}
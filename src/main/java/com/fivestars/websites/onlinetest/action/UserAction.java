package com.fivestars.websites.onlinetest.action;

import java.util.Map;

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

import lombok.Data;

@ParentPackage("user")
@Namespace("/user")
@Data
public class UserAction {
	private String userName;
	private String userPw;
	private String passwordConfirm;
	private String email;
	private String phone;
	private String graduate;
	private String major;

	private String message;
	private String path;

	@Autowired
	private UserService userService;

	@Action(value = "userReg", results = { @Result(name = "success", type = "redirectAction", params = { "namespace", "/user" }, location = "home") })
	public String userReg() {
		message = "";
		boolean existUser = userService.isExist(userName);
		Map<String, Object> session = ServletActionContext.getContext().getSession();

		if (existUser) {
			session.put("error", "用户名已存在");
		} else {
			User user = new User();
			user.setUserName(userName);
			user.setPassword(userPw);
			user.setEmail(email);
			user.setPhone(phone);
			user.setGraduate(graduate);
			user.setMajor(major);

			userService.save(user);
			session.put("user", user);
		}
		return ActionSupport.SUCCESS;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Action(value = "userLogin", results = { @Result(name = "success", type = "redirectAction", params = { "namespace", "/" }, location = "home") })
	public String userLogin() {
		User user = userService.loadByNameAndPwd(userName, userPw);
		if (user == null) {
			Map session = ServletActionContext.getContext().getSession();
			session.put("error", "用户名或密码错误");
		} else {
			Map session = ServletActionContext.getContext().getSession();
			session.put("user", user);
		}
		return ActionSupport.SUCCESS;
	}

	@SuppressWarnings("rawtypes")
	@Action(value = "userLogout", results = { @Result(name = "success", type = "redirectAction", params = { "namespace", "/" }, location = "home") })
	public String userLogout() {
		Map session = ServletActionContext.getContext().getSession();
		session.remove("user");
		return ActionSupport.SUCCESS;
	}

	@Action(value = "home", results = { @Result(name = "success", location = "/WEB-INF/views/user/userInfo.jsp"),
			@Result(name = "login", type = "redirectAction", params = { "namespace", "/" }, location = "home") })
	public String home() throws JsonProcessingException {
		Map<String, Object> session = ServletActionContext.getContext().getSession();
		User user = (User) session.get("user");
		if (user == null) {
			return ActionSupport.LOGIN;
		}

		user = userService.loadByName(user.getUserName());

		ObjectMapper objectMapper = new ObjectMapper();
		ServletActionContext.getRequest().setAttribute("json", objectMapper.writeValueAsString(new User(user)));
		return ActionSupport.SUCCESS;
	}

	@Action(value = "password", results = { @Result(name = "success", location = "/WEB-INF/views/user/password.jsp"),
			@Result(name = "login", type = "redirectAction", params = { "namespace", "/" }, location = "home") })
	public String password() throws JsonProcessingException {
		Map<String, Object> session = ServletActionContext.getContext().getSession();
		User user = (User) session.get("user");
		if (user == null) {
			return ActionSupport.LOGIN;
		}

		user = userService.loadByName(user.getUserName());

		ObjectMapper objectMapper = new ObjectMapper();
		ServletActionContext.getRequest().setAttribute("json", objectMapper.writeValueAsString(new User(user)));
		return ActionSupport.SUCCESS;
	}
}

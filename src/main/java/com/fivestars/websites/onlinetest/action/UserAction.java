package com.fivestars.websites.onlinetest.action;

import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

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

	private String message;
	private String path;

	@Autowired
	private UserService userService;

	@Action(value = "userReg", results = { @Result(name = "successAddUser", location = "/views/article/article.jsp") })
	public String userReg() {
		User user = new User();
		user.setUserName(userName);
		user.setPassword(userPw);

		boolean existUser = userService.isExist(userName);

		if (existUser) {
			System.out.println("exist");
			return "erroExistUser";
		} else {
			System.out.println("add user");
			userService.save(user);
			Map session = ServletActionContext.getContext().getSession();
			session.put("user", user);
			return "successAddUser";
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Action(value = "userLogin", results = { @Result(name = "succeed", location = "/WEB-INF/views/index.jsp") })
	public String userLogin() {
		User user = userService.loadByNameAndPwd(userName, userPw);
		if (user == null) {
			// TODO set request Attribute. This one would cause mvn install failure
			// ServletActionContext.getRequest().setAttribute("error", "用户名或密码错误");
		} else {
			Map session = ServletActionContext.getContext().getSession();
			session.put("user", user);
		}
		return "succeed";
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Action(value = "userRegPage", results = {
			@Result(name = "succeed", location = "/WEB-INF/views/user/userReg.jsp") })
	public String userRegPage() {
		return "succeed";
	}

	public String userLogout() {
		Map session = ServletActionContext.getContext().getSession();
		session.remove("user");
		return ActionSupport.SUCCESS;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPw() {
		return userPw;
	}

	public void setUserPw(String userPw) {
		this.userPw = userPw;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

}

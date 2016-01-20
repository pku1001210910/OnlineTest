package com.fivestars.websites.onlinetest.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.fivestars.websites.onlinetest.service.UserService;

import lombok.Data;

@ParentPackage("userAjax")
@Namespace("/user")
@Data
public class UserAjaxAction {
	private String userName;
	private String userPw;
	private boolean existUser;

	@Autowired
	private UserService userService;

	@Action(value = "checkUserNameUnique", results = { @Result(name="success", type = "json")})
	public String checkUserNameUnique() {
		existUser = userService.isExist(userName);
		return "success";
	}

	public String getUserName() {
		return userName;
	}

	public boolean isExistUser() {
		return existUser;
	}

	public void setExistUser(boolean existUser) {
		this.existUser = existUser;
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

}

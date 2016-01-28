package com.fivestars.websites.onlinetest.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fivestars.websites.onlinetest.constant.Message;
import com.fivestars.websites.onlinetest.model.User;
import com.fivestars.websites.onlinetest.service.UserService;
import com.opensymphony.xwork2.ActionSupport;

@ParentPackage("user")
@InterceptorRef(value="global")
public class UserAjaxAction {
	private String userName;
	private String userPw;
	private boolean existUser;

	private String passwordConfirm;
	private String password;
	private String passwordOld;
	private String email;
	private String phone;
	private String graduate;
	private String major;

	private String error;
	private String message;

	@Autowired
	private UserService userService;

	@Action(value = "checkUserNameUnique", results = { @Result(name = "success", type = "json") })
	public String checkUserNameUnique() {
		existUser = userService.isExist(userName);
		return "success";
	}

	@Action(value = "update", interceptorRefs = {@InterceptorRef(value="global")}, results = { @Result(name = "success", type = "json") })
	public String update() throws JsonProcessingException {
		User user = userService.loadByName(userName);
		user.setEmail(email);
		user.setPhone(phone);
		user.setGraduate(graduate);
		user.setMajor(major);
		userService.saveOrUpdate(user);

		return ActionSupport.SUCCESS;
	}

	@Action(value = "updatePwd", interceptorRefs = {@InterceptorRef(value="global")}, results = { @Result(name = "success", type = "json") })
	public String updatePwd() throws JsonProcessingException {
		User user = userService.loadByName(userName);

		if (passwordOld == null || password == null || passwordConfirm == null) {
			error = Message.MUST_FILL_ALL;
			return ActionSupport.SUCCESS;
		} else if (!user.getPassword().equals(passwordOld)) {
			error = Message.OLD_PASSWORD_WRONG;
		} else if (!password.equals(passwordConfirm)) {
			error = Message.PASSWORD_NOT_MATCH;
		}

		if (error == null) {
			message = Message.UPDATE_SUCCEED;
			user.setPassword(password);
			userService.saveOrUpdate(user);
		}
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

	public boolean isExistUser() {
		return existUser;
	}

	public void setExistUser(boolean existUser) {
		this.existUser = existUser;
	}

	public String getPasswordConfirm() {
		return passwordConfirm;
	}

	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordOld() {
		return passwordOld;
	}

	public void setPasswordOld(String passwordOld) {
		this.passwordOld = passwordOld;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getGraduate() {
		return graduate;
	}

	public void setGraduate(String graduate) {
		this.graduate = graduate;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}

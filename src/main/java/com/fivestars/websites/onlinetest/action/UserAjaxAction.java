package com.fivestars.websites.onlinetest.action;


import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fivestars.websites.onlinetest.constant.Message;
import com.fivestars.websites.onlinetest.model.User;
import com.fivestars.websites.onlinetest.service.UserService;
import com.opensymphony.xwork2.ActionSupport;

import lombok.Data;

@ParentPackage("userAjax")
@Namespace("/user")
@Data
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

	@Action(value = "checkUserNameUnique", results = { @Result(name="success", type = "json")})
	public String checkUserNameUnique() {
		existUser = userService.isExist(userName);
		return "success";
	}
	
	@Action(value = "update", results = { @Result(name="success", type = "json")})
	public String update() throws JsonProcessingException {
		User user = userService.loadByName(userName);
		user.setEmail(email);
		user.setPhone(phone);
		user.setGraduate(graduate);
		user.setMajor(major);
		userService.saveOrUpdate(user);
		
		return ActionSupport.SUCCESS;
	}
	
	@Action(value = "updatePwd", results = { @Result(name="success", type = "json")})
	public String updatePwd() throws JsonProcessingException {
		User user = userService.loadByName(userName);
		
		if(passwordOld == null || password == null || passwordConfirm == null) {
			error = Message.MUST_FILL_ALL;
			return ActionSupport.SUCCESS;
		} else if(!user.getPassword().equals(passwordOld)) {
			error = Message.OLD_PASSWORD_WRONG;
		} else if(!password.equals(passwordConfirm)) {
			error = Message.PASSWORD_NOT_MATCH;
		}
		
		if(error == null) {
			message = Message.UPDATE_SUCCEED;
			user.setPassword(password);
			userService.saveOrUpdate(user);
		}
		return ActionSupport.SUCCESS;
	}
}

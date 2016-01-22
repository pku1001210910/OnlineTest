package com.fivestars.websites.onlinetest.action.admin.mgmt;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.factory.annotation.Autowired;

import com.fivestars.websites.onlinetest.model.QuizCategory;
import com.fivestars.websites.onlinetest.service.QuizService;
import com.opensymphony.xwork2.ActionSupport;

@ParentPackage("json-default")
@Namespace("/admin")
public class QuizzesAjaxAction {
	
	@Autowired
	private QuizService quizService;
	
	private List<QuizCategory> allCategories;
	
	@JSON
	public List<QuizCategory> getAllCategories() {
		return allCategories;
	}

	public void setAllCategories(List<QuizCategory> allCategories) {
		this.allCategories = allCategories;
	}
	
	@Action(value = "getAllQuizCategories", results = { @Result(name="success", type = "json")})
	public String getAllQuizCategories() {
		allCategories = quizService.getAllQuizCategories();
		return ActionSupport.SUCCESS;
	}
}

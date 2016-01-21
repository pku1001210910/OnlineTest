package com.fivestars.websites.onlinetest.action.quiz;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.fivestars.websites.onlinetest.model.QuizCategory;
import com.fivestars.websites.onlinetest.service.QuizService;
import com.opensymphony.xwork2.ActionSupport;

import lombok.Data;

@ParentPackage("quiz")
@Namespace("/quiz")
@Data
public class QuizAction {
	
	public List<QuizCategory> getCategoryList() {
		return categoryList;
	}



	public void setCategoryList(List<QuizCategory> categoryList) {
		this.categoryList = categoryList;
	}



	@Autowired
	private QuizService quizService;
	
	private List<QuizCategory> categoryList;
	
	

	@Action(value = "startQuiz", results = { @Result(name = "success", location = "/WEB-INF/views/quiz/quizlist.jsp") })
	public String startQuiz() {
		categoryList = quizService.getAllQuizCategories();
		return ActionSupport.SUCCESS;
	}
	
}

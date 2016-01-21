package com.fivestars.websites.onlinetest.action.quiz;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.fivestars.websites.onlinetest.service.QuizService;
import com.opensymphony.xwork2.ActionSupport;

import lombok.Data;

@ParentPackage("quiz")
@Namespace("/quiz")
@Data
public class QuizAction {
	
	@Autowired
	private QuizService quizService;

	@Action(value = "startQuiz", results = { @Result(name = "success", location = "/WEB-INF/views/quiz/quizlist.jsp") })
	public String startQuiz() {
		return ActionSupport.SUCCESS;
	}
	
}

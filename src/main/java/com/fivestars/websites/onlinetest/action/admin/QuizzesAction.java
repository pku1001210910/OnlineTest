package com.fivestars.websites.onlinetest.action.admin;


import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fivestars.websites.onlinetest.model.Quiz;
import com.fivestars.websites.onlinetest.service.QuizService;
import com.opensymphony.xwork2.ActionSupport;

@ParentPackage("admin")
@Namespace("/admin")
public class QuizzesAction{
	@Autowired
	private QuizService quizService;
	
	@Action(value = "quizzes", results = { @Result(name = "success", location = "/WEB-INF/views/admin/mgmt/quizzes.jsp") })
	public String quizzes() throws JsonProcessingException {
		List<Quiz> quizzes = quizService.loadAllQuiz();
		ObjectMapper json = new ObjectMapper();
		ServletActionContext.getRequest().setAttribute("quizzes", json.writeValueAsString(quizzes));
		return ActionSupport.SUCCESS;
	}
}
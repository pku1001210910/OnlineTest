package com.fivestars.websites.onlinetest.action.admin.mgmt;


import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fivestars.websites.onlinetest.constant.QuizConst;
import com.fivestars.websites.onlinetest.model.Quiz;
import com.fivestars.websites.onlinetest.model.QuizCategory;
import com.fivestars.websites.onlinetest.service.QuizService;
import com.opensymphony.xwork2.ActionSupport;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@ParentPackage("admin")
@Namespace("/admin")
public class QuizzesAction{
	
	@Autowired
	private QuizService quizService;
	
	@Action(value = "quizzes", results = { @Result(name = "success", location = "/WEB-INF/views/admin/mgmt/quizzes.jsp") })
	public String quizzes() throws JsonProcessingException {
		List<Quiz> quizzes = quizService.loadAllQuiz();
		JSONArray jsonArray = new JSONArray(); 
		for (Quiz quiz : quizzes) {
			Integer categoryId = quiz.getCategory();
			QuizCategory category = quizService.getQuizCategoryById(categoryId);
			String categoryName = "";
			if (category != null) {
				categoryName = category.getCategoryName();
			}
			
			Map<String, Object> quizMap = new HashMap<>();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
			quizMap.put("quizId", quiz.getQuizId());
			quizMap.put("title", quiz.getTitle());
			quizMap.put("description", quiz.getDescription());
			quizMap.put("category", categoryName);
			quizMap.put("submitDate", formatter.format(quiz.getSubmitDate()));
			quizMap.put("status", quiz.getStatus() == QuizConst.STATUS_TEMPORARY_SAVE ? "未发布" : "已发布");
			quizMap.put("price", quiz.getPrice() + " 元");
			JSONObject quizJson = JSONObject.fromObject(quizMap);
			jsonArray.add(quizJson);
		}
		ServletActionContext.getRequest().setAttribute("quizzes", jsonArray.toString());
		return ActionSupport.SUCCESS;
	}
}
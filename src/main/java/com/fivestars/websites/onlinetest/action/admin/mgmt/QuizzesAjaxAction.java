package com.fivestars.websites.onlinetest.action.admin.mgmt;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.factory.annotation.Autowired;

import com.fivestars.websites.onlinetest.constant.QuizConst;
import com.fivestars.websites.onlinetest.model.Quiz;
import com.fivestars.websites.onlinetest.model.QuizCategory;
import com.fivestars.websites.onlinetest.service.QuizService;
import com.opensymphony.xwork2.ActionSupport;

import lombok.Data;
import net.sf.json.JSONObject;

@ParentPackage("json-default")
@Namespace("/admin")
@Data
public class QuizzesAjaxAction implements ServletRequestAware {
	
	@Autowired
	private QuizService quizService;
	
	private HttpServletRequest request;
	
	// return value
	private List<QuizCategory> allCategories;
	private String result;
	
	@JSON
	public List<QuizCategory> getAllCategories() {
		return allCategories;
	}
	public void setAllCategories(List<QuizCategory> allCategories) {
		this.allCategories = allCategories;
	}
	
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	@Override
	public void setServletRequest(HttpServletRequest arg0) {
		this.request = arg0;
	}
	
	@Action(value = "getAllQuizCategories", results = { @Result(name="success", type = "json")})
	public String getAllQuizCategories() {
		allCategories = quizService.getAllQuizCategories();
		return ActionSupport.SUCCESS;
	}
	
	@Action(value = "createQuiz", results = { @Result(name="success", type="json")})
	public String createQuiz() {
		String title = request.getParameter("title");
		String description = request.getParameter("description");
		Integer category = Integer.parseInt(request.getParameter("category"));
		Byte needCharge = Byte.parseByte(request.getParameter("needCharge"));
		Double price = Double.parseDouble(request.getParameter("price") != null ? request.getParameter("price") : "0");
		Quiz quiz = new Quiz(title, description, category, needCharge, price, new Date(),
				QuizConst.REPEATABLE_TRUE, QuizConst.STATUS_TEMPORARY_SAVE, null);
		Integer quizId = quizService.createQuiz(quiz);
		Map<String, Integer> resultMap = new HashMap<>();
		resultMap.put("quizId", quizId);
		result = JSONObject.fromObject(resultMap).toString();
		return ActionSupport.SUCCESS;
	}
}

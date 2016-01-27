package com.fivestars.websites.onlinetest.action.admin.mgmt;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.factory.annotation.Autowired;

import com.fivestars.websites.onlinetest.constant.QuizConst;
import com.fivestars.websites.onlinetest.model.Feedback;
import com.fivestars.websites.onlinetest.model.Quiz;
import com.fivestars.websites.onlinetest.model.QuizCategory;
import com.fivestars.websites.onlinetest.model.QuizSubject;
import com.fivestars.websites.onlinetest.model.SubjectItem;
import com.fivestars.websites.onlinetest.service.FeedbackService;
import com.fivestars.websites.onlinetest.service.QuizService;
import com.opensymphony.xwork2.ActionSupport;

import lombok.Data;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@ParentPackage("admin")
@Namespace("/admin")
@InterceptorRef(value="global")
@Data
public class QuizzesAjaxAction implements ServletRequestAware {
	
	@Autowired
	private QuizService quizService;
	@Autowired
	private FeedbackService feedbackService;
	
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
	
	@Action(value = "publishQuiz", results = { @Result(name="success", type="json")})
	public String publishQuiz() {
		Integer quizId = Integer.parseInt(request.getParameter("quizId"));
		Quiz quiz = quizService.loadQuizById(quizId);
		quiz.setStatus(QuizConst.STATUS_SUBMITTED);
		quizService.updateQuiz(quiz);
		return ActionSupport.SUCCESS;
	}
	
	@Action(value = "updateQuizMeta", results = { @Result(name="success", type="json")})
	public String updateQuizMeta() {
		Integer quizId = Integer.parseInt(request.getParameter("quizId"));
		String title = request.getParameter("title");
		String description = request.getParameter("description");
		Byte needCharge = Byte.parseByte(request.getParameter("needCharge"));
		Double price = Double.parseDouble(request.getParameter("price"));
		Quiz quiz = quizService.loadQuizById(quizId);
		quiz.setTitle(title);
		quiz.setDescription(description);
		quiz.setNeedCharge(needCharge);
		quiz.setPrice(price);
		quizService.updateQuiz(quiz);
		return ActionSupport.SUCCESS;
	}
	
	@Action(value = "updateQuizCategory", results = { @Result(name="success", type="json")})
	public String updateQuizCategory() {
		Integer quizId = Integer.parseInt(request.getParameter("quizId"));
		Integer category = Integer.parseInt(request.getParameter("category"));
		Quiz quiz = quizService.loadQuizById(quizId);
		quiz.setCategory(category);
		quizService.updateQuiz(quiz);
		return ActionSupport.SUCCESS;
	}
	
	@Action(value = "addSubject", results = { @Result(name="success", type="json")})
	public String addSubject() {
		Integer quizId = Integer.parseInt(request.getParameter("quizId"));
		Integer type = Integer.parseInt(request.getParameter("type"));
		String question = request.getParameter("question");
		QuizSubject subject = new QuizSubject();
		subject.setType(type);
		subject.setQuestion(question);
		Integer subjectId = quizService.addSubjectToQuiz(quizId, subject);
		List<Integer> itemIds = new ArrayList<>();
		
		String itemJson = request.getParameter("items");
		JSONArray itemJsonArray = JSONArray.fromObject(itemJson);
		for (int i = 0; i < itemJsonArray.size(); i++) {  
            JSONObject itemJsonObject = itemJsonArray.getJSONObject(i);
            boolean isValidItem = false;
            SubjectItem item = new SubjectItem();
            if (itemJsonObject.get("choice") != null) {
            	item.setChoice((String) itemJsonObject.get("choice"));
            	isValidItem = true;
            }
            if (itemJsonObject.get("score") != null) {
            	item.setScore(Double.parseDouble(itemJsonObject.get("score").toString()));
            	isValidItem = true;
            }
            if (isValidItem) {
            	Integer itemId = quizService.addItemToSubject(subjectId, item);
            	itemIds.add(itemId);
            }
        }
		
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("subjectId", subjectId);
		resultMap.put("itemIds", itemIds);
		result = JSONObject.fromObject(resultMap).toString();
		return ActionSupport.SUCCESS;
	}
	
	@Action(value = "deleteSubject", results = { @Result(name="success", type="json")})
	public String deleteSubject() {
		Integer quizId = Integer.parseInt(request.getParameter("quizId"));
		Integer subjectId = Integer.parseInt(request.getParameter("subjectId"));
		quizService.deleteSubjectFromQuiz(quizId, subjectId);
		return ActionSupport.SUCCESS;
	}
	
	@Action(value = "shiftSubjectUp", results = { @Result(name="success", type="json")})
	public String shiftSubjectUp() {
		Integer quizId = Integer.parseInt(request.getParameter("quizId"));
		Integer subjectId = Integer.parseInt(request.getParameter("subjectId"));
		quizService.shiftSubjectUp(quizId, subjectId);
		return ActionSupport.SUCCESS;
	}
	
	@Action(value = "shiftSubjectDown", results = { @Result(name="success", type="json")})
	public String shiftSubjectDown() {
		Integer quizId = Integer.parseInt(request.getParameter("quizId"));
		Integer subjectId = Integer.parseInt(request.getParameter("subjectId"));
		quizService.shiftSubjectDown(quizId, subjectId);
		return ActionSupport.SUCCESS;
	}
	
	@Action(value = "updateSubjectQuestion", results = { @Result(name="success", type="json")})
	public String updateSubjectQuestion() {
		Integer subjectId = Integer.parseInt(request.getParameter("subjectId"));
		String question = request.getParameter("question");
		QuizSubject subject = quizService.loadQuizSubjectById(subjectId);
		subject.setQuestion(question);
		quizService.updateQuizSubject(subject);
		return ActionSupport.SUCCESS;
	}

	@Action(value = "addItem", results = { @Result(name="success", type="json")})
	public String addItem() {
		Integer subjectId = Integer.parseInt(request.getParameter("subjectId"));
		String choice = request.getParameter("choice");
		Double score = Double.parseDouble(request.getParameter("score").toString());
		SubjectItem item = new SubjectItem();
		item.setChoice(choice);
		item.setScore(score);
		Integer itemId = quizService.addItemToSubject(subjectId, item);
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("itemId", itemId);
		result = JSONObject.fromObject(resultMap).toString();
		return ActionSupport.SUCCESS;
	}
	
	@Action(value = "deleteItem", results = { @Result(name="success", type="json")})
	public String deleteItem() {
		Integer subjectId = Integer.parseInt(request.getParameter("subjectId"));
		Integer itemId = Integer.parseInt(request.getParameter("itemId"));
		quizService.deleteItemFromSubject(subjectId, itemId);
		return ActionSupport.SUCCESS;
	}
	
	@Action(value = "updateItemChoice", results = { @Result(name="success", type="json")})
	public String updateItemChoice() {
		Integer itemId = Integer.parseInt(request.getParameter("itemId"));
		String choice = request.getParameter("choice");
		SubjectItem item = quizService.loadSubjectItemById(itemId);
		item.setChoice(choice);
		quizService.updateSubjectItem(item);
		return ActionSupport.SUCCESS;
	}
	
	@Action(value = "updateItemScore", results = { @Result(name="success", type="json")})
	public String updateItemScore() {
		Integer itemId = Integer.parseInt(request.getParameter("itemId"));
		Double score =  Double.parseDouble(request.getParameter("score").toString());
		SubjectItem item = quizService.loadSubjectItemById(itemId);
		item.setScore(score);
		quizService.updateSubjectItem(item);
		return ActionSupport.SUCCESS;
	}
	
	@Action(value = "shiftItemDown", results = { @Result(name="success", type="json")})
	public String shiftItemDown() {
		Integer subjectId = Integer.parseInt(request.getParameter("subjectId"));
		Integer itemId = Integer.parseInt(request.getParameter("itemId"));
		quizService.shiftItemDown(subjectId, itemId);
		return ActionSupport.SUCCESS;
	}
	
	@Action(value = "shiftItemUp", results = { @Result(name="success", type="json")})
	public String shiftItemUp() {
		Integer subjectId = Integer.parseInt(request.getParameter("subjectId"));
		Integer itemId = Integer.parseInt(request.getParameter("itemId"));
		quizService.shiftItemUp(subjectId, itemId);
		return ActionSupport.SUCCESS;
	}
	
	@Action(value = "addFeedback", results = { @Result(name="success", type="json")})
	public String addFeedback() {
		Integer quizId = Integer.parseInt(request.getParameter("quizId"));
		String content = request.getParameter("content");
		Double scoreFrom = Double.parseDouble(request.getParameter("scoreFrom").toString());
		Double scoreTo = Double.parseDouble(request.getParameter("scoreTo").toString());
		Feedback feedback = new Feedback(quizId, content, scoreFrom, scoreTo);
		Integer feedbackId = feedbackService.createFeedback(feedback);
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("feedbackId", feedbackId);
		result = JSONObject.fromObject(resultMap).toString();
		return ActionSupport.SUCCESS;
	}
	
	@Action(value = "updateFeedback", results = { @Result(name="success", type="json")})
	public String updateFeedback() {
		Integer feedbackId = Integer.parseInt(request.getParameter("feedbackId"));
		Feedback feedback = feedbackService.getFeedbackById(feedbackId);
		String content = request.getParameter("content");
		Double scoreFrom = Double.parseDouble(request.getParameter("scoreFrom").toString());
		Double scoreTo = Double.parseDouble(request.getParameter("scoreTo").toString());
		feedback.setContent(content);
		feedback.setScoreFrom(scoreFrom);
		feedback.setScoreTo(scoreTo);
		feedbackService.updateFeedback(feedback);
		return ActionSupport.SUCCESS;
	}
	
	@Action(value = "deleteFeedback", results = { @Result(name="success", type="json")})
	public String deleteFeedback() {
		Integer feedbackId = Integer.parseInt(request.getParameter("feedbackId"));
		feedbackService.deleteFeedback(feedbackId);
		return ActionSupport.SUCCESS;
	}
}

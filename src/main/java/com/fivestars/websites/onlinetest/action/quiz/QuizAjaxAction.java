package com.fivestars.websites.onlinetest.action.quiz;

import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fivestars.websites.onlinetest.constant.QuizConst;
import com.fivestars.websites.onlinetest.entity.SingleChoiceAnswerEntity;
import com.fivestars.websites.onlinetest.model.QuizOwnership;
import com.fivestars.websites.onlinetest.model.SubjectItem;
import com.fivestars.websites.onlinetest.model.User;
import com.fivestars.websites.onlinetest.model.UserAnswer;
import com.fivestars.websites.onlinetest.model.UserQuiz;
import com.fivestars.websites.onlinetest.service.FeedbackService;
import com.fivestars.websites.onlinetest.service.QuizService;
import com.fivestars.websites.onlinetest.service.UserQuizService;
import com.opensymphony.xwork2.ActionSupport;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@ParentPackage("quizAjax")
public class QuizAjaxAction {
	private String userName;
	private int quizId;
	private boolean quizValid;
	private String subjectItemList;
	private boolean finishFlg;
	private String feedbackContent;

	
	private ObjectMapper mapper = new ObjectMapper();
	@Autowired
	private UserQuizService userQuizService;
	
	@Autowired
	private FeedbackService feedbackService;
	
	@Autowired
	private QuizService quizService;

	@Action(value = "checkUserQuizOwner", results = { @Result(name = "success", type = "json") })
	public String checkUserNameUnique() {
		quizValid = userQuizService.isUserOwnQuiz(quizId, userName);
		return ActionSupport.SUCCESS;
	}
	
	@Action(value = "buyQuiz", results = { @Result(name = "success", type = "json") })
	public String buyQuiz() {
		//TODO check already bought
		QuizOwnership ownership = new QuizOwnership();
		ownership.setUserName(userName);
		ownership.setQuizId(quizId);
		ownership.setBuyDate(new Date());
		ownership.setExpired(QuizConst.EXPIRED_FALSE);
		Integer ownershipId = userQuizService.addUserQuizOwnership(ownership);
		if(ownershipId != null) {
			quizValid = true;
		}
		return ActionSupport.SUCCESS;
	}

	@Action(value = "finishQuiz", results = { @Result(name = "success", type = "json") })
	public String finishQuiz() {
        JSONArray array = JSONArray.fromObject(subjectItemList);  
        Object[] objs = new Object[array.size()];  
        for(int i = 0; i < array.size(); i++){           
            JSONObject jsonObject = array.getJSONObject(i);           
            objs[i] = JSONObject.toBean(jsonObject, UserAnswer.class);           
        }           
        
        Map<String, Object> session = ServletActionContext.getContext().getSession();
		User user = (User) session.get("user");
		UserQuiz userQuiz = new UserQuiz();
		userQuiz.setUserName(user.getUserName());
		userQuiz.setQuizId(quizId);
		userQuiz.setQuizDate(new Date());
		userQuiz.setEvaluator("test_teacher");
		Set<UserAnswer> userAnswerSet = new HashSet<>();
		userQuiz.setUserAnswers(userAnswerSet);
		double totalScore = 0.0D;
        for(int i = 0; i < objs.length; i++){  
        	UserAnswer answer = new UserAnswer();
    		answer.setUserName(user.getUserName());
    		answer.setQuizId(quizId);
    		answer.setSubjectId(((UserAnswer)objs[i]).getSubjectId());
    		SingleChoiceAnswerEntity singleChoice = new SingleChoiceAnswerEntity(Integer.parseInt(((UserAnswer)objs[i]).getAnswer()));
    		try {
				answer.setAnswer(mapper.writeValueAsString(singleChoice));
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
    		answer.setAnswerDate(new Date());
    		SubjectItem subjectItem = quizService.loadSubjectItemById(singleChoice.getItemId());
    		Double score = subjectItem.getScore();
    		answer.setScore(score);
    		answer.setUserQuiz(userQuiz);
    		userAnswerSet.add(answer);
    		totalScore += score;
        }
        
        userQuiz.setScore(totalScore);
        Integer feedbackId = feedbackService.getFeedbackByScore(quizId, totalScore);
        userQuiz.setFeedbackId(feedbackId);
        Integer recordId = userQuizService.createUserQuiz(userQuiz);
        feedbackContent = feedbackService.getFeedbackById(feedbackId).getContent();
        finishFlg = true;
		return ActionSupport.SUCCESS;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getQuizId() {
		return quizId;
	}

	public void setQuizId(int quizId) {
		this.quizId = quizId;
	}

	public boolean isQuizValid() {
		return quizValid;
	}

	public void setQuizValid(boolean quizValid) {
		this.quizValid = quizValid;
	}

	public String getSubjectItemList() {
		return subjectItemList;
	}

	public void setSubjectItemList(String subjectItemList) {
		this.subjectItemList = subjectItemList;
	}

	public boolean isFinishFlg() {
		return finishFlg;
	}

	public void setFinishFlg(boolean finishFlg) {
		this.finishFlg = finishFlg;
	}

	public String getFeedbackContent() {
		return feedbackContent;
	}

	public void setFeedbackContent(String feedbackContent) {
		this.feedbackContent = feedbackContent;
	}

}

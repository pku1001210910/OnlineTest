package com.fivestars.websites.onlinetest.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fivestars.websites.onlinetest.model.Quiz;
import com.fivestars.websites.onlinetest.model.QuizCategory;
import com.fivestars.websites.onlinetest.constant.SessionConst;
import com.fivestars.websites.onlinetest.model.User;
import com.fivestars.websites.onlinetest.model.UserQuiz;
import com.fivestars.websites.onlinetest.service.QuizService;
import com.fivestars.websites.onlinetest.service.UserQuizService;
import com.fivestars.websites.onlinetest.service.UserService;
import com.fivestars.websites.onlinetest.vo.UserQuizVo;
import com.opensymphony.xwork2.ActionSupport;

@ParentPackage("user")
@Namespace("/user")
public class UserAction {
	private String userName;

	private String userPw;
	private String passwordConfirm;
	private String email;
	private String phone;
	private String graduate;
	private String major;

	private String message;
	private String path;
	
	
	private int curPageNum;
	private int pageSize = 10;
	private int totalPage;
	
	private int firstPageNum = 1;
	private boolean beginMore;
	private int pageNum1;
	private int pageNum2;
	private int pageNum3;
	private int pageNum4;
	private int pageNum5;
	private boolean lastMore;
	private int lastPageNum;
	
	List<UserQuizVo> userQuizVoList;

	@Autowired
	private UserService userService;
	
	@Autowired
	private UserQuizService userQuizService;
	
	@Autowired
	private QuizService quizService;

	@Action(value = "userReg", results = { @Result(name = "success", type = "redirectAction", params = { "namespace", "/user" }, location = "home") })
	public String userReg() {
		message = "";
		boolean existUser = userService.isExist(userName);
		Map<String, Object> session = ServletActionContext.getContext().getSession();

		if (existUser) {
			session.put("error", "用户名已存在");
		} else {
			User user = new User();
			user.setUserName(userName);
			user.setPassword(userPw);
			user.setEmail(email);
			user.setPhone(phone);
			user.setGraduate(graduate);
			user.setMajor(major);

			userService.save(user);
			session.put(SessionConst.USER, user);
		}
		return ActionSupport.SUCCESS;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Action(value = "userLogin", results = { @Result(name = "success", type = "redirectAction", params = { "namespace", "/" }, location = "home") })
	public String userLogin() {
		User user = userService.loadByNameAndPwd(userName, userPw);
		if (user == null) {
			Map session = ServletActionContext.getContext().getSession();
			session.put("error", "用户名或密码错误");
		} else {
			Map session = ServletActionContext.getContext().getSession();
			session.put(SessionConst.USER, user);
		}
		return ActionSupport.SUCCESS;
	}

	@SuppressWarnings("rawtypes")
	@Action(value = "userLogout", interceptorRefs = { @InterceptorRef(value = "global") }, results = {
			@Result(name = "success", type = "redirectAction", params = { "namespace", "/" }, location = "home") })
	public String userLogout() {
		Map session = ServletActionContext.getContext().getSession();
		session.remove(SessionConst.USER);
		return ActionSupport.SUCCESS;
	}

	@Action(value = "home", interceptorRefs = {@InterceptorRef(value="global")}, results = { @Result(name = "success", location = "/WEB-INF/views/user/userInfo.jsp"),
			@Result(name = "login", type = "redirectAction", params = { "namespace", "/" }, location = "home") })
	public String home() throws JsonProcessingException {
		Map<String, Object> session = ServletActionContext.getContext().getSession();
		User user = (User) session.get(SessionConst.USER);
		user = userService.loadByName(user.getUserName());

		ObjectMapper objectMapper = new ObjectMapper();
		ServletActionContext.getRequest().setAttribute("json", objectMapper.writeValueAsString(new User(user)));
		return ActionSupport.SUCCESS;
	}

	@Action(value = "password", interceptorRefs = { @InterceptorRef(value = "global") }, results = { @Result(name = "success", location = "/WEB-INF/views/user/password.jsp"),
			@Result(name = "login", type = "redirectAction", params = { "namespace", "/" }, location = "home") })
	public String password() throws JsonProcessingException {
		Map<String, Object> session = ServletActionContext.getContext().getSession();
		User user = (User) session.get(SessionConst.USER);
		user = userService.loadByName(user.getUserName());

		ObjectMapper objectMapper = new ObjectMapper();
		ServletActionContext.getRequest().setAttribute("json", objectMapper.writeValueAsString(new User(user)));
		return ActionSupport.SUCCESS;
	}
	
	@Action(value = "userquiz", results = { @Result(name = "success", location = "/WEB-INF/views/user/quizlist.jsp"),
			@Result(name = "login", type = "redirectAction", params = { "namespace", "/" }, location = "home") })
	public String userquiz() throws JsonProcessingException {
		Map<String, Object> session = ServletActionContext.getContext().getSession();
		User user = (User) session.get("user");
		if (user == null) {
			return ActionSupport.LOGIN;
		}
		int totalNum = userQuizService.getUserParticipatedQuizSize(user.getUserName());
		totalPage = totalNum / pageSize + (totalNum % pageSize > 0 ? 1 : 0);
		preparePageNum(curPageNum, totalPage);
		List<UserQuiz> userQuizList = userQuizService.getUserParticipatedQuiz(user.getUserName(), curPageNum, pageSize);
		
		List<Integer> quizIdList = new ArrayList<Integer>();
		for(UserQuiz userQuiz: userQuizList) {
			quizIdList.add(userQuiz.getQuizId());
		}
		List<Quiz> quizList = quizService.loadQuizzesByIds(quizIdList);
		userQuizVoList = new ArrayList<UserQuizVo>();
		
		for(int index = 0; index < userQuizList.size(); index++){
			UserQuiz userQuiz = userQuizList.get(index);
			for(int quizIndex = 0; quizIndex < quizList.size(); quizIndex++) {
				if(quizList.get(quizIndex).getQuizId() == userQuiz.getQuizId()) {
					userQuizVoList.add(new UserQuizVo(userQuiz, quizList.get(quizIndex)));
					break;
				}
			}
		}
		
		List<QuizCategory> categoryList = quizService.getAllQuizCategories();
		for(UserQuizVo vo : userQuizVoList) {
			for(int categoryIndex = 0; categoryIndex < categoryList.size(); categoryIndex++) {
				if(vo.getQuiz().getCategory().equals(categoryList.get(categoryIndex).getCategoryId())) {
					vo.setQuizCategory(categoryList.get(categoryIndex));
					break;
				}
			}
		}
		
		user = userService.loadByName(user.getUserName());
		ObjectMapper objectMapper = new ObjectMapper();
		ServletActionContext.getRequest().setAttribute("json", objectMapper.writeValueAsString(new User(user)));
		return ActionSupport.SUCCESS;
	}
	
	@Action(value = "quizDetail", results = { @Result(name = "success", location = "/WEB-INF/views/user/quizdetail.jsp"),
			@Result(name = "login", type = "redirectAction", params = { "namespace", "/" }, location = "home") })
	public String quizDetail() throws JsonProcessingException {
		Map<String, Object> session = ServletActionContext.getContext().getSession();
		User user = (User) session.get("user");
		if (user == null) {
			return ActionSupport.LOGIN;
		}
		user = userService.loadByName(user.getUserName());
		ObjectMapper objectMapper = new ObjectMapper();
		ServletActionContext.getRequest().setAttribute("json", objectMapper.writeValueAsString(new User(user)));
		
		
		// TODO quiz detail
		return ActionSupport.SUCCESS;
	}
	
	private void preparePageNum(int curPageNum, int totalPage) {
		if (curPageNum == 0) {
			curPageNum = 1;
			this.curPageNum = 1;
		}
		lastPageNum = totalPage;
		pageNum1 = curPageNum - 2;
		pageNum2 = curPageNum - 1;
		pageNum3 = curPageNum;
		pageNum4 = curPageNum + 1;
		pageNum5 = curPageNum + 2;
		beginMore = pageNum1 > firstPageNum + 1 ? true : false;
		lastMore = pageNum5 < lastPageNum - 1 ? true : false;
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

	public String getPasswordConfirm() {
		return passwordConfirm;
	}

	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
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

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public int getCurPageNum() {
		return curPageNum;
	}

	public void setCurPageNum(int curPageNum) {
		this.curPageNum = curPageNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getFirstPageNum() {
		return firstPageNum;
	}

	public void setFirstPageNum(int firstPageNum) {
		this.firstPageNum = firstPageNum;
	}

	public boolean isBeginMore() {
		return beginMore;
	}

	public void setBeginMore(boolean beginMore) {
		this.beginMore = beginMore;
	}

	public int getPageNum1() {
		return pageNum1;
	}

	public void setPageNum1(int pageNum1) {
		this.pageNum1 = pageNum1;
	}

	public int getPageNum2() {
		return pageNum2;
	}

	public void setPageNum2(int pageNum2) {
		this.pageNum2 = pageNum2;
	}

	public int getPageNum3() {
		return pageNum3;
	}

	public void setPageNum3(int pageNum3) {
		this.pageNum3 = pageNum3;
	}

	public int getPageNum4() {
		return pageNum4;
	}

	public void setPageNum4(int pageNum4) {
		this.pageNum4 = pageNum4;
	}

	public int getPageNum5() {
		return pageNum5;
	}

	public void setPageNum5(int pageNum5) {
		this.pageNum5 = pageNum5;
	}

	public boolean isLastMore() {
		return lastMore;
	}

	public void setLastMore(boolean lastMore) {
		this.lastMore = lastMore;
	}

	public int getLastPageNum() {
		return lastPageNum;
	}

	public void setLastPageNum(int lastPageNum) {
		this.lastPageNum = lastPageNum;
	}

	public List<UserQuizVo> getUserQuizVoList() {
		return userQuizVoList;
	}

	public void setUserQuizVoList(List<UserQuizVo> userQuizVoList) {
		this.userQuizVoList = userQuizVoList;
	}

}

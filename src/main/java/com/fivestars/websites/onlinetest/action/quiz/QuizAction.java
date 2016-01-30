package com.fivestars.websites.onlinetest.action.quiz;

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

import com.fivestars.websites.onlinetest.constant.CategoryConst;
import com.fivestars.websites.onlinetest.constant.QuizConst;
import com.fivestars.websites.onlinetest.constant.SessionConst;
import com.fivestars.websites.onlinetest.model.Quiz;
import com.fivestars.websites.onlinetest.model.QuizCategory;
import com.fivestars.websites.onlinetest.model.QuizOwnership;
import com.fivestars.websites.onlinetest.model.User;
import com.fivestars.websites.onlinetest.service.QuizService;
import com.fivestars.websites.onlinetest.service.UserQuizService;
import com.fivestars.websites.onlinetest.vo.QuizVo;
import com.opensymphony.xwork2.ActionSupport;

@ParentPackage("quiz")
@Namespace("/quiz")
public class QuizAction {

	@Autowired
	private QuizService quizService;
	
	@Autowired
	private UserQuizService userQuizService;
	
	private List<QuizCategory> categoryList;
	private List<Quiz> quizList;
	private List<QuizVo> quizVoList;
	private String userName;
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

	private int categoryId = CategoryConst.TYPE_ALL;
	private String categoryName;
	
	private int quizId;
	private Quiz quiz;
	

	@Action(value = "startQuiz", results = { @Result(name = "success", location = "/WEB-INF/views/quiz/quizlist.jsp") })
	public String startQuiz() {
		Map<String, Object> session = ServletActionContext.getContext().getSession();
		User user = (User) session.get(SessionConst.USER);
		categoryList = quizService.getAllQuizCategories();
		int totalNum = quizService.getAllSubmittedQuizSize(categoryId, null);
		totalPage = totalNum / pageSize + (totalNum % pageSize > 0 ? 1 : 0);
		preparePageNum(curPageNum, totalPage);
		if(categoryId != CategoryConst.TYPE_ALL) {
			categoryName = quizService.loadQuizCategoryById(categoryId).getCategoryName();
		} else {
			categoryName = CategoryConst.TYPE_ALL_LABEL;
		}
		quizList = quizService.loadAllSubmittedQuiz(categoryId, null, curPageNum, pageSize);
		quizVoList = new ArrayList<QuizVo>();
		for(Quiz quiz : quizList) {
			for(QuizCategory quizCategory : categoryList) {
				if(quiz.getCategory().equals(CategoryConst.TYPE_ALL)) {
					quizVoList.add(new QuizVo(quiz, new QuizCategory(-2, CategoryConst.TYPE_ALL_LABEL, null, null)));
					break;
				} else if(quiz.getCategory().equals(quizCategory.getCategoryId())){
					quizVoList.add(new QuizVo(quiz, quizCategory));
					break;
				}
				
			}
		}
		if(user != null) {
			List<QuizOwnership> quizOwnerShipList = userQuizService.loadQuizOwnershipList(user.getUserName());
			for(QuizOwnership quizOwnership: quizOwnerShipList) {
				for(QuizVo quizVo : quizVoList) {
					if(quizOwnership.getQuizId().equals(quizVo.getQuiz().getQuizId())) {
						quizVo.setQuizOwnership(quizOwnership);
						break;
					}
				}
			}
		}
		return ActionSupport.SUCCESS;
	}
	
	@Action(value = "quizDetail", interceptorRefs = {@InterceptorRef(value="global")}, results = { @Result(name = "success", location = "/WEB-INF/views/quiz/quizdetail.jsp"), @Result(name = "input", type="redirectAction", location = "startQuiz.action") })
	public String quizDetail() {
		Map<String, Object> session = ServletActionContext.getContext().getSession();
		User user = (User) session.get(SessionConst.USER);
		boolean quizValid = userQuizService.isUserOwnQuiz(quizId, user.getUserName());
		quiz = quizService.loadQuizById(quizId);
		if(quiz.getNeedCharge() == null || quiz.getNeedCharge().equals(QuizConst.NOT_NEED_CHARGE)) {
			quizValid = true;
		}
		if(!quizValid) {
			return ActionSupport.INPUT;
		} else {
			return ActionSupport.SUCCESS;
		}
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

	public int getCategoryId() {
		return categoryId;
	}

	public List<QuizCategory> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(List<QuizCategory> categoryList) {
		this.categoryList = categoryList;
	}

	public List<Quiz> getQuizList() {
		return quizList;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public void setQuizList(List<Quiz> quizList) {
		this.quizList = quizList;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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
	
	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public int getQuizId() {
		return quizId;
	}

	public void setQuizId(int quizId) {
		this.quizId = quizId;
	}

	public Quiz getQuiz() {
		return quiz;
	}

	public void setQuiz(Quiz quiz) {
		this.quiz = quiz;
	}

	public List<QuizVo> getQuizVoList() {
		return quizVoList;
	}

	public void setQuizVoList(List<QuizVo> quizVoList) {
		this.quizVoList = quizVoList;
	}

}

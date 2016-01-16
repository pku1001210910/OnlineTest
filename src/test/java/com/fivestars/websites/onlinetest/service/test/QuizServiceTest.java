package com.fivestars.websites.onlinetest.service.test;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fivestars.websites.onlinetest.constant.QuizConst;
import com.fivestars.websites.onlinetest.model.Quiz;
import com.fivestars.websites.onlinetest.model.QuizSubject;
import com.fivestars.websites.onlinetest.model.SubjectItem;
import com.fivestars.websites.onlinetest.service.QuizService;

public class QuizServiceTest {

	private static ApplicationContext context;
	private static QuizService quizService;
	
	/**
     * set up
     */
	@BeforeClass
    public static void before() {
    	context = new ClassPathXmlApplicationContext("applicationContext.xml");
    	quizService = (QuizService) context.getBean("quizService");
    }
	
	@Test
	public void testQuiz() {
		Quiz quiz = prepareQuiz();
		Integer quizId = quizService.createQuiz(quiz);
		assertNotNull(quizId);
		
		List<Quiz> quizList = quizService.loadAllQuiz();
		assertThat(quizList.size(), equalTo(1));
		
		quizService.deleteQuiz(quizId);
		assertThat(quizService.loadAllQuiz().size(), equalTo(0));
	}
	
	private Quiz prepareQuiz() {
		Quiz quiz = new Quiz();
		quiz.setTitle("第一套心理测试题");
		quiz.setDescription("这是第一套心理测试题");
		quiz.setCategory(0);
		quiz.setNeedCharge(QuizConst.NOT_NEED_CHARGE);
		quiz.setPrice(0.0);
		quiz.setSubmitDate(new Date());
		quiz.setRepeatable(QuizConst.REPEATABLE_TRUE);
		quiz.setStatus(QuizConst.STATUS_SUBMITTED);
		List<QuizSubject> subjects = new ArrayList<>();
		quiz.setQuizSubjects(subjects);
		
		QuizSubject firstSubject = new QuizSubject();
		subjects.add(firstSubject);
		firstSubject.setQuiz(quiz);
		firstSubject.setType(QuizConst.TYPE_SINGLE_CHOICE);
		firstSubject.setResourceId(0);
		firstSubject.setSubjectOrder(0);
		firstSubject.setQuestion("一个生鸡蛋可以放在四个环境里，水，口袋，树上，土里，请问你怎么放？");
		List<SubjectItem> options = new ArrayList<>();
		firstSubject.setSubjectItems(options);
		
		SubjectItem firstOption = new SubjectItem(firstSubject, "水", Double.parseDouble("10"), null);
		options.add(firstOption);
		SubjectItem secondOption = new SubjectItem(firstSubject, "口袋", Double.parseDouble("20"), null);
		options.add(secondOption);
		SubjectItem thirdOption = new SubjectItem(firstSubject, "树上", Double.parseDouble("30"), null);
		options.add(thirdOption);
		SubjectItem fourthOption = new SubjectItem(firstSubject, "土里", Double.parseDouble("40"), null);
		options.add(fourthOption);
		
		return quiz;
	}

}

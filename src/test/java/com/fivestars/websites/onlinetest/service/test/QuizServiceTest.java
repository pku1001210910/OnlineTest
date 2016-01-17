package com.fivestars.websites.onlinetest.service.test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
	public void testAddDeleteSubject() {
		Quiz quiz = prepareQuiz();
		Integer quizId = quizService.createQuiz(quiz);
		assertNotNull(quizId);
		Quiz quizInDB = quizService.loadQuizById(quizId);
		assertThat(quizInDB.getQuizSubjects().size(), equalTo(1));
		QuizSubject firstSubject = quizInDB.getQuizSubjects().iterator().next();
		Integer firstSubjectId = firstSubject.getSubjectId();
		
		// test add subject 
		QuizSubject secondSubject = prepareSubject(quiz);
		quizService.addSubjectToQuiz(quizId, secondSubject);
		quizInDB = quizService.loadQuizById(quizId);
		assertThat(quizInDB.getQuizSubjects().size(), equalTo(2));
		Set<QuizSubject> subjectSet = quizInDB.getQuizSubjects();
		QuizSubject secondSubjectInDB = null;
		for (QuizSubject subject : subjectSet) {
			if (subject.getQuestion().equals("吃西餐最先动那一道?")) {
				secondSubjectInDB = subject;
				break;
			}
		}
		assertNotNull(secondSubjectInDB);
		Integer secondSubjectId = secondSubjectInDB.getSubjectId();
		assertThat(secondSubjectInDB.getSubjectOrder(), equalTo(1));
		
		// test delete subject
		quizService.deleteSubjectFromQuiz(quizId, firstSubjectId);
		quizInDB = quizService.loadQuizById(quizId);
		assertThat(quizInDB.getQuizSubjects().size(), equalTo(1));
		secondSubjectInDB = quizInDB.getQuizSubjects().iterator().next();
		assertThat(secondSubjectInDB.getSubjectId(), equalTo(secondSubjectId));
		assertThat(secondSubjectInDB.getSubjectOrder(), equalTo(0));
		
		quizService.deleteQuiz(quizId);
	}
	
	@Test
	public void testShiftSubject() {
		Quiz quiz = prepareQuiz();
		Integer quizId = quizService.createQuiz(quiz);
		assertNotNull(quizId);
		QuizSubject secondSubject = prepareSubject(quiz);
		quizService.addSubjectToQuiz(quizId, secondSubject);
		Quiz quizInDB = quizService.loadQuizById(quizId);
		Integer secondSubjectId = null;
		for (QuizSubject subject : quizInDB.getQuizSubjects()) {
			if (subject.getQuestion().equals(secondSubject.getQuestion())) {
				assertThat(subject.getSubjectOrder(), equalTo(1));
				secondSubjectId = subject.getSubjectId();
				assertNotNull(secondSubjectId);
			} else {
				assertThat(subject.getSubjectOrder(), equalTo(0));
			}
		}
		// test shift up
		quizService.shiftSubjectUp(quizId, secondSubjectId);
		quizInDB = quizService.loadQuizById(quizId);
		for (QuizSubject subject : quizInDB.getQuizSubjects()) {
			if (subject.getQuestion().equals(secondSubject.getQuestion())) {
				assertThat(subject.getSubjectOrder(), equalTo(0));
			} else {
				assertThat(subject.getSubjectOrder(), equalTo(1));
			}
		}
		// test shift down
		quizService.shiftSubjectDown(quizId, secondSubjectId);
		quizInDB = quizService.loadQuizById(quizId);
		for (QuizSubject subject : quizInDB.getQuizSubjects()) {
			if (subject.getQuestion().equals(secondSubject.getQuestion())) {
				assertThat(subject.getSubjectOrder(), equalTo(1));
			} else {
				assertThat(subject.getSubjectOrder(), equalTo(0));
			}
		}
		
		quizService.deleteQuiz(quizId);
	}
	
	@Test
	public void testInsertSubject() {
		Quiz quiz = prepareQuiz();
		Integer quizId = quizService.createQuiz(quiz);
		assertNotNull(quizId);		
		Quiz quizInDB = quizService.loadQuizById(quizId);
		QuizSubject existSubject = quizInDB.getQuizSubjects().iterator().next();
		
		QuizSubject newSubject = prepareSubject(quiz);
		quizService.insertSubjectAt(quizId, newSubject, 0);
		quizInDB = quizService.loadQuizById(quizId);
		assertThat(quizInDB.getQuizSubjects().size(), equalTo(2));
		for (QuizSubject subject : quizInDB.getQuizSubjects()) {
			if (subject.getSubjectId().equals(existSubject.getSubjectId())) {
				assertThat(subject.getSubjectOrder(), equalTo(1));
			} else {
				assertThat(subject.getSubjectOrder(), equalTo(0));
			}
		}
		
		quizService.deleteQuiz(quizId);
	}
	
	@Test
	public void testQuiz() {
		Quiz quiz = prepareQuiz();
		Integer quizId = quizService.createQuiz(quiz);
		assertNotNull(quizId);
		
		List<Quiz> quizList = quizService.loadAllQuiz();
		assertThat(quizList.size(), greaterThan(0));
		
		Quiz quizInDB = quizService.loadQuizById(quizId);
		assertThat(quizInDB.getTitle(), equalTo(quiz.getTitle()));
		
		String newTitle = "新的心理测试题";
		quiz.setTitle(newTitle);
		quizService.updateQuiz(quiz);
		quizInDB = quizService.loadQuizById(quizId);
		assertThat(quizInDB.getTitle(), equalTo(newTitle));
		
		quizService.deleteQuiz(quizId);
		assertNull(quizService.loadQuizById(quizId));
	}
	
	private QuizSubject prepareSubject(Quiz quiz) {
		QuizSubject subject = new QuizSubject();
		subject.setQuiz(quiz);
		subject.setType(QuizConst.TYPE_ANSWER);
		subject.setResourceId(null);
		subject.setSubjectOrder(0);
		subject.setQuestion("吃西餐最先动那一道?");
		subject.setSubjectItems(null);
		return subject;
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
		Set<QuizSubject> subjects = new HashSet<>();
		quiz.setQuizSubjects(subjects);
		
		QuizSubject firstSubject = new QuizSubject();
		subjects.add(firstSubject);
		firstSubject.setQuiz(quiz);
		firstSubject.setType(QuizConst.TYPE_SINGLE_CHOICE);
		firstSubject.setResourceId(null);
		firstSubject.setSubjectOrder(0);
		firstSubject.setQuestion("一个生鸡蛋可以放在四个环境里，水，口袋，树上，土里，请问你怎么放？");
		Set<SubjectItem> options = new HashSet<>();
		firstSubject.setSubjectItems(options);
		
		SubjectItem firstOption = new SubjectItem(firstSubject, "水", Double.parseDouble("10"), null, 0);
		options.add(firstOption);
		SubjectItem secondOption = new SubjectItem(firstSubject, "口袋", Double.parseDouble("20"), null, 1);
		options.add(secondOption);
		SubjectItem thirdOption = new SubjectItem(firstSubject, "树上", Double.parseDouble("30"), null, 2);
		options.add(thirdOption);
		SubjectItem fourthOption = new SubjectItem(firstSubject, "土里", Double.parseDouble("40"), null, 3);
		options.add(fourthOption);
		
		return quiz;
	}

}

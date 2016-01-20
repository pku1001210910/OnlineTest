package com.fivestars.websites.onlinetest.service.test;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fivestars.websites.onlinetest.model.Feedback;
import com.fivestars.websites.onlinetest.service.FeedbackService;

public class FeedbackServiceTest {

	private static ApplicationContext context;
	private static FeedbackService feedbackService;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		context = new ClassPathXmlApplicationContext("applicationContext.xml");
		feedbackService = (FeedbackService) context.getBean("feedbackService");
	}

	@Test
	public void testFeedback() {
		Feedback feedback1 = new Feedback(1, "Bad", new Double(0), new Double(25));
		Feedback feedback2 = new Feedback(1, "Not Bad", new Double(26), new Double(50));
		Feedback feedback3 = new Feedback(1, "Good", new Double(51), new Double(75));
		Feedback feedback4 = new Feedback(1, "Very Good", new Double(76), new Double(100));
		Integer feedback1Id = feedbackService.createFeedback(feedback1);
		Integer feedback2Id = feedbackService.createFeedback(feedback2);
		Integer feedback3Id = feedbackService.createFeedback(feedback3);
		Integer feedback4Id = feedbackService.createFeedback(feedback4);
		
		assertThat(feedbackService.getFeedbackByScore(1, new Double(80)), equalTo(feedback4Id));
		assertThat(feedbackService.getFeedbackByScore(1, new Double(0)), equalTo(feedback1Id));
		assertThat(feedbackService.getFeedbackByScore(1, new Double(33)), equalTo(feedback2Id));
		assertThat(feedbackService.getFeedbackByScore(1, new Double(75)), equalTo(feedback3Id));
		
		feedbackService.deleteFeedback(feedback1Id);
		feedbackService.deleteFeedback(feedback2Id);
		feedbackService.deleteFeedback(feedback3Id);
		feedbackService.deleteFeedback(feedback4Id);
		
		assertNull(feedbackService.getFeedbackById(feedback1Id));
	}

}

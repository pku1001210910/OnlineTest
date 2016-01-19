package com.fivestars.websites.onlinetest.service.test;


import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fivestars.websites.onlinetest.constant.UserConst;
import com.fivestars.websites.onlinetest.model.User;
import com.fivestars.websites.onlinetest.service.UserService;

public class UserTest {

	private static ApplicationContext context;
	private static UserService userService;
	
	/**
     * set up
     */
	@BeforeClass
    public static void before() {
    	context = new ClassPathXmlApplicationContext("applicationContext.xml");
    	userService = (UserService) context.getBean("userService");
    }
	
	@Test
	public void testInsertUsers() {
		for(int i = 0; i < 100; i++) {
			User user = new User();
			user.setUserName("user" + i);
			user.setPassword("test");
			user.setEmail("user"+i + "@163.com");
			user.setGraduate("中国某某大学" + (i % 10));
			user.setMajor("专业" + (i % 12));
			user.setPhone("131210312"+ i % 100);
			user.setBackground("专业水平");
			user.setIsAdmin((i % 10) == 0 ? UserConst.IS_ADMIN : UserConst.IS_NOT_ADMIN);
			user.setIsTeacher(UserConst.IS_NOT_TEACHER);
			
			// save if not exists
			if(!userService.isExist(user.getUserName())) {
				userService.save(user);
			}
		}
	}
}

package com.fivestars.websites.onlinetest.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fivestars.websites.onlinetest.dao.UserDAO;


public class TestEnv {
	private ApplicationContext ac;
	private UserDAO userDao;

    /**
     * set up
     */
    @Before
    public void before() {
        ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        userDao = (UserDAO) ac.getBean("userDao");
    }

    @After
    public void after() {
    }
    
	@Test
	public void testSpring() {
		// run test method
	}
}
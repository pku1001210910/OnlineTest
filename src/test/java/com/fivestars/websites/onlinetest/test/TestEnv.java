package com.fivestars.websites.onlinetest.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class TestEnv {
	private ApplicationContext ac;

    /**
     * set up
     */
    @Before
    public void before(){
        ac = new ClassPathXmlApplicationContext(new String[]{"spring.xml","spring-hibernate.xml"});
    }

    @After
    public void after() {
    }
    
	@Test
	public void testSpring() {
		// run test method
	}
    
    @Test
    public void testSaveMethod(){
//        Admin admin = new Admin();
//        admin.setName("tes1");
//        admin.setPwd("1232");
//        adminService.save(admin);
    }
}
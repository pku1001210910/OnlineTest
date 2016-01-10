package com.fivestars.websites.onlinetest.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fivestars.websites.onlinetest.model.Admin;
import com.fivestars.websites.onlinetest.service.AdminService;

public class TestEnv {
	private AdminService adminService;
	private ApplicationContext ac;

    /**
     * set up
     */
    @Before
    public void before(){
        ac = new ClassPathXmlApplicationContext(new String[]{"spring.xml","spring-hibernate.xml"});
        adminService = (AdminService) ac.getBean("adminService");
    }

    @After
    public void after() {
    }
    
	@Test
	public void testSpring() {
		// run test method
		adminService.test();
	}
    
    @Test
    public void testSaveMethod(){
        Admin admin = new Admin();
        admin.setName("tes1");
        admin.setPwd("1232");
        adminService.save(admin);
    }
}
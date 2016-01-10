package com.fivestars.websites.onlinetest.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;

import com.fivestars.websites.onlinetest.service.AdminService;

@ParentPackage("basePackage")
@Action(value="login")
@Namespace("/")
public class LoginAction {
    
    /**
     * userService
     */
    @Autowired
    private AdminService adminService;

    /**
     * http://localhost:8080/onlinetest/login!test.action
     * MethodName: test
     */
    public void test(){
        System.out.println("TestAction");
        adminService.test();
    }
}
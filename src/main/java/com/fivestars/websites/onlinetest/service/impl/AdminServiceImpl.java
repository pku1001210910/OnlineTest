package com.fivestars.websites.onlinetest.service.impl;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fivestars.websites.onlinetest.dao.AdminDao;
import com.fivestars.websites.onlinetest.model.Admin;
import com.fivestars.websites.onlinetest.service.AdminService;

@Service("adminService")
public class AdminServiceImpl implements AdminService {
	@Autowired
	private AdminDao adminDao;
	
    /**
     * only for testing
     */
    public void test() {
    	System.out.println("Hello World");
    }

	public Serializable save(Admin admin) {
		return adminDao.save(admin);
	}
}
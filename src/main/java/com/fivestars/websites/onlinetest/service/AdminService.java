package com.fivestars.websites.onlinetest.service;

import java.io.Serializable;

import com.fivestars.websites.onlinetest.model.Admin;

public interface AdminService {

    /**
     * only for testing
     */
    public void test();
    
    /**
     * save admin
     * @param user
     * @return
     */
    Serializable save(Admin admin); 
}
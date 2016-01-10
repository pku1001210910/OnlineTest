package com.fivestars.websites.onlinetest.dao.impl;

import java.io.Serializable;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fivestars.websites.onlinetest.dao.AdminDao;
import com.fivestars.websites.onlinetest.model.Admin;

@Repository("adminDao")
public class AdminDaoImpl implements AdminDao{
	
    @Autowired
    private SessionFactory sessionFactory;
    
	/**
	 * 
	 * save Admin to DB
     * @param user
     * @return
     */
    public Serializable save(Admin admin) {
    	return sessionFactory.getCurrentSession().save(admin);
    }
}

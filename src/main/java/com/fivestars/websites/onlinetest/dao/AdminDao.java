package com.fivestars.websites.onlinetest.dao;

import java.io.Serializable;

import com.fivestars.websites.onlinetest.model.Admin;

public interface AdminDao {
	/**
	 * 
	 * save Admin to DB
     * @param user
     * @return
     */
    public Serializable save(Admin admin); 
}

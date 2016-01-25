package com.fivestars.websites.onlinetest.service;

import java.io.Serializable;
import java.util.List;

import com.fivestars.websites.onlinetest.model.User;

public interface UserService {
	/**
	 * 
	 * save user
     * @param user
     * @return
     */
    public Serializable save(User user); 
    
    public void saveOrUpdate(User user);
    
    /**
     * load all
     * @return
     */
    public List<User> findAll();

	public User loadByName(String userName);
	
	public boolean isExist(String userName);
	
	public User loadByNameAndPwd(String userName, String pwd);
}
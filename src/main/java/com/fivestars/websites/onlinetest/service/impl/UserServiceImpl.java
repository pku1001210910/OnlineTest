package com.fivestars.websites.onlinetest.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fivestars.websites.onlinetest.dao.UserDAO;
import com.fivestars.websites.onlinetest.model.User;
import com.fivestars.websites.onlinetest.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDAO userDao;

	public Serializable save(User user) {
		return userDao.save(user);
	}

	@Override
	public List<User> findAll() {
		return userDao.listAll();
	}

	@Override
	public User loadByName(String name) {
		return userDao.load(name);
	}
	
	@Override
	public boolean isExist(String name) {
		return userDao.isExist(name);
	}
	
	@Override
	public User loadByNameAndPwd(String userName, String pwd) {
		boolean exist = userDao.isExist(userName);
		if(!exist) {
			return null;
		}
		User existUser = userDao.load(userName);
		// TODO º”√‹/Ω‚√‹ √‹¬Î
		if(existUser.getPassword().equals(pwd)) {
			return existUser;
		} else {
			return null;
		}
	}
}
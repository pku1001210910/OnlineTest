package com.fivestars.websites.onlinetest.dao.impl;

import org.springframework.stereotype.Repository;

import com.fivestars.websites.onlinetest.dao.UserDAO;
import com.fivestars.websites.onlinetest.model.User;

@Repository("userDao")
public class UserDAOImpl extends GenericDAOImpl<User, String> implements UserDAO {

}

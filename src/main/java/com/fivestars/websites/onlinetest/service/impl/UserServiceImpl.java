package com.fivestars.websites.onlinetest.service.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fivestars.websites.onlinetest.constant.UserConst;
import com.fivestars.websites.onlinetest.dao.UserDAO;
import com.fivestars.websites.onlinetest.model.User;
import com.fivestars.websites.onlinetest.service.UserService;

@Transactional
@Service("userService")
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDAO userDao;

	public Serializable save(User user) {
		return userDao.save(user);
	}
	
	public void saveOrUpdate(User user) {
		userDao.saveOrUpdate(user);
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
		if(existUser.getPassword().equals(pwd)) {
			return existUser;
		} else {
			return null;
		}
	}

	@Override
	public List<User> loadAllUsers() {
		DetachedCriteria criteria = DetachedCriteria.forClass(User.class);
		Criterion isNull = Restrictions.isNull("isAdmin");
		Criterion isUser = Restrictions.eq("isAdmin", UserConst.IS_NOT_ADMIN);
		criteria.add(Restrictions.or(isNull, isUser));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return userDao.listSome(criteria);
	}

	@Override
	public List<User> loadAllAdmins() {
		DetachedCriteria criteria = DetachedCriteria.forClass(User.class);
		Criterion isAdminEq = Restrictions.eq("isAdmin", UserConst.IS_ADMIN);
		criteria.add(isAdminEq);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return userDao.listSome(criteria);
	}

	@Override
	public void delete(String userName) {
		userDao.delete(userName);
	}
}
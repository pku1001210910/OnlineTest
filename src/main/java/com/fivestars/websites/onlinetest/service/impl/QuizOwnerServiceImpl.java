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

import com.fivestars.websites.onlinetest.dao.QuizOwnershipDAO;
import com.fivestars.websites.onlinetest.model.QuizOwnership;
import com.fivestars.websites.onlinetest.service.QuizOwnerService;

@Transactional
@Service("quizOwnerService")
public class QuizOwnerServiceImpl implements QuizOwnerService {
	@Autowired
	private QuizOwnershipDAO ownershipDao;
	
	public Serializable save(QuizOwnership ownership) {
		return ownershipDao.save(ownership);
	}

	public void saveOrUpdate(QuizOwnership ownership) {
		 ownershipDao.saveOrUpdate(ownership);
	}

	public List<QuizOwnership> loadByUser(String userName) {
		DetachedCriteria criteria = DetachedCriteria.forClass(QuizOwnership.class);
		Criterion byUserName = Restrictions.eq("userName", userName);
		criteria.add(byUserName);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return ownershipDao.listSome(criteria);

	}

	public void saveOrUpdateBatch(List<QuizOwnership> ownerships) {
		for(QuizOwnership each : ownerships) {
			saveOrUpdate(each);
		}
	}

	@Override
	public void delete(Integer ownershipId) {
		ownershipDao.delete(ownershipId);
	}
}
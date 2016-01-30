package com.fivestars.websites.onlinetest.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fivestars.websites.onlinetest.constant.CategoryConst;
import com.fivestars.websites.onlinetest.constant.QuizConst;
import com.fivestars.websites.onlinetest.dao.QuizOwnershipDAO;
import com.fivestars.websites.onlinetest.dao.UserAnswerDAO;
import com.fivestars.websites.onlinetest.dao.UserQuizDAO;
import com.fivestars.websites.onlinetest.model.Quiz;
import com.fivestars.websites.onlinetest.model.QuizOwnership;
import com.fivestars.websites.onlinetest.model.UserAnswer;
import com.fivestars.websites.onlinetest.model.UserQuiz;
import com.fivestars.websites.onlinetest.service.UserQuizService;

@Transactional
@Service("userQuizService")
public class UserQuizServiceImpl implements UserQuizService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserQuizServiceImpl.class);
	
	@Autowired
	private UserQuizDAO userQuizDao;
	@Autowired
	private UserAnswerDAO userAnswerDao;
	@Autowired
	private QuizOwnershipDAO ownershipDao;
	
	@Override
	public List<UserQuiz> getUserParticipatedQuiz(String userName) {
		DetachedCriteria userCriteria = DetachedCriteria.forClass(UserQuiz.class);
		Criterion userNameEq = Restrictions.eq("userName", userName);
		userCriteria.add(userNameEq);
		userCriteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return userQuizDao.listSome(userCriteria);
	}
	
	@Override
	public List<UserQuiz> getUserParticipatedQuiz(String userName, int curPageNum, int pageSize, String orderProperty, boolean asc) {
		DetachedCriteria resultCriteria = DetachedCriteria.forClass(UserQuiz.class);
		Criterion userEq = Restrictions.eq("userName", userName);
		resultCriteria.add(userEq);
		resultCriteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		resultCriteria.setFetchMode("userAnswers", FetchMode.SELECT);
		resultCriteria.addOrder(asc ? Order.asc(orderProperty) : Order.desc(orderProperty)); 
		List<UserQuiz> userQuizList = userQuizDao.pagedQuery(resultCriteria, curPageNum, pageSize);
		LOGGER.info("[UserQuizService]Successfully load all userQuiz, useName = {}, curPageNum = {}, pageSize = {}, orderProperty = {}, asc = {}", userName, curPageNum, pageSize, orderProperty, asc);
		return userQuizList;
	}
	
	@Override
	public List<UserQuiz> getUserParticipatedQuiz(String userName, int curPageNum, int pageSize) {
		DetachedCriteria resultCriteria = DetachedCriteria.forClass(UserQuiz.class);
		Criterion userEq = Restrictions.eq("userName", userName);
		resultCriteria.add(userEq);
		resultCriteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		resultCriteria.setFetchMode("userAnswers", FetchMode.SELECT);
		List<UserQuiz> userQuizList = userQuizDao.pagedQuery(resultCriteria, curPageNum, pageSize);
		LOGGER.info("[UserQuizService]Successfully load all userQuiz, useName = {}, curPageNum = {}, pageSize = {}", userName, curPageNum, pageSize);
		return userQuizList;
	}
	
	@Override
	public Integer getUserParticipatedQuizSize(String userName) {
		DetachedCriteria resultCriteria = DetachedCriteria.forClass(UserQuiz.class);
		Criterion userNameEq = Restrictions.eq("userName", userName);
		resultCriteria.add(userNameEq);
		resultCriteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		Integer allQuizSize = userQuizDao.countSome(resultCriteria);
		return allQuizSize;
	}

	@Override
	public Integer createUserQuiz(UserQuiz userQuiz) {
		Integer recordId = userQuizDao.save(userQuiz);
		LOGGER.info("[UserQuizService]Successfully created user quiz record of id " + recordId);
		return recordId;
	}

	@Override
	public void updateUserQuiz(UserQuiz userQuiz) {
		userQuizDao.saveOrUpdate(userQuiz);
		LOGGER.info("[UserQuizService]Successfully updated user quiz record of id " + userQuiz.getRecordId());
	}

	@Override
	public void deleteUserQuiz(Integer recordId) {
		userQuizDao.delete(recordId);
		LOGGER.info("[UserQuizService]Successfully delete user quiz record of id " + recordId);
	}

	@Override
	public UserQuiz loadUserQuizById(Integer recordId) {
		return userQuizDao.get(recordId);
	}

	@Override
	public UserAnswer getAnswerBySubject(Integer recordId, Integer subjectId) {
		UserQuiz userQuiz = userQuizDao.load(recordId);
		for (UserAnswer userAnswer : userQuiz.getUserAnswers()) {
			if (userAnswer.getSubjectId().equals(subjectId)) {
				return userAnswer;
			}
		}
		LOGGER.warn("[UserQuizService]Cannot find answer for subject " + subjectId + " in the user quiz record " + recordId);
		return null;
	}

	@Override
	public void updateUserAnswer(UserAnswer userAnswer) {
		userAnswerDao.saveOrUpdate(userAnswer);
		LOGGER.info("[UserQuizService]Successfully updated user answer of id " + userAnswer.getAnswerId());
	}

	@Override
	public void gradeUserAnswer(Integer answerId, Double score) {
		UserAnswer answer = userAnswerDao.get(answerId);
		answer.setScore(score);
		updateUserAnswer(answer);
	}

	@Override
	public Integer addUserQuizOwnership(QuizOwnership ownership) {
		Integer ownershipId = ownershipDao.save(ownership);
		LOGGER.info("[UserQuizService]Successfully added user quiz ownership");
		return ownershipId;
	}

	@Override
	public void deleteUserQuizOwnership(Integer ownershipId) {
		ownershipDao.delete(ownershipId);
		LOGGER.info("[UserQuizService]Successfully deleted user quiz ownership");
	}

	@Override
	public boolean isUserOwnQuiz(Integer quizId, String userName) {
		Criterion quizIdEq = Restrictions.eq("quizId", quizId);
		Criterion userNameEq = Restrictions.eq("userName", userName);
		List<QuizOwnership> ownership = ownershipDao.listSome(new Criterion[] {quizIdEq, userNameEq});
		if (ownership.size() == 0) {
			return false;
		} else {
			QuizOwnership own = ownership.get(0);
			return own.getExpired() == null || own.getExpired() == QuizConst.EXPIRED_FALSE; 
		}
	}
	
	@Override
	public List<QuizOwnership> loadQuizOwnershipList(String userName) {
		Criterion userNameEq = Restrictions.eq("userName", userName);
		Criterion expiredEq = Restrictions.eq("expired", QuizConst.EXPIRED_FALSE );
		List<QuizOwnership> ownershipList = ownershipDao.listSome(new Criterion[] {userNameEq, expiredEq});
		// return ownershipList.stream().filter(ownership -> ownership.getExpired() == QuizConst.EXPIRED_FALSE || ownership.getExpired() == null).collect(Collectors.toList());
		return ownershipList;
		
	}

	

}

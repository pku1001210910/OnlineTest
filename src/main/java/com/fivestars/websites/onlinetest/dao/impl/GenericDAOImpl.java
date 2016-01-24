package com.fivestars.websites.onlinetest.dao.impl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.fivestars.websites.onlinetest.dao.GenericDAO;

/**
 * Implement the basic methods used in Hibernate DAO, every other DAO is supposed to extend
 * from this class
 *
 * @param <T>
 * @param <PK>
 */
@SuppressWarnings("unchecked")
public class GenericDAOImpl<T, PK extends java.io.Serializable> implements GenericDAO<T, PK> {

	protected static final Logger LOGGER = LoggerFactory.getLogger(GenericDAOImpl.class);
	
	@Autowired
    private SessionFactory sessionFactory;
	
	private Class<T> entityClass;
	
	public GenericDAOImpl() {
		Type type = getClass().getGenericSuperclass();
		Type[] params = ((ParameterizedType) type).getActualTypeArguments();
		entityClass = (Class<T>) params[0];
	}
	
	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	protected void clear() {
		getSession().clear();
	}
	
	protected void flush() {
		getSession().flush();
	}
	
	protected Criteria createCriteria() {
		Criteria criteria = getSession().createCriteria(entityClass);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria;
	}
	
	protected Criteria createCriteria(Criterion... criterions) {
		Criteria criteria = createCriteria();
		for (Criterion crierion : criterions) {
			criteria.add(crierion);
		}
		return criteria;
	}
	
	@Override
	public PK save(T entity) {
		return (PK) getSession().save(entity);
	}

	@Override
	public void saveOrUpdate(T entity) {
		getSession().saveOrUpdate(entity);		
	}

	@Override
	public void delete(PK id) {
		getSession().delete(get(id));
	}

	@Override
	public void delete(T entity) {
		getSession().delete(entity);		
	}

	@Override
	public void deleteSome(Collection<T> entities) {
		if(entities == null || entities.size() == 0) {
			return;
		}
		for (T entity : entities) {
			getSession().delete(entity);
		}
	}

	@Override
	public boolean isExist(PK id) {
		return get(id) != null;
	}

	@Override
	public T load(PK id) {
		return (T) getSession().load(entityClass, id);
	}

	@Override
	public T get(PK id) {
		return (T) getSession().get(entityClass, id);
	}

	@Override
	public int countAll() {
		return countSome(createCriteria());
	}

	@Override
	public int countSome(Criteria criteria) {
		return Integer.valueOf(criteria.setProjection(Projections.rowCount()).uniqueResult().toString());
	}

	@Override
	public List<T> listAll() {
		return createCriteria().list();
	}

	@Override
	public List<T> listAllWithOrder(String orderBy, boolean isAsc) {
		Criteria criteria = createCriteria();
		criteria.addOrder(isAsc ? Order.asc(orderBy) : Order.desc(orderBy));
		return criteria.list();
	}

	@Override
	public List<T> listSome(Criteria criteria) {
		return criteria.list();
	}

	@Override
	public List<T> listSome(Criterion... criterions) {
		return createCriteria(criterions).list();
	}

	@Override
	public T uniqueResult(Criteria criteria) {
		return (T) criteria.uniqueResult();
	}

	@Override
	public T uniqueResult(Criterion... criterions) {
		return uniqueResult(createCriteria(criterions));
	}

	@Override
	public List<T> pagedQuery(Criteria criteria, int pageNo, int pageSize) {
		criteria.setFirstResult((pageNo - 1) * pageSize);
		criteria.setMaxResults(pageSize);
		return listSome(criteria);
	}

	@Override
	public List<T> listSome(DetachedCriteria criteria) {
		return (List<T>) listSome(criteria.getExecutableCriteria(getSession()));
	}
}

package com.fivestars.websites.onlinetest.dao;

import java.util.Collection;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;

public interface GenericDAO<T, PK extends java.io.Serializable> {
	
	/**
	 * Save an entity of type T
	 * @param entity
	 * @return primary key of entity
	 */
	public PK save(T entity);
	
	/**
	 * Save or update an entity of type T
	 * @param entity
	 */
	public void saveOrUpdate(T entity);
	
	/**
	 * Delete the entity with id
	 * @param id
	 */
	public void delete(PK id);
	
	/**
	 * Delete the entity
	 * @param entity
	 */
	public void delete(T entity);
	
	/**
	 * Delete all the entities in parameter
	 * @param entities
	 */
	public void deleteSome(Collection<T> entities);
	
	/**
	 * Judge if entity is exist
	 * @param id
	 * @return
	 */
	public boolean isExist(PK id);
	
	/**
	 * Load an entity with id
	 * @param id
	 * @return
	 */
	public T load(PK id);
	
	/**
	 * Get an entity with id
	 * if you do not want to involve Hibernate lazy load, use get instead of load
	 * @param id
	 * @return
	 */
	public T get(PK id);
	
	/**
	 * Get the count of all entities
	 * @return
	 */
	public int countAll();
	
	/**
	 * Get the count of entities matching criteria
	 * @param criteria
	 * @return
	 */
	public int countSome(Criteria criteria);
	
	/**
	 * Get the count of entities matching criteria
	 * @return
	 */
	public int countSome(DetachedCriteria criteria);
	
	/**
	 * Get the count of entities matching criteria
	 * @return
	 */
	public int countSome(Criterion... criterions);
	
	/**
	 * List all the entities 
	 * @return
	 */
	public List<T> listAll();
	
	/**
	 * List all the entities with specified order
	 * @param orderBy
	 * @param isAsc
	 * @return
	 */
	public List<T> listAllWithOrder(String orderBy, boolean isAsc);
	
	/**
	 * List the entities which match the criteria
	 * @param criteria
	 * @return
	 */
	public List<T> listSome(Criteria criteria);
	
	/**
	 * List the entities which match the detached criteria
	 * @param criteria
	 * @return
	 */
	public List<T> listSome(DetachedCriteria criteria);
	
	/**
	 * List the entities which match the restrictions
	 * @param criterions
	 * @return
	 */
	public List<T> listSome(Criterion... criterions);
	
	/**
	 * Get the unique entity matching the criteria
	 * @param criteria
	 * @return
	 */
	public T uniqueResult(Criteria criteria);
	
	/**
	 * Get the unique entity matching the restrictions
	 * @param criterions
	 * @return
	 */
	public T uniqueResult(Criterion... criterions);
	
	/**
	 * Get the page of list matching criteria
	 * @param criteria
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public List<T> pagedQuery(Criteria criteria, int pageNo, int pageSize);

	/**
	 * Get the page of list matching criteria
	 * @param criteria
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public List<T> pagedQuery(DetachedCriteria criteria, int pageNo, int pageSize);

	/**
	 * Get the page of list matching criteria 
	 * @param criterions
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public List<T> pagedQuery(Criterion[] criterions, int pageNo, int pageSize);
}

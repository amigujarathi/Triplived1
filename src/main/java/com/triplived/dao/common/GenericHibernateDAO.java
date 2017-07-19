package com.triplived.dao.common;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate3.HibernateTemplate;



public abstract class GenericHibernateDAO<T, ID extends Serializable> implements GenericDAO<T, ID> {
	
	private Class<T> persistentClass;
	
	@SuppressWarnings("unchecked")
	public GenericHibernateDAO() {
		this.persistentClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;
	
	private HibernateTemplate hibernateTemplate;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
		this.hibernateTemplate = new HibernateTemplate(sessionFactory);
	}

	public Session getSession(){
		 return sessionFactory.getCurrentSession();
    }
	
	public Session getCurrentSession(){
		 return getSession();
   }

	public HibernateTemplate getHibernateTemplate() {
		if(hibernateTemplate == null){
			this.hibernateTemplate = new HibernateTemplate(sessionFactory);
		}
		return hibernateTemplate;
	}
	
	public Class<T> getPersistentClass() {
		return persistentClass;
	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	@Override
	public T findById(ID id, boolean lock) {
		T entity;
		if (lock)
			entity = (T) getSession().get(getPersistentClass(), id, LockMode.UPGRADE);
		else
			entity = (T) getSession().get(getPersistentClass(), id);
		return entity;
	}
	
	@Override
	public ListContainer<T> findAll() {
		return findByCriteria(0, -1, null);
	}
	
	@SuppressWarnings("unchecked")
	public List<T> findByExample(T exampleInstance, String... excludeProperty) {
		Criteria crit = getSession().createCriteria(getPersistentClass());
		Example example = Example.create(exampleInstance);
		for (String exclude : excludeProperty) {
			example.excludeProperty(exclude);
		}
		crit.add(example);
		return crit.list();
	}

	@Override
	public T saveOrUpdate(T entity) {
		getSession().saveOrUpdate(entity);
		return entity;
	}
	
	@Override
	public T save(T entity)
	{
		getSession().save(entity);
		return entity;
	}
	
	@Override
	public Collection<T> save(Collection<T> entities) {
		if (CollectionUtils.isNotEmpty(entities)) {
			for (T entity : entities) {
				getSession().save(entity);
			}
		}
		return entities;
	}
	
	@Override
	public Object saveObject(Object entity)
	{
		getSession().save(entity);
		return entity;
	}
	
	@Override
	public void updateObject(Object entity){
		getSession().update(entity);
	}
	
	@Override
	public T merge(T entity)
	{
		getSession().merge(entity);
		return entity;
	}
	
	@Override
	public void delete(T entity) {
		getSession().delete(entity);
	}
	
	public T update(T entity) {
		getSession().update(entity);
		return entity;
	}

	public void flush() {
		getSession().flush();
	}

	public void clear() {
		getSession().clear();
	}
	
	public void evict(T entity){
		getSession().evict(entity);
	}

	/**
	 * Use this inside subclasses as a convenience method.
	 */
	@SuppressWarnings("unchecked")
	public ListContainer<T> findByCriteria(int StartRow, int pageSize, Order order, Criterion... criterion) {
		ListContainer<T> listContainer = new ListContainer<T>();
		Criteria crit = getSession().createCriteria(getPersistentClass());
		for (Criterion c : criterion) {
			crit.add(c);
		} 
		int totalCount = getTotalCount(criterion);
				
		if(order != null)
		{
			crit.addOrder(order);
		}
		crit.setFirstResult(StartRow);
		if(pageSize != -1)
		{
			crit.setMaxResults(pageSize);
		}
		List<T> entityList = crit.list();
		
		listContainer.setEntityList(entityList);
		listContainer.setTotalCount(totalCount);
		
		return listContainer;
	} 
	
	
	/**
	 * Overloaded method to use when to provide dynamically multiple criterias
	 * @param StartRow
	 * @param pageSize
	 * @param order
	 * @param criterion
	 * @return
	 */
	public ListContainer<T> findByCriteria(int startRow, int pageSize, Order order, List<Criterion> criteria) {
		
		Criterion crit[]=new Criterion[criteria.size()];
		for(int i=0; i<criteria.size();i++){
			crit[i]=criteria.get(i);
		}
		
		return findByCriteria(startRow,	pageSize, order, crit);
		
	}
	
	public int getTotalCount(Criterion... criterion)
	{
		Criteria crit = getSession().createCriteria(getPersistentClass())
		.setProjection( Projections.rowCount() );
		for (Criterion c : criterion) {
			crit.add(c);
		} 
		
		return getCountAsIntegerfromCreterion(crit) ;
	}
	public Criteria createCriteria(List<Criterion> criterionList, Order order, int StartRow, int pageSize)
	{
		Criteria crit = getSession().createCriteria(getPersistentClass());
		for (Criterion c : criterionList) {
			crit.add(c);
		} 
		if(order != null)
		{
			crit.addOrder(order);
		}
		crit.setFirstResult(StartRow);
		if(pageSize != -1)
		{
			crit.setMaxResults(pageSize);
		}
		return crit;
	}
	public Criteria createCriteria(List<Criterion> criterionList, int StartRow, int pageSize, List<Order> orders)
	{
		Criteria crit = getSession().createCriteria(getPersistentClass());
		for (Criterion c : criterionList) {
			crit.add(c);
		} 
		for(Order order : orders)
		{
			crit.addOrder(order);
		}
		crit.setFirstResult(StartRow);
		if(pageSize != -1)
		{
			crit.setMaxResults(pageSize);
		}
		return crit;
	}
	
	public ListContainer<T> findByCriteria(Criteria crit, List<Criterion> criterionList) {
		@SuppressWarnings("unchecked")
		List<T> entityList = crit.list();
		int totalCount = getTotalCount(criterionList);
		ListContainer<T> listContainer = new ListContainer<T>();
		listContainer.setEntityList(entityList);
		listContainer.setTotalCount(totalCount);
		return listContainer;
	}
	
	public int getTotalCount(List<Criterion> criterionList)
	{
		Criteria crit = getSession().createCriteria(getPersistentClass())
		.setProjection( Projections.rowCount() );
		for (Criterion c : criterionList) {
			crit.add(c);
		} 
		
		return getCountAsIntegerfromCreterion(crit);
	}
	
	private int getCountAsIntegerfromCreterion(Criteria crit) {
		
		Object o =  crit.uniqueResult() ; 
		if(o != null) {
			
			if(o.getClass().equals(Long.class)) {
				
				Long totalCount = (Long) crit.uniqueResult();	
				return totalCount.intValue();
			}else {
				int totalCount = (Integer) crit.uniqueResult();		
				return totalCount;
			}
		} else{
			return 0;
		}
	}
	
	public int getTotalCount(Criteria crit)
	{
		crit.setProjection( Projections.rowCount() );
		return getCountAsIntegerfromCreterion(crit);
	
	}
	
	public int getTotalCount(Query query){
		return query.list().size();
	}

}

package com.triplived.dao.common;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * 
 * @author santosh joshi
 *
 * @param <T>
 * @param <ID>
 */

public interface GenericDAO<T, ID extends Serializable> {
	
	public T findById(ID id, boolean lock);

	public ListContainer<T> findAll();

	public List<T> findByExample(T exampleInstance, String... excludeProperty);

	public T saveOrUpdate(T entity);

	public T save(T entity);

	public T merge(T entity);

	public void delete(T entity);

	public 	void flush();

	public void clear();

	public 	void evict(T entity);

	public 	Object saveObject(Object entity);

	public 	void updateObject(Object entity);

	public T update(T entity);

	public Collection<T> save(Collection<T> entities);
}

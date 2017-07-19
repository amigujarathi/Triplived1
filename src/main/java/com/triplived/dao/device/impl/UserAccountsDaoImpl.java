package com.triplived.dao.device.impl;


import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.triplived.dao.common.GenericHibernateDAO;
import com.triplived.dao.device.UserAccountsDao;
import com.triplived.entity.UserAccountsDb;

@Repository
@Transactional("txManager")
public class UserAccountsDaoImpl extends GenericHibernateDAO<UserAccountsDb, Serializable> implements UserAccountsDao {

	/**
	 * Return a particular account associated with this email and account
	 */
	@Override
	public UserAccountsDb getUserAccount(String accountId, String email) {
		
		StringBuilder queryBuilder = new StringBuilder("Select user_dynamic from com.triplived.entity.UserAccountsDb user_dynamic where accountId = :accountId ");
		if(StringUtils.isNotEmpty(email)){
			queryBuilder.append(" and email = :email");
		}
		
		Query query = getSession().createQuery(queryBuilder.toString()).setString("accountId", accountId);
		
		if(StringUtils.isNotEmpty(email)){
			query.setString("email", email);
		}
		
		return (UserAccountsDb) query.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserAccountsDb> getUserAccountByEmails(String email) {
		
		StringBuilder queryBuilder = new StringBuilder("Select user_dynamic from com.triplived.entity.UserAccountsDb user_dynamic where email = :email");

		Query query = getSession().createQuery(queryBuilder.toString()).setString("email", email);
	
		return (List<UserAccountsDb>) query.list();
	}
	
}

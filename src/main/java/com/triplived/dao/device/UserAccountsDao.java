package com.triplived.dao.device;

import java.io.Serializable;
import java.util.List;

import com.triplived.dao.common.GenericDAO;
import com.triplived.entity.UserAccountsDb;


/**
 * 
 * @author santosh Joshi
 *
 */
public interface UserAccountsDao extends GenericDAO<UserAccountsDb, Serializable> {

	/**
	 * Returns user Account;
	 * 
	 * @param accountId
	 * @param email
	 * @return
	 */
	public UserAccountsDb getUserAccount(String accountId , String email);
	
	
	/**
	 * Get All accounts associated with an email
	 * 
	 * @param email
	 * @return
	 */
	public List<UserAccountsDb> getUserAccountByEmails(String email);

}

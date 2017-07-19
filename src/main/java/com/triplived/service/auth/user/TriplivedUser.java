/**
 * 
 */
package com.triplived.service.auth.user;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.triplived.controller.profile.Person;

/**
 * @author santosh joshi
 *
 */
public class TriplivedUser extends User {

    /**
     * @param username
     * @param password
     * @param enabled
     * @param accountNonExpired
     * @param credentialsNonExpired
     * @param accountNonLocked
     * @param authorities
     */
    public TriplivedUser(String username, String password, boolean enabled, boolean accountNonExpired,
            boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities, Person person) {
	super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
	this.person = person;
	// TODO Auto-generated constructor stub
    }
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private Person person;
    
    /**
     * @return the person
     */
    public Person getPerson() {
	return this.person;
    }
    
}

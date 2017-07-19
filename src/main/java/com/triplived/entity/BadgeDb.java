package com.triplived.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.connectme.domain.triplived.badge.BadgeType;
import com.connectme.domain.triplived.badge.BadgeType.UserBadgeType;

@Entity
@Table(name = "badge", catalog = "trip")
public class BadgeDb {

	private Long id;
	private Long personId;
	private String name;
	private String metaId;
	private UserBadgeType badgeType;
	private Date createdDate;
	
	@Column(name = "USER_ID", unique = true, nullable = false, length = 30)
	public Long getPersonId() {
		return personId;
	}
	public void setPersonId(Long personId) {
		this.personId = personId;
	}
	
	@Id
	@GeneratedValue
	@Column(name = "ID", unique = true, nullable = false, length = 30)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "META_ID")
	public String getMetaId() {
		return metaId;
	}
	
	public void setMetaId(String metaId) {
		this.metaId = metaId;
	}
	
	@Column(name = "NAME")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Enumerated(EnumType.STRING)
	@Column(name = "BADGE_TYPE", length = 20)
	public UserBadgeType getBadgeType() {
		return badgeType;
	}
	public void setBadgeType(UserBadgeType userType) {
		this.badgeType = userType;
	}
	
	@Column(name = "CREATED_DATE", nullable = false, length = 19)
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
	
}
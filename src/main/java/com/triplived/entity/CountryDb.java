package com.triplived.entity;

// Generated Mar 28, 2015 8:38:57 PM by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Country generated by hbm2java
 */
@Entity
@Table(name = "country", catalog = "trip")
public class CountryDb implements java.io.Serializable {

	private String countryCode;
	private String countryName;
	private String status;
	private String createdBy;
	private Date createdDate;
	private String updatedBy;
	private Date updatedDate;
	private Set<CityDb> cityDbs = new HashSet<CityDb>(0);

	public CountryDb() {
	}

	public CountryDb(String countryCode, String countryName, String createdBy,
			Date createdDate) {
		this.countryCode = countryCode;
		this.countryName = countryName;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
	}

	public CountryDb(String countryCode, String countryName, String status,
			String createdBy, Date createdDate, String updatedBy,
			Date updatedDate, Set<CityDb> cityDbs) {
		this.countryCode = countryCode;
		this.countryName = countryName;
		this.status = status;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.updatedBy = updatedBy;
		this.updatedDate = updatedDate;
		this.cityDbs = cityDbs;
	}

	@Id
	@Column(name = "COUNTRY_CODE", unique = true, nullable = false, length = 5)
	public String getCountryCode() {
		return this.countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	@Column(name = "COUNTRY_NAME", nullable = false, length = 50)
	public String getCountryName() {
		return this.countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	@Column(name = "STATUS", length = 2)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "CREATED_BY", nullable = false, length = 60)
	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_DATE", nullable = false, length = 19)
	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Column(name = "UPDATED_BY", length = 60)
	public String getUpdatedBy() {
		return this.updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATED_DATE", length = 19)
	public Date getUpdatedDate() {
		return this.updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "country")
	public Set<CityDb> getCities() {
		return this.cityDbs;
	}

	public void setCities(Set<CityDb> cityDbs) {
		this.cityDbs = cityDbs;
	}

}

package com.triplived.entity;

// Generated Mar 28, 2015 8:38:57 PM by Hibernate Tools 3.4.0.CR1

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * City generated by hbm2java
 */
@Entity
@Table(name = "city_weather", catalog = "trip")
public class CityWeatherDb implements java.io.Serializable {

	private String cityCode;
	private BigDecimal tempMin;
	private BigDecimal tempMax;
	private BigDecimal tempCurrent;
	private Date updateDate;
	


	@Id
	@Column(name = "CITY_CODE", unique = true, nullable = false, length = 20)
	public String getCityCode() {
		return this.cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	@Column(name = "tempMin", precision = 9, scale = 6)
	public BigDecimal getTempMin() {
		return this.tempMin;
	}

	public void setTempMin(BigDecimal tempMin) {
		this.tempMin = tempMin;
	}
	
	@Column(name = "tempMax", precision = 9, scale = 6)
	public BigDecimal getTempMax() {
		return this.tempMax;
	}

	public void setTempMax(BigDecimal tempMax) {
		this.tempMax = tempMax;
	}

	@Column(name = "tempCurrent", precision = 9, scale = 6)
	public BigDecimal getTempCurrent() {
		return this.tempCurrent;
	}

	public void setTempCurrent(BigDecimal tempCurrent) {
		this.tempCurrent = tempCurrent;
	}

	@Column(name="UPDATED_DATE")
	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}




}

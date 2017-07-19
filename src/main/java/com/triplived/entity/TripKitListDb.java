package com.triplived.entity;

import java.util.Date;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="trip_kit_list")
public class TripKitListDb {

	@Id
    @Column(name="ID")
    @GeneratedValue
	private Long id;
	
	@Column(name="TRIP_ID")
    private Long tripId;
	
	@Column(name="ITEM")
	private String item;
	
	@Column(name="UPDATED_DATE")
	private Date updateDate;
	
	@Column(name = "CREATED_DATE")
	private Date createDate;
	
    @Column(name = "ORDER_SEQ")
    private Integer orderSeq;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getTripId() {
		return tripId;
	}

	public void setTripId(Long tripId) {
		this.tripId = tripId;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Integer getOrderSeq() {
		return orderSeq;
	}

	public void setOrderSeq(Integer orderSeq) {
		this.orderSeq = orderSeq;
	}

}

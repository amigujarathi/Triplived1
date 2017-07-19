package com.triplived.entity;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="trip_categories")
public class TripCategoryDb {

	@Id
    @Column(name="ID")
    @GeneratedValue
	private Long id;
	
	@Column(name="NAME")
	private String tripCategoryName;
	
	@Column(name="STATUS")
	private String status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTripCategoryName() {
		return tripCategoryName;
	}

	public void setTripCategoryName(String tripCategoryName) {
		this.tripCategoryName = tripCategoryName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
}

package com.triplived.dao.common;

import java.util.List;

public class ListContainer<T> {
	
	List<T> entityList;
	int totalCount;
	
	public List<T> getEntityList() {
		return entityList;
	}
	public void setEntityList(List<T> entityList) {
		this.entityList = entityList;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
}

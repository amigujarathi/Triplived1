package com.triplived.service.attraction.impl;

import java.util.Date;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.connectme.domain.triplived.dto.AttractionDataUploadDTO;
import com.connectme.domain.triplived.dto.AttractionImageDTO;
import com.connectme.domain.triplived.dto.NewAttractionDataAddDTO;
import com.triplived.dao.attraction.AttractionCategoryMappingDao;
import com.triplived.dao.attraction.AttractionDao;
import com.triplived.dao.attraction.AttractionExternalDao;
import com.triplived.dao.attraction.AttractionImageDao;
import com.triplived.dao.attraction.CityDao;
import com.triplived.entity.AttractionCategoryMappingDb;
import com.triplived.entity.AttractionDb;
import com.triplived.entity.AttractionExternalDb;
import com.triplived.entity.AttractionImageDb;
import com.triplived.entity.CityDb;
import com.triplived.service.attraction.AttractionService;

@Service
public class AttractionServiceImpl implements AttractionService{
	
	@Autowired
	private AttractionDao attractionDao;
	
	
	@Autowired
	private AttractionExternalDao attractionExternalDao;

	@Autowired
	private AttractionImageDao attractionImageDoa;
	
	
	@Autowired
	private AttractionCategoryMappingDao attractionCategoryMappingDao;

	@Autowired
	private CityDao cityDao;
	
	@Autowired
	private AttractionExternalDao externalDao; 
	

	@Transactional(readOnly=false)
	@Override
	public void addAttractionImage(AttractionDataUploadDTO attrObj) {
		
		if(CollectionUtils.isNotEmpty(attrObj.getFiles())){
			for(AttractionImageDTO dto : attrObj.getFiles()) {
				AttractionImageDb obj = new AttractionImageDb();
				obj.setAttractionId(attrObj.getAttractionId());
				obj.setAttractionSrc(dto.getUrl());
					obj.setUpdatedDate(new Date());
					obj.setImageName(dto.getName());
					obj.setImageTitle(dto.getFiletitle());
					obj.setAuthor(dto.getFileAuthor());
					obj.setUpdatedBy(dto.getUpdatedBy());
					obj.setFileFrom(dto.getFileFrom());
					obj.setStatus("A");
					attractionImageDoa.addAttractionImage(obj);
				}
		}
		
		
		AttractionDb obj =  attractionDao.findById(attrObj.getAttractionId(), Boolean.FALSE); 
		int totalUpdates = 0;
		if(StringUtils.isNotEmpty(attrObj.getAttractionDescription())){
			obj.setDescription(attrObj.getAttractionDescription());
			totalUpdates++;
		}
		if(attrObj.getLatitude() != null){
			obj.setLatitude(attrObj.getLatitude());
			totalUpdates++;
		}
		if(attrObj.getLongitude() != null){
			obj.setLongitude(attrObj.getLongitude());
			totalUpdates++;
		}
		
		if(StringUtils.isNotEmpty( attrObj.getAttractionPunchLine())){
			obj.setAttractionPunchLine(attrObj.getAttractionPunchLine());
			totalUpdates++;
		}
		
		if(StringUtils.isNotEmpty( attrObj.getAttractionTiming())){
			obj.setTimings(attrObj.getAttractionTiming());
			totalUpdates++;
		}
		
		if(StringUtils.isNotEmpty( attrObj.getAttractionAddress())){
			obj.setAddress(attrObj.getAttractionAddress());
			totalUpdates++;
		}
		
		if(StringUtils.isNotEmpty( attrObj.getBestTime())){
			obj.setBestTime(attrObj.getBestTime());
			totalUpdates++;
		}
		
		if(StringUtils.isNotEmpty( attrObj.getReqTime())){
			obj.setReqTime(attrObj.getReqTime());
			totalUpdates++;
		}
		
		if(StringUtils.isNotEmpty(attrObj.getPhone())){
			obj.setPhone(attrObj.getPhone());
			totalUpdates++;
		}
		if(StringUtils.isNotEmpty(attrObj.getWebSite())){
			obj.setWebSite(attrObj.getWebSite());
			totalUpdates++;
		}
		if(StringUtils.isNotEmpty(attrObj.getAttractionDescription())){
			obj.setTicket(attrObj.getTicket());
			totalUpdates++;
		}
		
		if(totalUpdates != 0) {
			obj.setId(attrObj.getAttractionId());
			obj.setUpdateDate(new Date());
			obj.setUpdatedBy(attrObj.getCreatedBy());
			attractionDao.updateAttraction(obj);
		}
		
		//Update city
		if(StringUtils.isNotEmpty(attrObj.getCityDescription())){
			CityDb cityDb = cityDao.findById(attrObj.getCityCode(), Boolean.FALSE);
			cityDb.setCityDescription(attrObj.getCityDescription());
			cityDb.setUpdatedBy(attrObj.getCreatedBy());
			cityDb.setUpdatedDate(new Date());
			cityDao.update(cityDb);
		}
		
		//update attraction_extended
		if(StringUtils.isNotEmpty(attrObj.getGooglePlaceId()) ){
			AttractionExternalDb externalAttraction = externalDao.getAttractionExternalByAttractionId(attrObj.getAttractionId());
			
			if(externalAttraction != null){
				if(StringUtils.isNotEmpty(attrObj.getGooglePlaceId()) ){
					
					boolean isChanged = true;
					if(attrObj.getGoogleplaceName().equals(externalAttraction.getName()) && attrObj.getGooglePlaceId().equals(externalAttraction.getgCode())) {
						isChanged = false;
					}
					
					if(isChanged) {
						externalAttraction.setgCode(attrObj.getGooglePlaceId());
						externalAttraction.setName(attrObj.getGoogleplaceName());
						externalAttraction.setUpdateDate(new Date());
						externalAttraction.setAttractionId(attrObj.getAttractionId());
					}
					
				}	
			}else{
				
				externalAttraction  = new AttractionExternalDb();
				externalAttraction.setgCode(attrObj.getGooglePlaceId());
				externalAttraction.setName(attrObj.getGoogleplaceName());
				externalAttraction.setAttractionId(attrObj.getAttractionId());
				externalAttraction.setCreatedDate(new Date());
				
			}
			
			externalDao.saveOrUpdate(externalAttraction);
			
		}
	
	}


	@Override
	@Transactional(readOnly=false)
	public int inActivateImage(String name, String attractionId) {
		
		return attractionImageDoa.updateImageStatus(name, attractionId);
	}
	
	@Override
	@Transactional(readOnly=false)
	public int inActivateAttraction(String attractionId, String userName) {
		
		return attractionDao.updateInactivateAttraction( attractionId, userName);
	}


	@Override
	@Transactional(readOnly=false)
	public void addNewAttraction(NewAttractionDataAddDTO Obj) {
		AttractionDb attractionDbObj = new AttractionDb();
		attractionDbObj.setCityCode(Obj.getCity_Id());
		attractionDbObj.setName(Obj.getAttractionName());
		attractionDbObj.setAddress(Obj.getAddress());
		attractionDbObj.setState(Obj.getState());
		attractionDbObj.setCreatedBy(Obj.getCreatedBy());
		attractionDbObj.setUpdatedBy(Obj.getUpdatedBy());
		attractionDbObj.setLatitude(Obj.getLatitude());
		attractionDbObj.setLongitude(Obj.getLongitude());
		attractionDbObj.setStatus("A");
		attractionDbObj.setTa_attraction_id("123456");
		attractionDbObj.setUpdateDate(new Date());
		AttractionDb attrNewObj =  attractionDao.save(attractionDbObj);
		
		
		AttractionExternalDb attractionexternalObj = new AttractionExternalDb();
		attractionexternalObj.setgCode(Obj.getGooglePlaceId());
		attractionexternalObj.setStatus("A");
		attractionexternalObj.setAttractionId(attrNewObj.getId());
		attractionexternalObj.setCreatedDate(new Date());
		attractionExternalDao.save(attractionexternalObj);
		
		AttractionCategoryMappingDb attractionCategoryObj = new AttractionCategoryMappingDb();
		attractionCategoryObj.setCategorySeq(Obj.getCategory_seq());
		attractionCategoryObj.setCreatedBy(Obj.getCreatedBy());
		attractionCategoryObj.setUpdatedBy(Obj.getUpdatedBy());
		attractionCategoryObj.setCreatedDate(new Date());
		attractionCategoryObj.setAttractionId(attrNewObj.getId());
		attractionCategoryMappingDao.save(attractionCategoryObj);
	
	}
	

	
}

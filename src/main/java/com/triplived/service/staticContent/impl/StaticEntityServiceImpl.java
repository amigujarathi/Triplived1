package com.triplived.service.staticContent.impl;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.connectme.domain.triplived.AttractionResponse;
import com.connectme.domain.triplived.EntityResponse;
import com.connectme.domain.triplived.HotelResponse;
import com.connectme.domain.triplived.dto.AttractionCrowdSourcedDTO;
import com.connectme.domain.triplived.dto.AttractionDetailsDTO;
import com.connectme.domain.triplived.dto.EntityResponseDTO;
import com.connectme.domain.triplived.dto.HotelCrowdSourcedDTO;
import com.connectme.domain.triplived.dto.HotelDetailsDTO;
import com.triplived.dao.attraction.AttractionDao;
import com.triplived.dao.attraction.AttractionExternalDao;
import com.triplived.entity.AttractionDb;
import com.triplived.entity.AttractionExternalDb;
import com.triplived.rest.attractionClient.AttractionSearchClient;
import com.triplived.rest.client.StaticContent;
import com.triplived.rest.fourSquare.FourSquareRestClient;
import com.triplived.rest.hotelClient.HotelSearchClient;
import com.triplived.rest.restaurantSearch.RestaurantSearchClient;
import com.triplived.service.staticContent.StaticEntityService;
import com.triplived.service.trip.TripEntityCrowdSourcedService;

@Service
public class StaticEntityServiceImpl implements StaticEntityService{

	
    @Autowired
	private StaticContent staticContent;
    
    @Autowired
    private RestaurantSearchClient rsc;
    
    @Autowired
    private HotelSearchClient hsc;
    
    @Autowired
    private AttractionSearchClient asc;
    
    @Autowired
    private AttractionExternalDao aed;
    
    @Autowired
    private AttractionDao ad;
    
    @Autowired
    private FourSquareRestClient fourSquareClient;
    
    @Autowired
	private TripEntityCrowdSourcedService entityCrowdSourcedService;
    
	@Override
	public List<EntityResponse> getAttractionEntityByCoordinates(String lat, String lng) {
		
		try {
			List<EntityResponse> entityList = staticContent.suggestAttractionEntityByCoordinates(Double.parseDouble(lat), Double.parseDouble(lng));
			return entityList;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		return null;
	}
	
	
	@Override
	@Transactional("txManager")
	public List<EntityResponseDTO> getAttractionExternalEntityByCoordinatesForTimeline(String deviceId,String lat, String lng, String accuracyDist) {
		String[] distArr = new String[]{accuracyDist,"100","200","300"};
		
		try {
			List<EntityResponse> finalEntityList = new ArrayList<EntityResponse>();
			
			for(String d : distArr) {
				List<EntityResponse> entityList = asc.getAttractionSearchInfo(deviceId,lat, lng, d);
					
				Iterator<EntityResponse> iter = entityList.iterator();
				while(iter.hasNext()) {
					EntityResponse entity = iter.next();
					
					if(finalEntityList.contains(entity)) {
						iter.remove();
						continue;
					}
					//The subCategory field is for basically airports and train_stations which are not in our DB yet. Hence simply adding them in the list.
					if(entity.getCategory().contains("airport") || 
							entity.getCategory().contains("train_station")) {
						entity.setFoundAtQueryDistance(d);
						continue;
					}
					
					EntityResponse obj = staticContent.getAttractionEntityByGcode(entity.getId());//aed.getAttractionExternal(entity.getId());
					if(obj == null) {
						iter.remove();
					}else {
						entity.setFoundAtQueryDistance(d);
						
						if(!CollectionUtils.isEmpty(obj.getCategory_group_icons())) {
							List<String> orderedCategoryIconList = orderCategoryIconList(obj.getCategory_group_icons());
							entity.setCategory_group_icons(orderedCategoryIconList);
						}
					}
				}
				finalEntityList.addAll(entityList);
			}
			
			Map<String, EntityResponse> entityMap = new HashMap<String, EntityResponse>();
			for(EntityResponse er : finalEntityList) {
				entityMap.put(er.getId(), er);
			}
			
			List<EntityResponse> finalList = new ArrayList<EntityResponse>();
			
			Iterator it = entityMap.entrySet().iterator();
		    while (it.hasNext()) {
		        Map.Entry pair = (Map.Entry)it.next();
		       // System.out.println(pair.getKey() + " = " + pair.getValue());
		        finalList.add((EntityResponse) pair.getValue());
		    }
		    
		    List<EntityResponse> fourSquareEntityList = fourSquareClient.searchFS(deviceId, lat, lng, "300");
		    
		    //TODO improve performance of below code from o(n^2)
		    if(!CollectionUtils.isEmpty(finalList)) {
		    	Iterator<EntityResponse> itr = fourSquareEntityList.iterator();
			    while(itr.hasNext()) {
			    	EntityResponse outer = (EntityResponse) itr.next();
				    for(EntityResponse inner : finalList) {
				    	if(inner.getName().equalsIgnoreCase(outer.getName())) {
				    		itr.remove();
				    	}else if(inner.getName().toLowerCase().contains(outer.getName().toLowerCase())) {
				    		itr.remove();
				    	}else if(outer.getName().toLowerCase().contains(inner.getName().toLowerCase())) {
				    		itr.remove();
				    	}
				    }
			    }
		    }
		    finalList.addAll(fourSquareEntityList);
			
			return convertEntityResponseDTO(finalList);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		return null;
	}
	
	
	@Override
	@Transactional("txManager")
	public List<EntityResponseDTO> getAttractionExternalEntityByCoordinatesForTriviaNotifications(String deviceId, String lat, String lng, String accuracyDist) {
		String[] distArr = new String[]{accuracyDist};
		
		try {
			List<EntityResponse> finalEntityList = new ArrayList<EntityResponse>();
			for(String d : distArr) {
				List<EntityResponse> entityList = asc.getAttractionSearchInfo(deviceId, lat, lng, d);
					
				Iterator<EntityResponse> iter = entityList.iterator();
				while(iter.hasNext()) {
					EntityResponse entity = iter.next();
					
					if(finalEntityList.contains(entity)) {
						iter.remove();
						continue;
					}
					AttractionExternalDb obj = aed.getAttractionExternal(entity.getId());
					if(obj == null) {
					
						iter.remove();
					}else {
						if(null != obj.getStatus() && obj.getStatus().equalsIgnoreCase("I")) {
							iter.remove();
						}else {
							entity.setFoundAtQueryDistance(d);
							if(null != obj.getAttractionId()) {
								entity.setAttractionId(obj.getAttractionId().toString());
							}else {
								entity.setAttractionId(null);
							}
						}
						
					}
				}
				finalEntityList.addAll(entityList);
			}
			
			return convertEntityResponseDTO(finalEntityList);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		return null;
	}
	
	
public List<EntityResponseDTO> getAttractionExternalEntityByCoordinates(String deviceId, String lat, String lng, String d) {
		
		try {
			List<EntityResponse> entityList = asc.getAttractionSearchInfo(deviceId, lat, lng, d);
			
			Iterator<EntityResponse> iter = entityList.iterator();
			while(iter.hasNext()) {
				EntityResponse entity = iter.next();
				AttractionExternalDb obj = aed.getAttractionExternal(entity.getId());
				if(obj == null) {
					//Commenting the below code since we are now showing attractions only on basis of our DB and their corresponding google data.
					
					/*AttractionExternalDb newObj = new AttractionExternalDb();
					newObj.setgCode(entity.getId());
					newObj.setStatus("A");
					newObj.setCreatedDate(new Date());
					newObj.setName(entity.getName());
					newObj.setAddress(entity.getAddress());
					aed.updateAttractionExternal(newObj);*/
					iter.remove();
				}else {
					if(null != obj.getStatus() && obj.getStatus().equalsIgnoreCase("I")) {
						iter.remove();
					}
				}
			}
			return convertEntityResponseDTO(entityList);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		return null;
	}
	
	@Override
	@Transactional("txManager")
	public List<EntityResponseDTO> getAttractionExternalByCityCodeForMapping(String cityCode) {
		
		
		String[] distArr = new String[]{"100","200","300","400","500"};
		
		
			List<AttractionDb> attractions = ad.getAttractions(cityCode);
			
			for(AttractionDb a : attractions) {
				//System.out.println("Doing for iteration: " + ++counter);
			  try {	
				
				BufferedWriter brw = new BufferedWriter(new FileWriter("D:\\ayan\\work\\tl\\CityWiseAttractionStatus.txt"));
				if(a.getLatitude() == null || a.getLongitude() == null) {
					//System.out.println("Unable to process attraction because of no Lat/Long : " + a.getId()+"-"+a.getName());
					continue;
				}
				
				String lat = a.getLatitude().toString();
				String lng = a.getLongitude().toString();
				List<EntityResponse> entityList = new ArrayList<EntityResponse>();
				boolean flag = false;
				for(String str : distArr) {
					List<EntityResponse> entityListTemp = asc.getAttractionSearchInfoByCityCode(lat, lng, str, a, cityCode, brw);
		
					for(EntityResponse er : entityListTemp) {
						if(!entityList.contains(er)) {
							entityList.add(er);
							brw.append("New entity: " + cityCode + "-" + a.getId() + "-" + a.getName() + "-d:" + str);
						}else {
							continue;
						}
						
						AttractionResponse attraction = staticContent.matchAttractionByNameAndCity(er.getName(), cityCode, a, str, brw);
						
						if(null != attraction) {
							if(attraction.getAttractionName().equalsIgnoreCase(a.getName())) {
								brw.append("Attraction mapped correct textually: " + cityCode + "-" + a.getId() + "-" + a.getName() + "-d:" + str);	
								flag = true;
								AttractionExternalDb obj = aed.getAttractionExternal(er.getId());
								if(obj == null) {
									AttractionExternalDb newObj = new AttractionExternalDb();
									newObj.setgCode(er.getId());
									newObj.setStatus("A");
									newObj.setCreatedDate(new Date());
									newObj.setName(er.getName());
									newObj.setAddress(er.getAddress());
									newObj.setMappingName(a.getName() + " -- " + a.getCityCode());
									newObj.setAttractionId(a.getId());
									//aed.updateAttractionExternal(newObj);
								}
						   }else {
							   brw.append("Attraction mapped but not matched with curent attraction: " + cityCode + "-" + a.getId() + "-" + a.getName() + "-d:" + str);	
						   }
						}else {
							brw.append("Attraction not mapped with curent attraction: " + cityCode + "-" + a.getId() + "-" + a.getName() + "-d:" + str);	
							continue;
						}
						if(flag) {
							break;
						}
						
					}
						if(flag) {
							break;
						}
					
					}
				brw.close();
			  	} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				
			}
			return null;
		
		}
    
	@Override
	public List<EntityResponse> getHotelEntityByCoordinates(String lat, String lng, String d) {
		
		try {
			List<EntityResponse> entityList = staticContent.suggestHotelEntityByCoordinates(Double.parseDouble(lat), Double.parseDouble(lng),d);
			return entityList;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		return null;
	}
	
	@Override
	public List<EntityResponse> getAllEntityByCoordinates(String lat, String lng, String d) {
		
		try {
			List<EntityResponse> entityList = staticContent.suggestAllEntityByCoordinates(Double.parseDouble(lat), Double.parseDouble(lng), Double.parseDouble(d));
			return entityList;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		return null;
	}
	
	@Override
	public List<EntityResponseDTO> getHotelExternalEntityByCoordinates(String deviceId, String lat, String lng, String d) {
		
		try {
			List<EntityResponse> entityList = hsc.getHotelSearchInfo(deviceId, lat, lng, d);
			return convertEntityResponseDTO(entityList);
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		return null;
	}
	
	@Override
	public List<EntityResponseDTO> getRestaurantEntityByCoordinates(String deviceId, String lat, String lng, String d) {
		
		try {
			List<EntityResponse> entityList = rsc.getRestaurantSearchInfo(deviceId, lat, lng, d);
			return convertEntityResponseDTO(entityList);
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		return null;
	}
	
	
private List<EntityResponseDTO> convertEntityResponseDTO(List<EntityResponse> list) {
		
		List<EntityResponseDTO> listDTO = new ArrayList<EntityResponseDTO>();
		
		for(EntityResponse obj : list) {
			EntityResponseDTO dto = new EntityResponseDTO();
			dto.setId(obj.getId());
			dto.setCityCode(obj.getCityCode());
			dto.setAddress(obj.getAddress());
			dto.setName(obj.getName());
			dto.setType(obj.getType());
			dto.setLocality(obj.getLocality());
			dto.setLatitude(obj.getLatitude());
			dto.setLongitude(obj.getLongitude());
			dto.setImageSrc(obj.getImageSrc());
			dto.setStreet(obj.getStreet());
			dto.setDistanceFromCoordinates(obj.getDistanceFromCoordinates());
			dto.setCategory(obj.getCategory());
			dto.setFoundAtQueryDistance(obj.getFoundAtQueryDistance());
			dto.setAttractionId(obj.getAttractionId());
			dto.setCategoryGroupIcons(obj.getCategory_group_icons());
			dto.setMiscMap(obj.getMiscMap());
			listDTO.add(dto);
		}
		
		return listDTO;
	}

	@Override
	public List<EntityResponseDTO> orderListByDistance(String deviceId, List<EntityResponseDTO> attractions, List<EntityResponseDTO> restaurants, List<EntityResponseDTO> hotels, String lat, String lng) {
			if(null == attractions) {
				attractions = new ArrayList<EntityResponseDTO>();
			}
			attractions.addAll(restaurants);
			attractions.addAll(hotels);
			
			if(!CollectionUtils.isEmpty(attractions)) {
				Collections.sort(attractions);
			}
		
		return attractions;
	}
	
	private List<String> orderCategoryIconList(List<String> list) {
		List<String> orderedList = new ArrayList<String>();
		boolean flag = false;
		for(String str : list) {
			if(str.equalsIgnoreCase("Landmark")) {
				flag = true;
				continue;
			}
			orderedList.add(str);
		}
		if(flag) {
			orderedList.add("Landmark");
		}
		return orderedList;
	}

	@Override
   public AttractionDetailsDTO getAttractionDetails(String code) throws MalformedURLException, SolrServerException {
	   EntityResponse entity = staticContent.getAttractionEntityByCode(code);
	   AttractionDetailsDTO attDetailObj = new AttractionDetailsDTO();
	   if(null != entity) {
		   attDetailObj.setId(entity.getId());
		   attDetailObj.setName(entity.getName());
		   attDetailObj.setDescription(entity.getDescription());
		   attDetailObj.setReqTime(entity.getReqTime());
		   attDetailObj.setTimings(entity.getTimings());
		   attDetailObj.setBestTime(entity.getBestTime());
		   attDetailObj.setAddress(entity.getAddress());
		   attDetailObj.setCityName(entity.getCityName());
		   attDetailObj.setImages(entity.getImageSrc());
		   attDetailObj.setLatitude(entity.getLatitude());
		   attDetailObj.setLongitude(entity.getLongitude()); 
		   
		   AttractionCrowdSourcedDTO content = entityCrowdSourcedService.getAttractionCrowdSourcedDataFromId(code);
		   attDetailObj.setCrowdSourcedContent(content);
		   return attDetailObj;
	   }
	   return null;
   }
   
	@Override
	   public HotelDetailsDTO getHotelDetails(String code) throws MalformedURLException, SolrServerException {
		   HotelResponse entity = staticContent.getHotelEntityByCode(code);
		   HotelDetailsDTO htlDetailObj = new HotelDetailsDTO();
		   if(null != entity) {
			   htlDetailObj.setId(entity.getId());
			   htlDetailObj.setName(entity.getName());
			   htlDetailObj.setAddress(entity.getAddress());
			   htlDetailObj.setLatitude(entity.getLatitude());
			   htlDetailObj.setLongitude(entity.getLongitude());
			   htlDetailObj.setDescription(entity.getDescription());
			   htlDetailObj.setHotelImages(entity.getHotelImages());
			   htlDetailObj.setAmenities(entity.getAmenities());
			   htlDetailObj.setRating(entity.getRating());
			   
			   HotelCrowdSourcedDTO content = entityCrowdSourcedService.getHotelCrowdSourcedDataFromId(code);
			   htlDetailObj.setCrowdSourcedContent(content);
			   return htlDetailObj;
		   }
		   return null;
	   }   
   
	

}

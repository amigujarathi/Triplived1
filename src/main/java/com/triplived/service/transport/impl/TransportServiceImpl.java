package com.triplived.service.transport.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.connectme.domain.triplived.CityResponse;
import com.connectme.domain.triplived.EntityResponse;
import com.domain.triplived.transport.dto.FinalTransportStatus;
import com.domain.triplived.transport.dto.FinalTransportType;
import com.domain.triplived.transport.dto.ValidRouteRestDTO;
import com.domain.triplived.transport.dto.ValidTransportRestDTO;
import com.domain.triplived.transport.dto.ValidTransportType;
import com.triplived.controller.transport.TransportController;
import com.triplived.rest.client.StaticContent;
import com.triplived.rest.romeToRio.RomeToRioClient;
import com.triplived.service.transport.TransportService;

@Service
public class TransportServiceImpl implements TransportService {
	
	private static final Logger logger = LoggerFactory.getLogger(TransportServiceImpl.class);
	
	@Autowired
	private StaticContent staticContent;
	
	@Autowired
	private RomeToRioClient romeToRioClient;
	
	private String getCity(String cityId) throws Exception {
		List<CityResponse> cityList = staticContent.getCityFromCode(cityId);
		if(!CollectionUtils.isEmpty(cityList)) {
			return cityList.get(0).getCityName();
		}
		
		return null;
	}
	
	private String getEntityCategory(String entityId) throws Exception {
		EntityResponse response = staticContent.getAttractionEntityByGcode(entityId);
		if(null != response) {
			return response.getCategory();
		}
		
		return null;
	}
	
	
	
	@Override
	public String getTransport(String origin, String dest, String sourceEntityCategory, 
			String destEntityCategory, String deviceId, Long timeDiff, String tripId) throws Exception{
		
		if(null == sourceEntityCategory) {
			sourceEntityCategory = "undefined";
		}if(null == destEntityCategory) {
			destEntityCategory = "undefined";
		}
		
		String travelMode = FinalTransportType.NOT_SURE.getType();
		
		/*String sourceEntityCategory = getEntityCategory(sourceEntityId);
		String destEntityCategory = getEntityCategory(destEntityId);*/
		double timeDiffInMinutes = (timeDiff / 60);
		
		Integer flightBufferTime = 120;//in minutes
		
		
		Integer possibleFlightFromAirportLog = 2;
		Integer possibleTrain = 2;
		sourceEntityCategory = sourceEntityCategory.toLowerCase();
		destEntityCategory = destEntityCategory.toLowerCase();
		
		if((sourceEntityCategory.contains("airport")) && (destEntityCategory.contains("airport"))) {
			possibleFlightFromAirportLog = 0;
		}else if((!sourceEntityCategory.contains("airport")) && (destEntityCategory.contains("airport"))) {
			possibleFlightFromAirportLog = 1;
		}else if((sourceEntityCategory.contains("airport")) && (!destEntityCategory.contains("airport"))) {
			possibleFlightFromAirportLog = 1;
		}else {
			possibleFlightFromAirportLog = 2;
		}
		
		if((sourceEntityCategory.contains("train")) && (destEntityCategory.contains("train"))) {
			possibleTrain = 0;
			travelMode = FinalTransportType.TRAIN_SURE.getType();
			logger.warn("Mode of Transport returned is train for deviceId - {}, tripId - ", deviceId, tripId);
			return travelMode;
		}
		
		List<ValidRouteRestDTO> routes = romeToRioClient.getModesOfTransportBtnCities(origin, dest);
		
		
		Collections.sort(routes);
		boolean isFlightModeARoute = false;
		
		//Check For Flight
		if(!CollectionUtils.isEmpty(routes)) {
			for(ValidRouteRestDTO route : routes) {
				List<ValidTransportRestDTO> transports = route.getValidTransports();
				if(transports.size() == 1) {
				    ValidTransportRestDTO transport = transports.get(0);
					if(null != transport.getTransportType() && transport.getTransportType().equalsIgnoreCase(ValidTransportType.Flight.getType())) {
						
						isFlightModeARoute = true;
						
						if(timeDiffInMinutes < (transport.getDuration() + flightBufferTime)) {//comparable flight distance
							
							if(possibleFlightFromAirportLog == 0) {
								travelMode = FinalTransportType.FLIGHT_SURE.getType();
							}if(possibleFlightFromAirportLog == 1) {
								travelMode = FinalTransportType.FLIGHT_SURE.getType();
							}if(possibleFlightFromAirportLog == 2) {
								//NOT SURE, will have to compare distances with other modes
								Integer maxTime = transport.getDuration() + flightBufferTime;
								travelMode = compareFlightTimeWithOtherModes(Double.parseDouble(maxTime.toString()), routes);
								
							}
							
						}else if(timeDiffInMinutes < ((transport.getDuration() + flightBufferTime) * 1.5 )) {//comparable flight distance
							
							if(possibleFlightFromAirportLog == 0) {
								travelMode = FinalTransportType.FLIGHT_SURE.getType();							
						    }if(possibleFlightFromAirportLog == 1) {
								travelMode = FinalTransportType.FLIGHT_SURE.getType();
							}if(possibleFlightFromAirportLog == 2) {
								//NOT SURE, will have to compare distances with other modes
								travelMode = compareFlightTimeWithOtherModes((transport.getDuration() + flightBufferTime) * 1.5, routes);
							}
							
						}else {
							String timeDiffWiseTravelMode = compareFlightTimeWithOtherModes(timeDiffInMinutes, routes);
							if(possibleFlightFromAirportLog == 0) {
								travelMode = timeDiffWiseTravelMode;
						    }if(possibleFlightFromAirportLog == 1) {
						    	travelMode = timeDiffWiseTravelMode;
							}if(possibleFlightFromAirportLog == 2) {
								//NOT SURE, will have to check other sources
                                /*if(timeDiffWiseTravelMode.equalsIgnoreCase(FinalTransportType.FLIGHT_SURE.getType())) {
									travelMode = FinalTransportType.FLIGHT_SURE.getType();							
								}else if(timeDiffWiseTravelMode.equalsIgnoreCase(FinalTransportType.FLIGHT_MAYBE.getType())) {
									travelMode = FinalTransportType.FLIGHT_MAYBE.getType();							
								}
								else {
									travelMode = FinalTransportType.NOT_SURE.getType();
								}*/
								travelMode = timeDiffWiseTravelMode;
							}
						}
					  }
					
					}
				 
				}//routes for loop
			
			    //Code below tests for routes that do not have flight as an option ex: Delhi shimla
			
			if((!isFlightModeARoute && FinalTransportType.NOT_SURE.getType().equalsIgnoreCase(travelMode)) || 
			      (isFlightModeARoute && !travelMode.equalsIgnoreCase(FinalTransportType.FLIGHT_SURE.getType()))) {
				
				boolean isSurfaceRoute = false;
				boolean isTrainRoute = false;
				boolean isFerryRoute = false;
				for(ValidRouteRestDTO route : routes) {
					List<ValidTransportRestDTO> transports = route.getValidTransports();
					if(transports.size() == 1) {
					    ValidTransportRestDTO transport = transports.get(0);
						if(null != transport.getTransportType())
							if(transport.getTransportType().equalsIgnoreCase(ValidTransportType.Train.getType())) {
								isTrainRoute = true;
							}
							if(transport.getTransportType().equalsIgnoreCase(ValidTransportType.Ferry.getType())) {
								isFerryRoute = true;
							}
							if((transport.getTransportType().equalsIgnoreCase(ValidTransportType.Bus.getType())) || 
									(transport.getTransportType().equalsIgnoreCase(ValidTransportType.Car.getType())) ||
									(transport.getTransportType().equalsIgnoreCase(ValidTransportType.Drive.getType())) || 
									(transport.getTransportType().equalsIgnoreCase(ValidTransportType.Taxi.getType()))
									){
								isSurfaceRoute = true;
							}
						}
				 }//for loop
				
				 if(!isFlightModeARoute && FinalTransportType.NOT_SURE.getType() == travelMode) {
					 if(isTrainRoute && !isFerryRoute && !isSurfaceRoute) {
						 travelMode = FinalTransportType.TRAIN_SURE.getType();
					 }else if(!isTrainRoute && isFerryRoute && !isSurfaceRoute) {
						 travelMode = FinalTransportType.FERRY_SURE.getType();
					 }else if(!isTrainRoute && !isFerryRoute && isSurfaceRoute) {
						 travelMode = FinalTransportType.SURFACE_SURE.getType();
					 }else if(!isTrainRoute && !isFerryRoute && !isSurfaceRoute) {
						 travelMode = FinalTransportType.NOT_SURE.getType();
					 }else if(isTrainRoute && !isFerryRoute && isSurfaceRoute) {
						 travelMode = FinalTransportType.SURFACE_SURE.getType();
					 }else {
						 travelMode = FinalTransportType.NOT_SURE.getType();
					 }
			     }
				 
				 if(isFlightModeARoute && travelMode.equalsIgnoreCase(FinalTransportType.FLIGHT_MAYBE.getType())) {
					 /*if(isTrainRoute && !isFerryRoute && !isSurfaceRoute) {
						 travelMode = FinalTransportType.FLIGHT_MAYBE.getType();
					 }else if(!isTrainRoute && isFerryRoute && !isSurfaceRoute) {
						 travelMode = FinalTransportType.FLIGHT_MAYBE.getType();
					 }else if(!isTrainRoute && !isFerryRoute && isSurfaceRoute) {
						 travelMode = FinalTransportType.FLIGHT_MAYBE.getType();
					 }if(!isTrainRoute && !isFerryRoute && !isSurfaceRoute) {
						 travelMode = FinalTransportType.FLIGHT_MAYBE.getType();
					 }if(isTrainRoute && !isFerryRoute && isSurfaceRoute) {
						 travelMode = FinalTransportType.FLIGHT_MAYBE.getType();
					 }else {*/
						 travelMode = FinalTransportType.FLIGHT_MAYBE.getType();
					 //}
				 }
				 
				 if(isFlightModeARoute && travelMode.equalsIgnoreCase(FinalTransportType.NOT_SURE.getType())) {
					 if(isTrainRoute && !isFerryRoute && !isSurfaceRoute) {
						 travelMode = FinalTransportType.TRAIN_SURE.getType();
					 }else if(!isTrainRoute && isFerryRoute && !isSurfaceRoute) {
						 travelMode = FinalTransportType.FERRY_SURE.getType();
					 }else if(!isTrainRoute && !isFerryRoute && isSurfaceRoute) {
						 travelMode = FinalTransportType.SURFACE_SURE.getType();
					 }if(!isTrainRoute && !isFerryRoute && !isSurfaceRoute) {
						 travelMode = FinalTransportType.NOT_SURE.getType();
					 }if(isTrainRoute && !isFerryRoute && isSurfaceRoute) {
						 travelMode = FinalTransportType.SURFACE_SURE.getType();
					 }else {
						 travelMode = FinalTransportType.NOT_SURE.getType();
					 }
				 }
			   }
			
		  }//if empty check
		
		 return travelMode;
	    }
	
	
	    private String compareFlightTimeWithOtherModes(Double comparableTime, List<ValidRouteRestDTO> routes) {
	    	Double differenceInTime = null;
	    	boolean isAnyRouteTimeComparable = false;
	    	boolean onlyFlightRouteExist = true;
	    	Integer flightTime = null;
	    	
	    	for(ValidRouteRestDTO route : routes) {
				List<ValidTransportRestDTO> transports = route.getValidTransports();
				if(transports.size() == 1) {
				    ValidTransportRestDTO transport = transports.get(0);
				    if(null != transport && !transport.getTransportType().equalsIgnoreCase(ValidTransportType.Flight.getType())) {
				    	onlyFlightRouteExist = false;
				    	if(transport.getDuration() < comparableTime) {
				    		isAnyRouteTimeComparable = true;
				    		break;
				    	}else {
				    		if(null == differenceInTime || differenceInTime > (transport.getDuration() - comparableTime)) {
				    			differenceInTime = transport.getDuration() - comparableTime;
				    		}
				    		continue;
				    	}
				    	
				    }else if(null != transport && transport.getTransportType().equalsIgnoreCase(ValidTransportType.Flight.getType())) { 
				    	flightTime = transport.getDuration();
				    }
				}
	    	}
	    	if(isAnyRouteTimeComparable) {
	    		return FinalTransportType.NOT_SURE.getType();
	    	}else {
		    	if(null != differenceInTime && differenceInTime > 0) {
		    		if(differenceInTime > 300) {
		    			return FinalTransportType.FLIGHT_SURE.getType();
		    		}else {
		    			return FinalTransportType.NOT_SURE.getType();
		    		}
		    	}else {
		    		if(null == differenceInTime && onlyFlightRouteExist) {
		    			return FinalTransportType.FLIGHT_MAYBE.getType();
		    		}
		    		return FinalTransportType.NOT_SURE.getType();
		    	}
	    	}
	    	
	    }
	    
	    @Override
	    public FinalTransportStatus getFinalTransportDetail(String transportType) {
	    	FinalTransportStatus obj = new FinalTransportStatus();
	    	if(transportType.equalsIgnoreCase(FinalTransportType.FLIGHT_SURE.getType())) {
	    		obj.setStatus(1);
	    		obj.setMessage("Travelled by Flight");
	    		obj.setTravelMode("FLIGHT");
	    	}else if(transportType.equalsIgnoreCase(FinalTransportType.FLIGHT_MAYBE.getType())) {
	    		obj.setStatus(0);
	    		obj.setMessage("Seems you have travelled by Flight");
	    		obj.setTravelMode("FLIGHT");
	    	}else if(transportType.equalsIgnoreCase(FinalTransportType.SURFACE_SURE.getType())) {
	    		obj.setStatus(1);
	    		obj.setMessage("Travelled on Land");
	    		obj.setTravelMode("SURFACE");
	    	}else if(transportType.equalsIgnoreCase(FinalTransportType.SURFACE_MAYBE.getType())) {
	    		obj.setStatus(0);
	    		obj.setMessage("Seems you have travelled on Land");
	    		obj.setTravelMode("SURFACE");
	    	}else if(transportType.equalsIgnoreCase(FinalTransportType.FERRY_SURE.getType())) {
	    		obj.setStatus(1);
	    		obj.setMessage("Travelled by Ferry");
	    		obj.setTravelMode("FERRY");
	    	}else if(transportType.equalsIgnoreCase(FinalTransportType.TRAIN_SURE.getType())) {
	    		obj.setStatus(1);
	    		obj.setMessage("Travelled by train");
	    		obj.setTravelMode("TRAIN");
	    	}else if(transportType.equalsIgnoreCase(FinalTransportType.NOT_SURE.getType())) {
	    		obj.setStatus(1);
	    		obj.setMessage("Tell us your mode of transport");
	    		obj.setTravelMode("NOT_SURE");
	    	}
	    	return obj;
	    }

}

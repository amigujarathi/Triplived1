package com.triplived.controller.transport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.domain.triplived.transport.dto.FinalTransportStatus;
import com.google.gson.Gson;
import com.triplived.service.transport.TransportService;

@Controller
@RequestMapping(value="/transport")
public class TransportController {
	
	private static final Logger logger = LoggerFactory.getLogger(TransportController.class);
	
	@Autowired
	private TransportService transportService;

	@RequestMapping(value="/getMode", method = RequestMethod.GET)	
	public @ResponseBody String getModeOfTransport(@RequestParam(value="sourceTs") String sourceTimestamp,
			@RequestParam(value="destTs") String destTimestamp,
			@RequestParam(value="sourceEntityCat", required=false) String sourceEntityCat,
			@RequestParam(value="destEntityCat", required=false) String destEntityCat,
			@RequestParam(value="sourceCd", required=false) String sourceCd,
			@RequestParam(value="destCd", required=false) String destCd,
			@RequestParam(value="srcCityName", required=false) String srcCityName,
			@RequestParam(value="srcCountryName", required=false) String srcCountryName,
			@RequestParam(value="destCityName", required=false) String destCityName,
			@RequestParam(value="destCountryName", required=false) String destCountryName,
			@RequestParam(value="deviceId") String deviceId,
			@RequestParam(value="tripId") String tripId) {
		
		Gson gson = new Gson();
		FinalTransportStatus obj = new FinalTransportStatus();
		obj.setStatus(1);
		obj.setMessage("Tell us your mode of transport");
		obj.setTravelMode("NOT_SURE");
		try {
			
			logger.warn("Mode of Transport requested for deviceId - {}, tripId - {}. Parameters in request are: sourceTs - {}, destTs - {}, "
					+ "sourceEntityCat - {}, destEntityCat - {}, sourceCoordinates - {}, destCoordinates - {}, "
					+ "srcCity - {}, srcCountry - {}, destCity - {}, destCountry - {} ", deviceId, tripId, sourceTimestamp
					, destTimestamp, sourceEntityCat, destEntityCat, sourceCd, destCd, srcCityName, srcCountryName, destCityName, destCountryName);
			Long timeDiff = (Long.parseLong(destTimestamp) - Long.parseLong(sourceTimestamp))/1000;
			
			String travelMode = null;
			String src = null;
			String dest = null;
			if(null != sourceCd) {
				src = sourceCd;
			}
			if(null != destCd) {
			    dest = destCd;
			}
			if(null != srcCityName && null != srcCountryName) {
				src = srcCityName + "," + srcCountryName;
			}
			if(null != destCityName && null != destCountryName) {
				dest = destCityName + "," + destCountryName;
			}
			if(null != src && null != dest) {
				travelMode = transportService.getTransport(src, dest, sourceEntityCat, destEntityCat, deviceId, timeDiff, tripId);
				obj = transportService.getFinalTransportDetail(travelMode);
				return gson.toJson(obj);
			}else {
				logger.error("Sending no mode of communication, since either src - {} or dest - {} is null", src, dest);
				return gson.toJson(obj);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return gson.toJson(obj);
		}
		
		//return gson.toJson(obj);
	}
	
}

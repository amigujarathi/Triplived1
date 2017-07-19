package com.triplived.controller.report;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.connectme.domain.triplived.dto.ReportDTO;
import com.connectme.domain.triplived.trip.dto.SubTrip;
import com.connectme.domain.triplived.trip.dto.Trip;
import com.domain.triplived.trip.dto.Update;
import com.google.gson.Gson;
import com.triplived.controller.profile.Person;
import com.triplived.controller.video.VideoController;
import com.triplived.entity.TripFaqDb;
import com.triplived.entity.TripKitListDb;
import com.triplived.mail.client.RecordingStartMail;
import com.triplived.mail.client.VideoGenerationRequestMail;

import com.triplived.service.report.ReportService;

import com.triplived.util.Constants;
import com.triplived.util.RetriableTask;
import com.triplived.util.TripLivedUtil;

@Controller
@RequestMapping("/report")
public class ReportController {

	private static final Logger logger = LoggerFactory.getLogger(ReportController.class );
	
	@Autowired
	private ReportService reportService;

/*	@RequestMapping(method= RequestMethod.POST, value="/reportuser")
    public @ResponseBody Boolean loginReportFromWeb(@RequestBody ReportDTO report) {
		
		String reportId = report.getId();
		try {
			reportService.loginReportFromWeb(Long.parseLong(reportId), report);
			return true;
			
		}catch(Exception e) {
			
			e.printStackTrace();
			//return false;
		}
		return null;
	}*/
	
	
	@RequestMapping(method= RequestMethod.POST, value="/reportuser")
	public @ResponseBody String tripComments(@RequestParam("username") @Valid String username,
			@RequestParam("password") @Valid String password) {
		
		/*if(TripLivedUtil.isPublicIdEnabled(request)) {
			Long internalTripId = tripService.getTripIdByPublicId(Long.parseLong(tripId));
			tripId = internalTripId.toString();
		}*/
		
		//String deviceId = request.getHeader("UserDeviceId");
		//logger.warn("Trip comment request for trip - {} from user - {} and deviceId - {}", tripId, userId, deviceId);
		
		if(null != username && null != password) {
			//return tripService.addCommentsOnTrip(Long.parseLong(tripId), Long.parseLong(userId), comment);
			return reportService.selectUserFromReport(username, password);
		}else {
			return null;
		}
	}
	
	
	
		
}

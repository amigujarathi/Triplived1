package com.triplived.controller.travelpartner;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.connectme.domain.triplived.dto.MailerDTO;
import com.triplived.mail.client.SubscribeMailResponse;

@RequestMapping(value = "/travlpartner")
@Controller
public class TravelPartnerController {

	@Autowired
	private SubscribeMailResponse subscribeMailObj;

	private static final Logger logger = LoggerFactory.getLogger(TravelPartnerController.class);

	@RequestMapping(method= RequestMethod.GET)
   	public String index(){
		return "travelPartner" ;
	}
   			
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody String sendEmailers(@RequestBody MailerDTO contact) {
		logger.warn("Send mailers to   - {}" + contact.toString());
		try {
			if (null != contact) {
				if (StringUtils.isNotEmpty(contact.getEmail())) {
					subscribeMailObj.sendBulkMailers(contact);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "{\"Response\":\"Sent\"}";
	}

}

package com.triplived.controller.contact;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.connectme.domain.triplived.dto.ContactUsDTO;
import com.triplived.mail.client.SubscribeMailResponse;

@RequestMapping(value = "/contact")
@Controller
public class ContactController {
	
	@Autowired
	private SubscribeMailResponse subscribeMailObj;
	
	private static final Logger logger = LoggerFactory.getLogger(ContactController.class);
	
	@RequestMapping(value = "/post", method = RequestMethod.POST)
	public @ResponseBody String addContactForTlApp(@RequestBody ContactUsDTO contact) {
		logger.warn("PEOPLE WANT TO CONTACT US  - {}" + contact.toString() );
		try {
			if(null != contact) {
				if(StringUtils.isNotEmpty(contact.getEmail())){
					subscribeMailObj.sendMail(contact.getEmail());
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "Thanks for subscribing. We will contact you soon!!";
	}

}

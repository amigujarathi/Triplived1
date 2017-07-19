package com.triplived.mail.client;

import java.util.*;

import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ActiveUsersReportMail
{
	
   private static final Logger logger = LoggerFactory.getLogger(ActiveUsersReportMail.class);
	
   public void sendMail(List<String> activeUserDetails)
   {    
        logger.warn("Active Users Report mail");
        final String username = "contact@triplived.com";
		final String password = "contact@tl";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

      // Get the default Session object.
		Session session = Session.getInstance(props,
				  new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
				  });


      try{
    	  Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("contact@triplived.com"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("ayan@triplived.com,mayank@triplived.com,shadman@triplived.com,santosh@triplived.com"));
			//message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("ayan@triplived.com"));
			message.setSubject("Total no. of active users - " + activeUserDetails.size());
			
			StringBuilder bodyMail = new StringBuilder();
			bodyMail.append("User details \r\n");
			bodyMail.append("\r\n");
			for(String s : activeUserDetails) {
				bodyMail.append(s+"\r\n");
			}
			message.setText(bodyMail.toString());

			logger.warn("Active users - {}, details - {}", activeUserDetails.size(), bodyMail.toString());
			Transport.send(message);

			
		}catch (MessagingException mex) {
		 logger.error("Error while Sending Active users report, Error is {}", mex.getMessage());
		 mex.printStackTrace();
      }
   }
}




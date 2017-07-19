package com.triplived.mail.client;

import java.util.*;

import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class VideoStatusMail
{
	
   private static final Logger logger = LoggerFactory.getLogger(VideoStatusMail.class);
	
   public void sendMail(String tripId, String tripName, String userName, List<String> videoStages, Boolean status)
   {    
        logger.warn("Status of video generation for trip - {}", tripId);
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
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse("ayan@triplived.com,mayank@triplived.com,shadman@triplived.com,santosh@triplived.com"));
			message.setSubject("Video Status for tripId - " + tripId + " : " + status);
			
			StringBuilder bodyMail = new StringBuilder();
			String tripIdStr = "TripId : " + tripId;
			String tripNameStr = "TripName : " + tripName;
			String tripStatus = "TripStatus : " + status;
			String userNameStr = "User : " + userName;
			
			bodyMail.append(tripIdStr + "\r\n");
			bodyMail.append(tripNameStr + "\r\n");
			bodyMail.append(tripStatus + "\r\n");
			bodyMail.append(userNameStr + "\r\n");
			bodyMail.append("Trip Video Generation stages " + "\r\n");
			
			bodyMail.append("\r\n");
			for(String s : videoStages) {
				bodyMail.append(s+"\r\n");
			}
			
			message.setText(bodyMail.toString());

			Transport.send(message);

			logger.warn("Video Status mail sent trip Id - {}", tripId);
		}catch (MessagingException mex) {
		 logger.error("Error while Video Generation mail for trip Id - {} , Error is {}", tripId, mex.getMessage());
		 mex.printStackTrace();
      }
   }
}




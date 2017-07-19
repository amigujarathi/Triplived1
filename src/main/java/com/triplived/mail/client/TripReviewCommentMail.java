package com.triplived.mail.client;

import java.util.*;

import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class TripReviewCommentMail
{
	
   private static final Logger logger = LoggerFactory.getLogger(TripReviewCommentMail.class);
	
   public void sendMail(String tripId, String reviewerName)
   {    
        logger.warn("SendMail request for Trip Review comment for trip - {}", tripId);
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
			message.setRecipients(Message.RecipientType.TO,InternetAddress.parse("ayan@triplived.com,mayank@triplived.com,shadman@triplived.com,santosh@triplived.com,heena.sachdeva@triplived.com,tejasyambem@gmail.com,tanvi.johri@triplived.com"));
			message.setSubject("NEW TRIP REVIEW COMMENT: Reviewer - " + reviewerName + " , trip - " + tripId);
			
			StringBuilder bodyMail = new StringBuilder();
			String tripIdStr = "TripId : " + tripId;
			String userNameStr = "Reviewer : " + reviewerName;
			
			bodyMail.append(tripIdStr + "\r\n");
			bodyMail.append(userNameStr + "\r\n");
			bodyMail.append("Please look at the new comment added for the trip " + "\r\n");
			bodyMail.append("LINK : www.triplived.com/triplived/tripReviewComments " + "\r\n");
			
			bodyMail.append("\r\n");
			
			message.setText(bodyMail.toString());

			Transport.send(message);

			logger.warn("Trip Review comment mail sent trip Id - {}", tripId);
		}catch (MessagingException mex) {
		 logger.error("Error while sending Trip Review comment mail for trip Id - {} , Error is {}", tripId, mex.getMessage());
		 mex.printStackTrace();
      }
   }
}




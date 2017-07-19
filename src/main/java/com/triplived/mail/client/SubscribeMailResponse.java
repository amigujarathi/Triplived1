package com.triplived.mail.client;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang.StringUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.connectme.domain.triplived.dto.MailerDTO;
import com.triplived.service.profile.PersonProfile;

@Component
public class SubscribeMailResponse {
 
 @Autowired	
 private MailSender mailSender;
 
 @Autowired
 private VelocityEngine velocityEngine;

 private static final Logger logger = LoggerFactory.getLogger(SubscribeMailResponse.class);

 public void sendMail(String email) throws ResourceNotFoundException, ParseErrorException, Exception {
  
	 logger.warn("Mailing subscribed mail to id - {}", email);
	 
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
	 
		Message message = new MimeMessage(session);
  
  
  
		message.setFrom(new InternetAddress("contact@triplived.com"));
		message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
  message.setSubject("Welcome to Triplived. Thanks for subscribing");

  /*velocityEngine.setProperty("resource.loader", "class");
  velocityEngine.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");

  velocityEngine.init();*/
  
  Template template = velocityEngine.getTemplate("./subscribeMail.vm");

  VelocityContext velocityContext = new VelocityContext();
  velocityContext.put("firstName", "Yashwant");
  velocityContext.put("lastName", "Chavan");
  velocityContext.put("location", "Pune");
  
  StringWriter stringWriter = new StringWriter();
  
  template.merge(velocityContext, stringWriter);
  
  message.setText(stringWriter.toString());
  
  //mailSender.send(message);
  Transport.send(message);
 }
 
 
 
 public void sendMail(PersonProfile person) throws ResourceNotFoundException, ParseErrorException, Exception {
	  
	 logger.warn("Live requested by user - {}", person.getFname());
	 
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
	 
		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress("contact@triplived.com"));
		message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("ayan@triplived.com,mayank@triplived.com,shadman@triplived.com,santosh@triplived.com"));
		message.setSubject("Live Recording requested by user " + person.getFname() + " " +person.getLname() + " with Id " + person.getId());
		  
		message.setText("Live Recording requested by user " + person.getFname() + " " +person.getLname() + " with Id " + person.getId());
		
		//mailSender.send(message);
		Transport.send(message);
 }
 
 @Async
 public void sendBulkMailers(MailerDTO mailer) throws ResourceNotFoundException, ParseErrorException, Exception {
	  
	    logger.warn("Bulk Mailers {} ",  mailer.toString());
	    String toUsers = mailer.getUsers();
	    
	    List<PersonProfile> listUser = new ArrayList<PersonProfile> ();
	    
	    for(String userString : toUsers.split("\n")){
	    	PersonProfile profile = new PersonProfile();
	    	profile.setFname(userString.split(",")[0].trim());
	    	profile.setEmail(userString.split(",")[1].trim());
	    	
	    	listUser.add(profile);
	    }
	    
	    final String username = mailer.getEmail();
		final String password = mailer.getPassword();

		
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		for(PersonProfile person : listUser){
			Session session = Session.getInstance(props,
					  new javax.mail.Authenticator() {
						protected PasswordAuthentication getPasswordAuthentication() {
							return new PasswordAuthentication(username, password);
						}
					  });
		 
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));
			message.setRecipients(Message.RecipientType.BCC, InternetAddress.parse("santosh@triplived.com"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(person.getEmail()));
			message.setSubject(mailer.getSubject());
			  
			message.setContent(getText(mailer, person.getFname()), "text/html");
			
			//mailSender.send(message);
			Transport.send(message);
		}
			
   // Get the default Session object.
		
 }


	
	private String getText(MailerDTO mailer, String name) throws Exception {

		if(StringUtils.isNotEmpty(mailer.getTemplateName())) {
			
			Template template = null;
			switch(mailer.getTemplateName()) {
			
			case "User ReConnect":
				  template = velocityEngine.getTemplate("./Reconnect_users.vm");
				  break;
				  
			case "Trip Improvement":
				  template = velocityEngine.getTemplate("./trip_improvement.vm");
				  break;
			case  "Only Source Destination"	:
				  template = velocityEngine.getTemplate("./source_destination.vm");
				  break;  
			case "Good Trip":
				  template = velocityEngine.getTemplate("./good_trip.vm");
				  break;
			case "FeebBack":
				  template = velocityEngine.getTemplate("./Feedback_app.vm");
				  break;
			case "Travel Agent":
				  template = velocityEngine.getTemplate("./travel_agent.vm");
				  break;
			}
			
			if(template != null){
				 VelocityContext velocityContext = new VelocityContext();
				 
				 velocityContext.put("travellerName", name);
				 if(StringUtils.isNotEmpty(mailer.getDesignation())){
					 velocityContext.put("designation", mailer.getDesignation());
				 }
				 velocityContext.put("userfrom", mailer.getName());
				 StringWriter stringWriter = new StringWriter();
				 template.merge(velocityContext, stringWriter);
				  
				 return stringWriter.toString();
			}
		}
		
		return null;
	}
 
}
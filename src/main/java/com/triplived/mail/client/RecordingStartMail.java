package com.triplived.mail.client;

import java.io.StringWriter;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.stereotype.Component;

@Component
public class RecordingStartMail {
 
 @Autowired	
 private MailSender mailSender;
 
 @Autowired
 private VelocityEngine velocityEngine;

 private static final Logger logger = LoggerFactory.getLogger(RecordingStartMail.class);

 public void sendMail(String email, String name, String type, String endDate) throws ResourceNotFoundException, ParseErrorException, Exception {
  
	 logger.warn("Recording start Mail: Mailing subscribed mail to id - {}", email);
	 
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
	 
		MimeMessage message = new MimeMessage(session);
  
		message.setFrom(new InternetAddress("contact@triplived.com"));
		message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
		String subject = "Congratulations on starting a new trip on Triplived";
		if("Live".equalsIgnoreCase(type)){
			subject = "Congratulations on starting a new live trip on Triplived";
		}
		message.setSubject(subject);
		

  /*velocityEngine.setProperty("resource.loader", "class");
  velocityEngine.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");

  velocityEngine.init();*/
  
  Template template = velocityEngine.getTemplate("./recording-start.vm");

  VelocityContext velocityContext = new VelocityContext();
  velocityContext.put("travellerName", name);
   
  StringWriter stringWriter = new StringWriter();
  
  template.merge(velocityContext, stringWriter);
  
  message.setText(stringWriter.toString());
  message.setContent(stringWriter.toString(), "text/html");
  
  //mailSender.send(message);
  Transport.send(message);
 }
}
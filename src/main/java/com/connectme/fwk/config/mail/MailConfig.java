package com.connectme.fwk.config.mail;

import java.io.IOException;
import java.util.Properties;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.VelocityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.ui.velocity.VelocityEngineFactory;


/**
 * Mail configuration:
 *
 * @author santosh joshi
 *
 */
@Configuration
public class MailConfig{

    @Autowired
    Environment env;
    
    /**
     * 
     * <bean id="mailSender" class="org.springframework.mail.javamail.JavaMailSenderImpl">
        	<property name="host" value="smtp.gmail.com" />
        	<property name="port" value="465" />
        	<property name="username" value="santoshjoshi2003@gmail.com" />
        	<property name="password" value="password" />
        	<property name="javaMailProperties">
                    <props>
                        <prop key="mail.smtp.auth">true</prop>
                        <prop key="mail.smtp.starttls.enable">true</prop>
                        <prop key="mail.smtp.ssl.trust">smtp.gmail.com</prop>
                        <prop key="mail.debug">true</prop>
                        <prop key="mail.smtp.socketFactory.class">javax.net.ssl.SSLSocketFactory</prop>
        				<prop key="mail.smtp.socketFactory.fallback">false</prop>
                    </props>
        	</property>
	</bean>
     * @return
     */
   @Bean
   public JavaMailSenderImpl javaMailSender(){
       JavaMailSenderImpl  javaMailSender = new  JavaMailSenderImpl();
      
      javaMailSender.setHost(env.getProperty("smtp.host"));
      javaMailSender.setPort(Integer.parseInt(env.getProperty("smtp.port")));
      javaMailSender.setUsername(env.getProperty("smtp.username"));
      javaMailSender.setPassword(env.getProperty("smtp.password"));
      
      Properties javaMailProperties = new Properties();
      javaMailProperties.setProperty("mail.smtp.auth", Boolean.TRUE.toString());
      javaMailProperties.setProperty("mail.smtp.starttls.enable", Boolean.TRUE.toString());
      javaMailProperties.setProperty("mail.debug", Boolean.TRUE.toString());
      javaMailProperties.setProperty("mail.smtp.socketFactory.fallback", Boolean.TRUE.toString());
      javaMailProperties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
      javaMailProperties.setProperty("mail.smtp.ssl.trust", "smtp.gmail.com");
      
      javaMailSender.setJavaMailProperties(javaMailProperties);
      
      return javaMailSender;
   }
   
   /**
    * 
    *  <bean id="velocityEngine" class="org.springframework.ui.velocity.VelocityEngineFactoryBean"
          p:resourceLoaderPath="classpath:/org/springbyexample/email"
          p:preferFileSystemAccess="false"/>
    */

   @Bean
   public VelocityEngine velocity() throws VelocityException, IOException{
       VelocityEngineFactory factory = new VelocityEngineFactory();
       Properties props = new Properties();
       props.put("resource.loader", "class");
       props.put("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
       factory.setVelocityProperties(props);
       return factory.createVelocityEngine();
   }
   
   
   /**
    * 
    * <bean id="templateMessage" class="org.springframework.mail.SimpleMailMessage"
          p:from="dwinterfeldt@springbyexample.org"
          p:to="${mail.recipient}"
          p:subject="Greetings from Spring by Example" />
    */
   
   @Bean(name={"templateMessageForgotPassword"})
   public SimpleMailMessage templateMessageForgotPassword(){
       
       SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
       simpleMailMessage.setFrom("interestify@gmail.com");
       simpleMailMessage.setSubject("Your New Password");
       
       return simpleMailMessage;
   }
   
   @Bean(name={"templateMessageFeedback"})
   public SimpleMailMessage templateMessageFeedback(){
       
       SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
       simpleMailMessage.setFrom("interestify@gmail.com");
       simpleMailMessage.setSubject("User Feedback");
       
       return simpleMailMessage;
   }
   
}
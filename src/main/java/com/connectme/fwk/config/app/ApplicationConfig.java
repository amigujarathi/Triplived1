package com.connectme.fwk.config.app;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;


/**
 * Common Application configuration:
 *
 * This Includes :
 * 	a) Setting up the Properties
 *
 * @author santosh joshi
 *
 */
@Configuration
@PropertySource({"classpath:environment.properties","classpath:rest-client.properties","classpath:activityList.txt","classpath:triplived.properties", "classpath:badges.properties"})

public class ApplicationConfig{

  /* @Bean
   public static PropertyPlaceholderConfigurer properties(){
      PropertyPlaceholderConfigurer propertyPlaceholderConfigurer = new PropertyPlaceholderConfigurer();
      final Resource[] resources = new ClassPathResource[ ] {
         new ClassPathResource( "environment.properties" ),
        //new ClassPathResource( "restfull.properties" )
      };
      propertyPlaceholderConfigurer.setLocations( resources );
      propertyPlaceholderConfigurer.setIgnoreUnresolvablePlaceholders( true );
      return propertyPlaceholderConfigurer;
   }*/

}
package com.connectme.fwk.config.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.theme.CookieThemeResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.tiles2.TilesConfigurer;
import org.springframework.web.servlet.view.tiles2.TilesViewResolver;

import com.connectme.fwk.config.cache.CachingConfig;
import com.connectme.fwk.config.handler.HandlerConfig;
import com.connectme.fwk.config.mail.MailConfig;
import com.connectme.fwk.interceptor.SessionInterceptor;
import com.triplived.interceptor.RequestInterceptor;

/**
 *
 * @author santosh joshi
 *
 * Spring Web Configuration:
 *
 * This Includes:
 * 	a) Configuration for View Resolvers:
 *
 *     Currently two view resolvers are configured
 *     	i) Internal View Resolver
 *     ii) Tiles View Resolver
 *
 *
 */
@Configuration
@Import(value={MailConfig.class,  CachingConfig.class, HandlerConfig.class})
@ComponentScan(basePackages = {"com.gcm","com.connectme.controller", "com.triplived.controller","com.triplived.service",  "com.triplived.rest.client","com.triplived.rest","com.triplived.mail", "com.triplived.dao", "com.dataLogger"})
@EnableWebMvc
@EnableAsync
@EnableScheduling
@ImportResource( { "classpath*:/spring-triplived.xml" } )
public class WebConfig extends WebMvcConfigurerAdapter {

    public WebConfig() {
	super();
    }
    
   @Override
    public void addViewControllers(ViewControllerRegistry registry) {
      registry.addViewController("/").setViewName("redirect:home");
    }

    @Bean
    public  CookieThemeResolver themeResolver() {
	CookieThemeResolver cookieResolver =  new CookieThemeResolver();
	cookieResolver.setCookieName("theme");
	cookieResolver.setDefaultThemeName("standard");
	return cookieResolver;
    }

    /**
     * Internal View Resolver
     * <b>Order is set to 2</b>
     * @return
     */
    @Bean
    public ViewResolver viewResolver() {
	InternalResourceViewResolver resolver = new InternalResourceViewResolver();
	resolver.setPrefix("/WEB-INF/jsp/");
	resolver.setSuffix(".jsp");
	resolver.setOrder(2);
	return resolver;
    }

    /**
     * Configuring Tiles
     */
    @Bean
    public TilesConfigurer tilesConfigurer() {
	TilesConfigurer tilesConfigurer = new TilesConfigurer();
	tilesConfigurer.setDefinitions(new String[]{"classpath:tiles-definitions.xml"});
	return tilesConfigurer;
    }

    /**
     * Tiles View Resolver Configuration
     * <b>Order is set to 1</b>
     */
    @Bean
    public ViewResolver tilesViewResolver() {
	TilesViewResolver resolver = new TilesViewResolver();
	resolver.setOrder(0);
	return resolver;
    }
    
    /**
     *  <mvc:view-controller path="/" view-name="redirect:/site/hotels"/>
     */

    
    
  /*  @Override
    @Bean */
    public LocalValidatorFactoryBean getValidator() {
    	LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
    	//bean.setValidationMessageSource(messageSource());
    	return bean;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
	String pathPatterns[] = { "/message/*", "/userinterest/*", "/addOrUpdate/*", "/search/*", "/addSport/*",
				  "/message", "/userinterest", "/addOrUpdate", "/searchperson", "/region", "/search", "/addSport", "/searchSport", "/latestJoined", "/event"
				};
	registry.addInterceptor(new SessionInterceptor()).addPathPatterns(pathPatterns);
	
	String tlPathPatterns[] = {"/entitySuggest/entities/**", "/analysis_data_logging"};
	registry.addInterceptor(new RequestInterceptor()).addPathPatterns(tlPathPatterns);
    }

    /**
     * 	<!-- Multipart view resolver for files uploading through form -->
	<bean id="multipartResolver"
        class="org.springframework.web.multipart.commons.CommonsMultipartResolver">

        <!-- one of the properties available; the maximum file size in bytes -->
        <property name="maxUploadSize" value="10000000" />
    </bean>
     */
    
    @Bean(name = "multipartResolver")
	public CommonsMultipartResolver  multipartResolver() {

		//MultipartResolver  commonsMultipartResolver  = new CommonsMultipartResolver();
    	CommonsMultipartResolver  commonsMultipartResolver  = new CommonsMultipartResolver();
    	commonsMultipartResolver.setMaxUploadSize(500*1024*1204);
		return commonsMultipartResolver ;
	}
}

/**
 * The new @EnableWebMvc annotation does a number of useful things
 * specifically, in the case of REST, it detect the existence of Jackson and
 * JAXB 2 on the classpath and automatically creates and registers default JSON
 * and XML converters. The functionality of the annotation is equivalent to the
 * XML version: <mvc:annotation-driven /> Read more:
 * http://www.javacodegeeks.com
 * /2011/11/building-restful-web-service-with.html#ixzz1qgIdbe6o
 */

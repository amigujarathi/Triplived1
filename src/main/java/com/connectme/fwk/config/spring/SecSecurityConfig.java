package com.connectme.fwk.config.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AnonymousAuthenticationProvider;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.connectme.fwk.config.spring.auth.AuthenticationFailureHandler;
import com.connectme.fwk.config.spring.auth.AuthenticationSuccessHandler;
import com.connectme.fwk.config.spring.filter.CustomUsernamePasswordAuthenticationFilter;
import com.triplived.service.auth.impl.AuthServiceImpl;



@Configuration
@EnableWebSecurity
//@ImportResource({ "classpath:spring/securityContext.xml" })
public class SecSecurityConfig extends WebSecurityConfigurerAdapter {


    
    public SecSecurityConfig() {
        super();
    }
 
    @Override
    public void configure(WebSecurity security)
    {
        security.ignoring().antMatchers("/static/**", "/favicon.ico");
    }

    @Override
    protected void configure(HttpSecurity security) throws Exception
    {
        security.
        	csrf().disable()
        	.addFilter(getUserNamePasswordAuthenticationFilter())
                .formLogin()
                    .loginPage("/home/login").failureUrl("/accessdenied")
                    .defaultSuccessUrl("/home")
                    .successHandler(getSuccessHandler())
                    .failureHandler(getFailureHandler())
                    .usernameParameter("email")
                    .passwordParameter("password")
                .and().logout()
                    .logoutUrl("/logout").logoutSuccessUrl("/home/login")
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID")
                    .and()
                    .userDetailsService(userDetailService());
        
        security.rememberMe().userDetailsService(userDetailService()).tokenValiditySeconds(84600).key("sec-key");
        
    }

    private AuthServiceImpl userDetailService() {
    	//return new AuthServiceImpl (this.searchService);
    	return new AuthServiceImpl ();
    }

    private AuthenticationProvider getAuthenticationProvider() {
    	DaoAuthenticationProvider daoAuthenticationProvider=  new DaoAuthenticationProvider();
    	daoAuthenticationProvider.setUserDetailsService(userDetailsService());
    	return daoAuthenticationProvider;
    }
    
    private AuthenticationFailureHandler getFailureHandler() {
	return new AuthenticationFailureHandler();
    }
    
    /* (non-Javadoc)
     * @see org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter#userDetailsService()
     */
    
    
    private static final String ANON_PROVIDER_KEY = "9000234288201316478";

    private ProviderManager getAuthenticationManager() {
	List<AuthenticationProvider> authManagers = new ArrayList<AuthenticationProvider>();
	AuthenticationProvider daoProvider = getAuthenticationProvider();
	authManagers.add(daoProvider);

	authManagers.add(new AnonymousAuthenticationProvider(ANON_PROVIDER_KEY));
	ProviderManager providerManager = new ProviderManager(authManagers);

	return providerManager;
    }
    
 
   public  UsernamePasswordAuthenticationFilter getUserNamePasswordAuthenticationFilter() {
        UsernamePasswordAuthenticationFilter filter = new CustomUsernamePasswordAuthenticationFilter();

        filter.setAllowSessionCreation(false);
        // Set the Auth Success handler
        filter.setAuthenticationSuccessHandler(getSuccessHandler());
        filter.setAuthenticationFailureHandler(getFailureHandler());
        filter.setPostOnly(true);
      filter.setAuthenticationManager(getAuthenticationManager());
        filter.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/home/login"));

        return filter;
      }

   @Bean
   public SimpleUrlAuthenticationSuccessHandler getSuccessHandler() {
	
	return new AuthenticationSuccessHandler();
    }
 
  /*   @Bean(name = "springSecurityFilterChain")
     public FilterChainProxy getFilterChainProxy() {
       SecurityFilterChain chain = new SecurityFilterChain() {

         @Override
         public boolean matches(HttpServletRequest request) {
           // All goes through here
           return true;
         }

         @Override
         public List<Filter> getFilters() {
           List<Filter> filters = new ArrayList<Filter>();

           filters.add(getCookieAuthenticationFilter());
           filters.add(getLogoutFilter());
           filters.add(getUserNamePasswordAuthenticationFilter());
           filters.add(getSecurityContextHolderAwareRequestFilter());
           filters.add(getAnonymousAuthenticationFilter());
           filters.add(getExceptionTranslationFilter());
           filters.add(getFilterSecurityInterceptor());

           return filters;
         }
       };
       
       return new FilterChainProxy(chain);
     }
 */
       
}

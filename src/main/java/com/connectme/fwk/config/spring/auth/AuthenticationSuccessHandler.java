/**
 * 
 */
package com.connectme.fwk.config.spring.auth;

import java.io.IOException;

import java.io.Writer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import com.connectme.controller.home.LoginSignUpStatus;



/**
 * @author santosh joshi
 *
 */
public class AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler  {

    
    
    /* (non-Javadoc)
     * @see org.springframework.security.web.authentication.AbstractAuthenticationTargetUrlRequestHandler#handle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.springframework.security.core.Authentication)
     */
    @Override
    protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        super.handle(request, response, authentication);
    }
    
    
    /* (non-Javadoc)
     * @see org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler#onAuthenticationSuccess(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.springframework.security.core.Authentication)
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {}	
    
    private String getReturnUrl(HttpServletRequest request, HttpServletResponse response) {
	RequestCache requestCache = new HttpSessionRequestCache();
	SavedRequest savedRequest = requestCache.getRequest(request, response);
	if (savedRequest == null) {
		return "home";
	}
	return savedRequest.getRedirectUrl();
	
}
    
/*    @Override
    protected void onSuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, Authentication authResult)
    throws IOException {
 
        HttpServletResponseWrapper responseWrapper = new HttpServletResponseWrapper(response);
 
        Writer out = responseWrapper.getWriter();
 
        String targetUrl = determineTargetUrl( request, response );
        out.write("{success:true, targetUrl : \'" + targetUrl + "\'}");
        out.close();
 
    }*/
 
 /*   @Override
    protected void onUnsuccessfulAuthentication( HttpServletRequest request,
            HttpServletResponse response, AuthenticationException failed )
    throws IOException {
 
        HttpServletResponseWrapper responseWrapper = new HttpServletResponseWrapper(response);
 
        Writer out = responseWrapper.getWriter();
 
        out.write("{ success: false, errors: { reason: 'Login failed. Try again.' }}");
        out.close();
 
    }*/
}

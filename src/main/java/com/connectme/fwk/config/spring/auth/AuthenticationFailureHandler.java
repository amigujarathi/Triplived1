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
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import com.connectme.controller.home.LoginSignUpStatus;

/**
 * @author santosh joshi
 *
 */
public class AuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler  {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {
	HttpServletResponseWrapper responseWrapper = new HttpServletResponseWrapper(response);
	 
	LoginSignUpStatus loginSignUpStatus = new LoginSignUpStatus();
	loginSignUpStatus.setRedirectUrl("");
	
	ObjectMapper om = new ObjectMapper();
	
        Writer out = responseWrapper.getWriter();
        out.write(om.writeValueAsString(loginSignUpStatus));
        out.close();
    }
}

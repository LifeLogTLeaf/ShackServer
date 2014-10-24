package org.soma.tleaf.util;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soma.tleaf.accesskey.AccessKeyManager;
import org.soma.tleaf.exception.DatabaseConnectionException;
import org.soma.tleaf.exception.InvalidAccessKeyException;
import org.soma.tleaf.exception.ParameterInsufficientException;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * Checks Requests that need OAuth Token
 * @author susu
 * Date : Oct 24, 2014 10:10:28 PM
 */
public class OauthFilter implements Filter {

	private AccessKeyManager accessKeyManager;

	private static Logger logger = LoggerFactory.getLogger(OauthFilter.class);
	
	private final String USERID_PARAM_NAME = "userId";
	private final String APPID_PARAM_NAME = "appId";
	private final String ACCESSKEY_PARAM_NAME = "accessKey";

	@Override
	public void init ( FilterConfig filterConfig ) throws ServletException {
		ApplicationContext ctx = WebApplicationContextUtils.
				getRequiredWebApplicationContext(filterConfig.getServletContext());
		accessKeyManager = ctx.getBean(AccessKeyManager.class);
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		logger.info( "Handling Oauth Request... " );
		
		String accessKey = request.getParameter( ACCESSKEY_PARAM_NAME );
		String userId = request.getParameter( USERID_PARAM_NAME );
		String appId = request.getParameter( APPID_PARAM_NAME );

		// Be Careful on this, Every Request goes into Filter. Logging might create Performance Issues
		logger.info( accessKey ); logger.info( userId ); logger.info( appId );

		if ( accessKey == null || userId == null || appId == null )
			throw new ServletException( 
				"Needed Parameter is missing. Access Key, Application ID, User ID", 
				new ParameterInsufficientException() );
		
		try {
			
			accessKeyManager.isAccessKeyValid(accessKey, appId, userId);
			
		} catch (InvalidAccessKeyException e) {
			e.printStackTrace();
			throw new ServletException( 
					"Access Key, Application ID, User ID Does't match, or Your Access Key is INVALID", 
					e );
		} catch (DatabaseConnectionException e) {
			e.printStackTrace();
			throw new ServletException( 
					"Failed to Connect to API_KEY Database. Please Try again", 
					e );
		}

		chain.doFilter(request, response);		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}

}
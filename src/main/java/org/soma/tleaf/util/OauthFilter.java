package org.soma.tleaf.util;

import java.io.IOException;

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
import org.soma.tleaf.exception.CustomExceptionValue;
import org.soma.tleaf.exception.DatabaseConnectionException;
import org.soma.tleaf.exception.InvalidAccessKeyException;
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

	private final String USERID_HEADER_NAME = "x-tleaf-user-id";
	private final String APPID_HEADER_NAME = "x-tleaf-application-id"; // Same as other company's API Key
	private final String ACCESSKEY_HEADER_NAME = "x-tleaf-access-token";

	@Override
	public void init ( FilterConfig filterConfig ) throws ServletException {
		ApplicationContext ctx = WebApplicationContextUtils.
				getRequiredWebApplicationContext(filterConfig.getServletContext());
		accessKeyManager = ctx.getBean(AccessKeyManager.class);
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		if (!(request instanceof HttpServletRequest) || !(response instanceof HttpServletResponse)) {
			throw new ServletException("OncePerRequestFilter just supports HTTP requests");
		}
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;

		if( !httpRequest.getMethod().matches("OPTIONS") )
		{
			String accessKey = httpRequest.getHeader(ACCESSKEY_HEADER_NAME);
			String userId = httpRequest.getHeader(USERID_HEADER_NAME);
			String appId = httpRequest.getHeader(APPID_HEADER_NAME);

			// Be Careful on this, Every Request goes into Filter. Logging might create Performance Issues
			logger.info( accessKey ); logger.info( userId ); logger.info( appId );

			if ( accessKey == null || userId == null || appId == null ) {
				httpRequest.setAttribute("FilterException", CustomExceptionValue.Auth_Info_Insufficient_Exception );
			}

			else {
				try {
					accessKeyManager.isAccessKeyValid(accessKey, appId, userId);
				} catch (InvalidAccessKeyException e) {
					e.printStackTrace();
					httpRequest.setAttribute("FilterException", CustomExceptionValue.Invalid_AccessKey_Exception );
				} catch (DatabaseConnectionException e) {
					e.printStackTrace();
					httpRequest.setAttribute("FilterException", CustomExceptionValue.Database_Connection_Exception );
				}
			}
		}

		chain.doFilter(httpRequest, httpResponse);		
	}

	@Override
	public void destroy() {
	}

}
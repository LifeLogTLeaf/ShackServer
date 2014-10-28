/**
 * 
 */
package org.soma.tleaf.util;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Created with Eclipse IDE
 * Author : RichardJ
 * Date : Oct 23, 2014 9:35:11 AM
 * Description : 크로스 오리진 리소스 쉐어링 필터 클래스입니다.
 */
public class CorsFilter extends OncePerRequestFilter {
	static Logger logger = LoggerFactory.getLogger(CorsFilter.class);

	@Override
	protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			FilterChain filterChain) throws ServletException, IOException {
		logger.info("Filtered...");
		logger.info("HTTP METHOD : " + httpServletRequest.getMethod());

		if (httpServletRequest.getHeader("Access-Control-Request-Method") != null && "OPTIONS".equals(httpServletRequest.getMethod())) {
			logger.info("Add some code to Header ");

			httpServletResponse.addHeader("Access-Control-Allow-Origin", "*");
			httpServletResponse.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
			httpServletResponse.addHeader("Access-Control-Allow-Headers",  "origin, content-type, accept, x-requested-with, sid, mycustom, smuser");
			httpServletResponse.addHeader("Access-Control-Max-Age", "1800");// 30min
		}
		filterChain.doFilter(httpServletRequest, httpServletResponse);

	}
}

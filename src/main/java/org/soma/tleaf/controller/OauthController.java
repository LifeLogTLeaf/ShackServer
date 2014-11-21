package org.soma.tleaf.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soma.tleaf.accesskey.AccessKey;
import org.soma.tleaf.accesskey.OauthManager;
import org.soma.tleaf.dao.UserDao;
import org.soma.tleaf.domain.LoginRequest;
import org.soma.tleaf.domain.UserInfo;
import org.soma.tleaf.exception.CustomException;
import org.soma.tleaf.exception.CustomExceptionFactory;
import org.soma.tleaf.exception.CustomExceptionValue;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class OauthController {

	@Inject
	private OauthManager oauthManager;
	
	@Inject
	private CustomExceptionFactory customExceptionFactory;
	
	@Inject
	private UserDao userDao;
	
	// 300000 = 300sec = 5min
	// 3600000 = 1 hour
	// 86400000 = 1 day
	private final Long DEFAULT_DURATION = (long) 86400000;
	
	private static final Logger logger = LoggerFactory.getLogger(OauthController.class);
	
	/**
	 * with default, it creates an AccessKey valid from the time called.
	 * @author susu
	 * Date Oct 24, 2014 12:35:38 AM
	 * @param userId
	 * @param validForMillis
	 * @param isValid
	 * @return TLeaf Login Page
	 * @throws CustomException if AppId is Invalid
	 */
	@RequestMapping( value = "oauth" , method = RequestMethod.GET )
	public ModelMap oauth ( ModelMap model,
			@RequestParam( value = "appId" , required = true ) String appId ) throws CustomException {
		
		logger.info( appId + " requesting Login API");
		
		if ( !oauthManager.isAppIdValid(appId) ) throw customExceptionFactory.createCustomException( CustomExceptionValue.Invalid_App_Id_Exception );
		 
		model.put("code", oauthManager.createLoginAccessCode(appId));
		model.put("appId", appId);
		
		return model;
	}
	
	@RequestMapping( value = "oauth/login" , method = RequestMethod.POST )
	public ResponseEntity<AccessKey> userLogin ( HttpServletRequest httpRequest,
			@RequestBody( required = true ) LoginRequest request ) throws CustomException {
		
		String userAgent = httpRequest.getHeader("user-agent");
		logger.info( userAgent ); logger.info( request.getCode() ); logger.info( request.getAppId() );
		
		if ( request.getAppId() == null || request.getEmail() == null || request.getPassword() == null || ( userAgent.contains("Android") && request.getCode() == null ) )
			throw customExceptionFactory.createCustomException( CustomExceptionValue.Parameter_Insufficient_Exception );
		
		if ( userAgent.contains("Android") ) {
			// throws Exception If Not Correct
			oauthManager.checkLoginAccessCode( request.getCode(), request.getAppId() );
		}
		
		// Still, WrongAuthenticationInfoException Can occur
		return new ResponseEntity<AccessKey>( userDao.userLogin( request.getEmail(), request.getPassword(), request.getAppId() ), HttpStatus.OK );
	}
	
	@RequestMapping( value = "oauth/signup" , method = RequestMethod.POST )
	public ResponseEntity<String> userSignUp ( HttpServletRequest httpRequest,
			@RequestBody( required = true ) LoginRequest request ) throws CustomException {
		
		String userAgent = httpRequest.getHeader("user-agent");
		logger.info( userAgent );
		
		if ( ( userAgent.contains("Android") && request.getCode() == null ) || request.getAppId() == null || request.getEmail() == null || request.getPassword() == null
				|| request.getAge() == null || /*request.getGender() == null ||*/ request.getNickname() == null /*|| request.getRedirect() == null */)
			throw customExceptionFactory.createCustomException( CustomExceptionValue.Parameter_Insufficient_Exception );
		
		if ( userAgent.contains("Android") ) {
			// throws Exception If Not Correct
			oauthManager.checkLoginAccessCode(request.getCode(),request.getAppId());
		}
		
		UserInfo userInfo = new UserInfo();
		userInfo.setEmail(request.getEmail());
		userInfo.setPassword(request.getPassword());
		userInfo.setNickname(request.getNickname());
		userInfo.setAge(request.getAge());
		userInfo.setGender(request.getGender());
		
		return new ResponseEntity<String>( userDao.userSignUp(userInfo),HttpStatus.CREATED );
	}

}

package org.soma.tleaf.controller;

import java.util.HashMap;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soma.tleaf.accesskey.AccessKey;
import org.soma.tleaf.accesskey.OauthManager;
import org.soma.tleaf.dao.UserDao;
import org.soma.tleaf.domain.UserInfo;
import org.soma.tleaf.exception.CustomException;
import org.soma.tleaf.exception.CustomExceptionFactory;
import org.soma.tleaf.exception.CustomExceptionValue;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

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
	public ModelAndView oauth (
			@RequestParam( value = "appId" , required = true ) String appId ) throws CustomException {
		
		logger.info( appId + " requesting Login API");
		
		if ( !oauthManager.isAppIdValid(appId) ) throw customExceptionFactory.createCustomException( CustomExceptionValue.Invalid_App_Id_Exception );
		
		ModelAndView model = new ModelAndView("login");
		
		HashMap< String,String > modelMap = new HashMap< String,String >(); 
		modelMap.put("code", oauthManager.createLoginAccessCode(appId));
		
		model.addAllObjects(modelMap);
		return model;
	}
	
	@RequestMapping( value = "oauth/login" , method = RequestMethod.POST )
	public ResponseEntity<AccessKey> userLogin (
			@RequestBody( required = true ) String code, 
			@RequestBody( required = true ) String appId,
			@RequestBody( required = true ) String email,
			@RequestBody( required = true ) String password,
			@RequestBody( required = true ) String redirect ) throws CustomException {
		
		// throws Exception If Not Correct
		oauthManager.checkLoginAccessCode(code);
		
		// Still, WrongAuthenticationInfoException Can occur
		return new ResponseEntity<AccessKey>( userDao.userLogin(email, password), HttpStatus.OK );
	}
	
	@RequestMapping( value = "oauth/signup" , method = RequestMethod.POST )
	public ResponseEntity<AccessKey> userSignUp (
			@RequestBody( required = true ) String code, 
			@RequestBody( required = true ) String appId,
			@RequestBody( required = true ) String email,
			@RequestBody( required = true ) String password,
			@RequestBody( required = true ) String nickname,
			@RequestBody( required = true ) String gender,
			@RequestBody( required = true ) int age,
			@RequestBody( required = true ) String redirect ) throws CustomException {
		
		// throws Exception If Not Correct
		oauthManager.checkLoginAccessCode(code);
		
		UserInfo userInfo = new UserInfo();
		userInfo.setEmail(email);
		userInfo.setPassword(password);
		userInfo.setAge(age);
		userInfo.setGender(gender);
		
		return new ResponseEntity<AccessKey>( userDao.userSignUp(userInfo),HttpStatus.CREATED );
	}

}

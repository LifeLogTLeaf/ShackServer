package org.soma.tleaf.controller;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soma.tleaf.accesskey.AccessKey;
import org.soma.tleaf.accesskey.AccessKeyManager;
import org.soma.tleaf.exception.DatabaseConnectionException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class OauthController {

	@Inject
	private AccessKeyManager accessKeyManager;
	
	// 300000 = 300sec = 5min
	// 3600000 = 1 hour
	// 86400000 = 1 day
	private final Long DEFAULT_DURATION = (long) 300000;
	
	private static final Logger logger = LoggerFactory.getLogger(OauthController.class);
	
	/**
	 * with default, it creates an AccessKey valid from the time called.
	 * @author susu
	 * Date Oct 24, 2014 12:35:38 AM
	 * @param userId
	 * @param validForMillis
	 * @param isValid
	 * @return AccessKey Object
	 * @throws DatabaseConnectionException
	 */
	@RequestMapping("oauth")
	@ResponseBody
	public AccessKey oauth ( @RequestParam( value = "userId" , required = true ) String userId,
			@RequestParam( value = "validForMillis" , required = false ) Long validForMillis,
			@RequestParam( value = "isValid" , required = false , defaultValue = "true" ) boolean isValid) throws DatabaseConnectionException {
		
		logger.info( userId );
		
		if( validForMillis == null ) validForMillis = DEFAULT_DURATION;
		return accessKeyManager.createAccessKey(userId, validForMillis, isValid);
	}
	
	/*
 
	@RequestMapping("oauth")
	@ResponseBody
	public AccessKey oauth ( @RequestParam( value = "userId" , required = true ) String userId,
			@RequestParam( value = "validFrom" , required = true ) String validFrom,
			@RequestParam( value = "validTo" , required = true ) String validTo,
			@RequestParam( value = "isValid" , required = false , defaultValue = "true" ) boolean isValid) throws DatabaseConnectionException {
		
		logger.info( userId );
		
		return accessKeyManager.createAccessKey(userId, validFrom, validTo , isValid);
		//public AccessKey createAccessKey ( String userId, String vaildFrom, String vaildTo, boolean isValid );
	}
	
	@RequestMapping("oauth")
	@ResponseBody
	public AccessKey oauth ( @RequestParam( value = "userId" , required = true ) String userId,
			@RequestParam( value = "validFrom" , required = true ) String validFrom,
			@RequestParam( value = "validForMillis" , required = true ) Long validForMillis,
			@RequestParam( value = "isValid" , required = false , defaultValue = "true" ) boolean isValid) throws DatabaseConnectionException {
		
		logger.info( userId );
		
		return accessKeyManager.createAccessKey(userId, validFrom, validForMillis, isValid);
		//public AccessKey createAccessKey ( String userId, String vaildFrom, Long vaildForMillis, boolean isValid );
	}
	
	@RequestMapping("oauth")
	@ResponseBody
	public AccessKey oauth ( @RequestParam( value = "userId" , required = true ) String userId,
			@RequestParam( value = "validTo" , required = true ) String validTo,
			@RequestParam( value = "isValid" , required = false , defaultValue = "true" ) boolean isValid) throws DatabaseConnectionException {
		
		logger.info( userId );
		
		return accessKeyManager.createAccessKey(userId, validTo, isValid);
		//public AccessKey createAccessKey ( String userId, String vaildTo, boolean isValid );
	}
	
	*/

}

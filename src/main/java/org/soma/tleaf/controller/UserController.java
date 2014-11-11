package org.soma.tleaf.controller;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soma.tleaf.accesskey.AccessKey;
import org.soma.tleaf.couchdb.UserDao;
import org.soma.tleaf.domain.UserInfo;
import org.soma.tleaf.exception.CustomException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wordnik.swagger.annotations.ApiOperation;

/** Handles User related methods. ( login, signup, signout )
 * 2014.10.14
 * 
 * 2014.10.27
 * Changed Exception Handling ( FilterException, ExceptionFactory )
 * Now Able to read Json format data
 * 
 * @author susu Handles Requests related to Users
 */
@Controller
public class UserController {

	@Inject
	private UserDao userDao;
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	/** Handles User Login. value = "user/login", method = RequestMethod.POST
	 * 
	 * @author susu
	 * Date Nov 3, 2014 3:20:44 PM
	 * @param UserInfo UserInfo Class with Email and Password
	 * @return AccessKey AccessKey JSON String.
	 * @throws CustomException DatabaseConnectionException , NoSuchUserException , WrongAuthenticationInfoException
	 */
	@RequestMapping(value = "user/login", method = RequestMethod.POST )
	@ResponseBody
	@ApiOperation( httpMethod = "POST" , value = "User Login" )
	public AccessKey userLogin( @RequestBody UserInfo userInfo )
			throws CustomException {

		return userDao.userLogin( userInfo.getEmail(), userInfo.getPassword() );

	}
	
	/** New User Signing Up.
	 * 
	 * @author susu
	 * Date Nov 3, 2014 3:29:28 PM
	 * @param model
	 * @param UserInfo UserInfo Object with Specific information of the New User
	 * @return JSON String to tell about the SignUp Process
	 * @throws CustomException
	 */
	@RequestMapping(value = "user/signup", method = RequestMethod.POST )
	@ResponseBody
	@ApiOperation( httpMethod = "POST" , value = "New User SignUp" )
	public String userSignup( @RequestBody UserInfo userInfo )
			throws CustomException {
		
		logger.info( userInfo.getEmail() );
		return userDao.userSignUp ( userInfo );
	}

	/** Deletes User Database,Info if Email and Password is correct
	 * 
	 * @author susu
	 * Date Nov 3, 2014 3:34:08 PM
	 * @param model
	 * @param UserInfo UserInfo Object with Email and Password
	 * @return JSON String to tell about the SignUp Process
	 * @throws CustomException
	 */
	@RequestMapping(value = "user/signout", method = RequestMethod.DELETE )
	@ResponseBody
	@ApiOperation( httpMethod = "DELETE" , value = "Delete Existing User" )
	public String userSignout( @RequestBody UserInfo userInfo) throws CustomException {

		return userDao.userSignOut( userInfo.getEmail(), userInfo.getPassword() );
	}	

}

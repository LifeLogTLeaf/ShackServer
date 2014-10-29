package org.soma.tleaf.controller;

import javax.inject.Inject;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soma.tleaf.accesskey.AccessKey;
import org.soma.tleaf.couchdb.UserDao;
import org.soma.tleaf.domain.UserInfo;
import org.soma.tleaf.exception.CustomException;
import org.soma.tleaf.exception.CustomExceptionFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
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
	
	@Inject
	private CustomExceptionFactory customExceptionFactory;
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	/**
	 * 2014.10.18 User home
	 */
	@RequestMapping(value = "user/")
	public String user(Model model) {
		return "user";
	}

	/**
	 * 2014.10.15 Handles User login
	 */
	@RequestMapping(value = "user/login")
	public String userLogin(Model model) {
		return "login";
	}

	@RequestMapping(value = "user/login", method = RequestMethod.POST )
	@ResponseBody
	public AccessKey userLogin(Model model, @RequestBody UserInfo userInfo )
			throws CustomException {

		return userDao.userLogin( userInfo.getEmail(), userInfo.getPassword() );

	}

	/**
	 * 2014.10.15 Handles User logout. not Needed in Rest Server
	 */
	@RequestMapping(value = "user/logout")
	public String userLogout(Model model, HttpServletResponse response) {

		Cookie cookie = new Cookie("LoginStatus", "Log in first");
		response.addCookie(cookie);
	
		return "redirect:";
	}

	/**
	 * 2014.10.16 Handles User Sign up
	 */
	@RequestMapping(value = "user/signup")
	public String userSignup(Model model) {
		return "signup";
	}

	@RequestMapping(value = "user/signup", method = RequestMethod.POST )
	@ResponseBody
	public String userSignup(Model model, @RequestBody UserInfo userInfo )
			throws CustomException {
		
		logger.info( userInfo.getEmail() );
		 return userDao.userSignUp ( userInfo.getEmail(), userInfo.getPassword(), userInfo.getNickname(), userInfo.getGender(), userInfo.getAge());
	}

	/**
	 * 2014.10.17 Handles User Sign Out. Deletes User data
	 */
	@RequestMapping(value = "user/signout")
	public String userSignout(Model model) {
		return "signout";
	}

	@RequestMapping(value = "user/signout", method = RequestMethod.DELETE )
	@ResponseBody
	public String userSignout(Model model, @RequestBody UserInfo userInfo) throws CustomException {

		return userDao.userSignOut( userInfo.getEmail(), userInfo.getPassword() );

	}

}

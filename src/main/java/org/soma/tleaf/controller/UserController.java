package org.soma.tleaf.controller;

import javax.inject.Inject;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.soma.tleaf.couchdb.UserDao;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 2014.10.14
 * @author susu
 * Handles Requests related to Users
 */

@Controller

public class UserController {
	
	@Inject
	private UserDao userDao;
	
	/**
	 * 2014.10.18
	 * User home
	 */
	@RequestMapping( value = "user/" )
	public String user ( Model model ) {
		return "user";
	}
	
	/**
	 * 2014.10.15
	 * Handles User login
	 */
	@RequestMapping( value = "user/login" )
	public String userLogin ( Model model ) {
		return "login";
	}
	
	@RequestMapping( value = "user/login" ,method = RequestMethod.POST )
	public String userLogin ( Model model,String email1 ,String email2, String pw, HttpServletResponse response ) {
		System.out.println( email1 + "@" + email2 + "\n" + pw);
		
		return userDao.userLogin( email1 + "@" + email2 , pw , response );
	}
	
	/**
	 * 2014.10.15
	 * Handles User login
	 */
	@RequestMapping( value = "user/logout" )
	public String userLogout ( Model model, HttpServletResponse response ) {
		
		Cookie cookie = new Cookie( "LoginStatus" , "Log in first" );
		response.addCookie( cookie );
		
		return "redirect:";
	}
	
	/**
	 * 2014.10.16
	 * Handles User Sign up
	 */
	@RequestMapping( value = "user/signup" )
	public String userSignup ( Model model ) {
		return "signup";
	}
	
	@RequestMapping( value = "user/signup" ,method = RequestMethod.POST )
	public String userSignup ( Model model,String email1 ,String email2, String pw, String nickname, String gender, Integer age , HttpServletResponse response) {
		
		Cookie cookie = new Cookie( "LoginStatus" , "Log in first" );
		response.addCookie( cookie );
		
		return userDao.userSignUp( email1 + "@" + email2 , pw , nickname, gender, age );
	}
	
	/**
	 * 2014.10.17
	 * Handles User Sign Out. Deletes User data
	 */
	@RequestMapping( value = "user/signout" )
	public String userSignout ( Model model ) {
		return "signout";
	}
	
	@RequestMapping( value = "user/signout" ,method = RequestMethod.POST )
	@ResponseBody
	public String userSignout ( Model model,String email1 ,String email2, String pw, HttpServletResponse response ) {
		
		System.out.println( "User Signing out" );
		System.out.println( email1 + "@" + email2 );
		System.out.println( pw );
		
		return userDao.userSignOut( email1 + "@" + email2 , pw, response );
	}

}

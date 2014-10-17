package org.soma.tleaf;

import javax.inject.Inject;

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
	 * 2014.10.15
	 * Handles User login
	 */
	@RequestMapping( value = "user/login" )
	public String user ( Model model) {
		return "login";
	}
	
	@RequestMapping( value = "user/login" ,method = RequestMethod.POST )
	@ResponseBody
	public String user ( Model model,String email1 ,String email2, String pw ) {
		System.out.println( email1 + "@" + email2 + "\n" + pw);
		
		return userDao.userLogin( email1 + "@" + email2 , pw );
	}
	
	/**
	 * 2014.10.16
	 * Handles User Sign up
	 */
	@RequestMapping( value = "user/signup" )
	public String signup ( Model model ) {
		return "signup";
	}
	
	@RequestMapping( value = "user/signup" ,method = RequestMethod.POST )
	@ResponseBody
	public String signup ( Model model,String email1 ,String email2, String pw, String nickname, String gender, Integer age ) {
		
		System.out.println( email1 + "@" + email2 );
		System.out.println( pw );
		System.out.println( nickname );
		System.out.println( gender );
		System.out.println( age );
		
		return userDao.userSignUp( email1 + "@" + email2 , pw , nickname, gender, age );
	}
	
	/**
	 * 2014.10.17
	 * Handles User Sign Out. Deletes User data
	 */
	@RequestMapping( value = "user/signout" )
	public String signout ( Model model ) {
		return "signout";
	}
	
	@RequestMapping( value = "user/signout" ,method = RequestMethod.POST )
	@ResponseBody
	public String signout ( Model model,String email1 ,String email2, String pw ) {
		
		System.out.println( "User Signing out" );
		System.out.println( email1 + "@" + email2 );
		System.out.println( pw );
		
		return userDao.userSignOut( email1 + "@" + email2 , pw );
	}

}

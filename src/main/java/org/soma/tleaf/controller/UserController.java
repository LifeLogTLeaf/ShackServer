package org.soma.tleaf.controller;

import javax.inject.Inject;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;

import org.soma.tleaf.accesskey.AccessKey;
import org.soma.tleaf.couchdb.UserDao;
import org.soma.tleaf.exception.CustomException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 2014.10.14
 * 
 * @author susu Handles Requests related to Users
 */

@Controller
public class UserController {

	@Inject
	private UserDao userDao;

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

	@RequestMapping(value = "user/login", method = RequestMethod.POST)
	@ResponseBody
	public AccessKey userLogin(Model model, String email1, String email2, String pw)
			throws CustomException {

		return userDao.userLogin(email1 + "@" + email2, pw);

	}

	/**
	 * 2014.10.15 Handles User login
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

	@RequestMapping(value = "user/signup", method = RequestMethod.POST , consumes = MediaType.APPLICATION_JSON , produces = MediaType.APPLICATION_JSON )
	@ResponseBody
	public String userSignup(Model model, String email1, String email2,
			String pw, String nickname, String gender, Integer age, HttpServletResponse response)
			throws CustomException {

		response.addHeader("Access-Control-Allow-Origin", "*");
		
		 return userDao.userSignUp (email1 + "@" + email2, pw, nickname, gender, age);
	}

	/**
	 * 2014.10.17 Handles User Sign Out. Deletes User data
	 */
	@RequestMapping(value = "user/signout")
	public String userSignout(Model model) {
		return "signout";
	}

	@RequestMapping(value = "user/signout", method = RequestMethod.POST)
	@ResponseBody
	public String userSignout(Model model, String email1, String email2,
			String pw) throws CustomException {

		return userDao.userSignOut(email1 + "@" + email2, pw);

	}

}

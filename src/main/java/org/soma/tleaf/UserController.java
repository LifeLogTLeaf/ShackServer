package org.soma.tleaf;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 2014.10.14
 * @author susu
 * Handles Requests related to Users
 */

@Controller

public class UserController {
	
	/**
	 * 2014.10.15
	 * Handles User login
	 */
	@RequestMapping( value = "user/" )
	public String user ( Model model ) {
		return "login";
	}
	
	@RequestMapping( value = "user/login" )
	public ModelAndView userLogin ( Model model ) {
		
		ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:login");
        
		return mav;
	}

}

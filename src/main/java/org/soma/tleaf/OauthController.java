package org.soma.tleaf;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class OauthController {

//	@Inject
//	private AccessKeyManager accessKeyManager;
	
	private final Long DEFAULT_DURATION = (long) 300000;
	
	@RequestMapping("oauth")
	@ResponseBody
	public String oauth ( @RequestParam( value = "userId" , required = true ) String userId,
			@RequestParam( value = "validFrom" , required = false ) String validFrom,
			@RequestParam( value = "validTo" , required = false ) String validTo,
			@RequestParam( value = "validForMillis" , required = false ) Long validForMillis,
			@RequestParam( value = "isValid" , required = false , defaultValue = "true" ) boolean isValid) {
		
		System.out.println( userId );
		System.out.println( validFrom );
		System.out.println( validTo );
		System.out.println( validForMillis );
		System.out.println( isValid );
		
		return userId + validFrom + validTo + validForMillis + isValid;
	}
	
}

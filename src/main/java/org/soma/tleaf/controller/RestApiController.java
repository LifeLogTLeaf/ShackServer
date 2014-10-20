/**
 * 
 */
package org.soma.tleaf.controller;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soma.tleaf.domain.RequestDataWrapper;
import org.soma.tleaf.domain.ResponseDataWrapper;
import org.soma.tleaf.service.RestApiService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created with Eclipse IDE
 * Author : RichardJ
 * Date : Oct 17, 2014 1:36:07 PM
 * Description :
 */

@RequestMapping(value = "api/*")
@Controller
public class RestApiController {
	Logger logger = LoggerFactory.getLogger(RestApiController.class);

	@Inject
	private RestApiService restApiService;

	/**
	 * Author : RichardJ
	 * Date : Oct 17, 2014 1:55:06 PM
	 * Description : Test RequestMapping
	 */
	@RequestMapping(value = "/hello/{msg}", method = RequestMethod.GET)
	@ResponseBody
	public String sayHello(@PathVariable String msg) {
		return String.format("You put %s (GET)", msg);
	}
	
	@RequestMapping(value = "/hello/{msg}", method = RequestMethod.POST)
	@ResponseBody
	public String sayHelloPost(@PathVariable String msg) {
		return String.format("You put %s (POST) ", msg);
	}

	/**
	 * Author : RichardJ
	 * Date : Oct 17, 2014 1:55:06 PM
	 * Description : Create Data ( Any service can create their own data )
	 * Issue : 예외처리 아직 안들어갔습니다.
	 */
	@RequestMapping(value = "/log", method = RequestMethod.POST)
	@ResponseBody
	public boolean postUserLog(@RequestBody RequestDataWrapper userLogDataWrapper) {
		logger.info("data size = " + String.valueOf(userLogDataWrapper.checkSize()));
		boolean result = true;
		if (userLogDataWrapper != null && userLogDataWrapper.checkSize() != -1)
			restApiService.postUserData(userLogDataWrapper);
		else
			result = false;
		return result;
	}
	
	/**
	 * Author : RichardJ
	 * Date : Oct 17, 2014 1:55:06 PM
	 * Description : 해당 도큐먼트 아이디의 데이터를 읽어온다.
	 * Issue : 예외처리 아직 안들어갔습니다.
	 */
	@RequestMapping(value = "/log", method = RequestMethod.GET)
	@ResponseBody
	public ResponseDataWrapper getUserLog(@RequestParam(value = "documentId" , required = true) String documentId) {
		logger.info("documentId = " + documentId);
		return restApiService.getUserData(documentId);
	}
}

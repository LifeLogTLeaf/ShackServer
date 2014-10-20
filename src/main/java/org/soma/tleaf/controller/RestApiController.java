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

	/**
	 * Author : RichardJ
	 * Date : Oct 17, 2014 1:55:06 PM
	 * Description : Create Data ( Any service can create their own data )
	 * Issue : 예외처리 아직 안들어갔습니다.
	 */
	@RequestMapping(value = "/user/log", method = RequestMethod.POST)
	@ResponseBody
	public boolean postUserLog(@RequestBody RequestDataWrapper userLogDataWrapper,
			@RequestParam(value = "appId", required = false) String appId,
			@RequestParam(value = "userHashId", required = false) String userHashId,
			@RequestParam(value = "accessKey", required = false) String accessKey) {
		restApiService.postUserData(userLogDataWrapper);
		return Boolean.TRUE;
	}

	/**
	 * Author : RichardJ
	 * Date : Oct 20, 2014 18:55:06
	 * Description : 데이터 전체를 읽어옵니다.
	 * Issue : 파라미터명이 아직 정의되지 않았기때문에 아직 서비스 로직으로 데이터값을 넘기지 않았습니다.
	 */
	@RequestMapping(value = "/user/logs", method = RequestMethod.GET)
	@ResponseBody
	public ResponseDataWrapper getUserLog(@RequestParam(value = "appId", required = false) String appId,
			@RequestParam(value = "userHashId", required = false) String userHashId,
			@RequestParam(value = "accessKey", required = false) String accessKey,
			@RequestParam(value = "limit", required = false) String limit,
			@RequestParam(value = "startKey", required = false) String startKey,
			@RequestParam(value = "endKey", required = false) String endKey) {
		
		return restApiService.getUserData(userHashId);
	}
}

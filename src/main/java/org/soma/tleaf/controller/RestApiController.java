/**
 * 
 */
package org.soma.tleaf.controller;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soma.tleaf.domain.ErrorResponse;
import org.soma.tleaf.domain.RequestDataWrapper;
import org.soma.tleaf.domain.RequestParameter;
import org.soma.tleaf.domain.ResponseDataWrapper;
import org.soma.tleaf.exception.CustomException;
import org.soma.tleaf.exception.ExpiredAccessKeyException;
import org.soma.tleaf.exception.InvalidAccessKeyException;
import org.soma.tleaf.service.RestApiService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created with Eclipse IDE
 * Author : RichardJ
 * Date : Oct 17, 2014 1:36:07 PM
 * Description :
 */

@RequestMapping(value = "api/*")
@Controller
public class RestApiController {
	static Logger logger = LoggerFactory.getLogger(RestApiController.class);

	@Inject
	private RestApiService restApiService;
	
	// Just For Test.
	@RequestMapping(value = "/hello/{msg}", method = RequestMethod.GET)
	@ResponseBody
	public String sayHello(@PathVariable String msg) throws Exception{
		throw new ExpiredAccessKeyException();
	}

	/**
	 * Author : RichardJ
	 * Date : Oct 17, 2014 1:55:06 PM
	 * Description : 클라이언트로부터 데이터 생성 요청을 처리하는 메소드입니다.
	 */
	@RequestMapping(value = "/user/log", method = RequestMethod.POST)
	@ResponseBody
	public ResponseDataWrapper postUserLog(@RequestBody RequestDataWrapper requestDataWrapper,
			@RequestParam(value = "accessKey", required = true) String accessKey,
			@RequestParam(value = "appId", required = true) String appId) throws Exception{
		// Set Request Parameter from @RequestParam
		RequestParameter param = new RequestParameter();
		param.setAccessKey(accessKey);
		param.setAppId(appId);
		// Delegate Request to RestApiService Object
		return restApiService.postUserData(requestDataWrapper, param);
	}


	/**
	 * Author : RichardJ
	 * Date : Oct 20, 2014 18:55:06
	 * Description : 클라이언트로부터 데이터 읽기 요청을 처리하는 메소드입니다.
	 */
	@RequestMapping(value = "/user/logs", method = RequestMethod.GET)
	@ResponseBody
	public ResponseDataWrapper getUserLog(@RequestParam(value = "accessKey", required = false) String accessKey,
			@RequestParam(value = "limit", required = false) String limit,
			@RequestParam(value = "startKey", required = false) String startKey,
			@RequestParam(value = "endKey", required = false) String endKey) {
		// Set Request Parameter from @RequestParam
		RequestParameter param = new RequestParameter();
		param.setAccessKey(accessKey);
		param.setStartKey(startKey);
		param.setEndKey(endKey);
		param.setLimit(limit);

		return restApiService.getUserData(param);
	}
}

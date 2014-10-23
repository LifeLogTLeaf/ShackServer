/**
 * 
 */
package org.soma.tleaf.controller;

import java.util.Map;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soma.tleaf.domain.RequestDataWrapper;
import org.soma.tleaf.domain.RequestParameter;
import org.soma.tleaf.domain.ResponseDataWrapper;
import org.soma.tleaf.exception.ExpiredAccessKeyException;
import org.soma.tleaf.service.RestApiService;
import org.soma.tleaf.util.ISO8601;
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
	 * Issue : 데이터 생성 성공시 클라이언트에 보내줘야할 정보는 ?
	 */
	@RequestMapping(value = "/user/app/log", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> postUserLog(@RequestBody RequestDataWrapper requestDataWrapper,
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
	 * Description : 해당 엑세스키를 사용하는 사용자의 전체 데이터 조회입니다.
	 * Issue : 여기서 받는 앱아이디는 전체 데이터 조회용 ?
	 */
	@RequestMapping(value = "/user/logs", method = RequestMethod.GET)
	@ResponseBody
	public ResponseDataWrapper getUserLog(@RequestParam(value = "accessKey", required = true) String accessKey,
			@RequestParam(value = "appId", required = true) String appId,
			@RequestParam(value = "limit", required = false, defaultValue="1000") String limit,
			@RequestParam(value = "startKey", required = false, defaultValue=ISO8601.FAR_FAR_AWAY) String startKey,
			@RequestParam(value = "endKey", required = false, defaultValue=ISO8601.LONG_LONG_AGO) String endKey) throws Exception{
		// Set Request Parameter from @RequestParam
		RequestParameter param = new RequestParameter();
		param.setAccessKey(accessKey);
		param.setStartKey(startKey);
		param.setEndKey(endKey);
		param.setLimit(limit);
		param.setAppId(appId);
		
		// Delegate Request to RestApiService Object
		return restApiService.getUserData(param);
	}
	
	/**
	 * Author : RichardJ
	 * Date : Oct 20, 2014 18:55:06
	 * Description : 해당 엑세스키와 연결된 사용자의 해당 앱 아이디의 데이터만 조회처리해주는 메소드입니다.
	 */
	@RequestMapping(value = "/user/app/logs", method = RequestMethod.GET)
	@ResponseBody
	public ResponseDataWrapper getUserLogFromAppId(@RequestParam(value = "accessKey", required = true) String accessKey,
			@RequestParam(value = "appId", required = true) String appId,
			@RequestParam(value = "limit", required = false, defaultValue="1000") String limit,
			@RequestParam(value = "startKey", required = false, defaultValue=ISO8601.FAR_FAR_AWAY) String startKey,
			@RequestParam(value = "endKey", required = false, defaultValue=ISO8601.LONG_LONG_AGO) String endKey) throws Exception{
		// Set Request Parameter from @RequestParam
		RequestParameter param = new RequestParameter();
		param.setAccessKey(accessKey);
		param.setStartKey(startKey);
		param.setEndKey(endKey);
		param.setLimit(limit);
		param.setAppId(appId);
		
		// Delegate Request to RestApiService Object
		return restApiService.getUserDataFromAppId(param);
	}
	
	
}

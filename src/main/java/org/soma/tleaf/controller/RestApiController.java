/**
 * 
 */
package org.soma.tleaf.controller;

import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soma.tleaf.domain.RawData;
import org.soma.tleaf.domain.RequestDataWrapper;
import org.soma.tleaf.domain.RequestParameter;
import org.soma.tleaf.domain.ResponseDataWrapper;
import org.soma.tleaf.domain.UserInfo;
import org.soma.tleaf.exception.CustomException;
import org.soma.tleaf.exception.CustomExceptionFactory;
import org.soma.tleaf.exception.CustomExceptionValue;
import org.soma.tleaf.exception.DatabaseConnectionException;
import org.soma.tleaf.service.RestApiService;
import org.soma.tleaf.util.ISO8601;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * Handles API Calls Directly Related to User Data, And So Needs Access Headers(OauthFilter)
 * Created with Eclipse IDE
 * Author : RichardJ
 * Date : Oct 17, 2014 1:36:07 PM
 */
@RequestMapping(value = "api/*")
@Controller
public class RestApiController {
	static Logger logger = LoggerFactory.getLogger(RestApiController.class);

	@Inject
	private RestApiService restApiService;

	@Inject
	private CustomExceptionFactory customExceptionFactory;

	private final String USERID_HEADER_NAME = "x-tleaf-user-id";
	private final String APPID_HEADER_NAME = "x-tleaf-application-id"; // Same as other company's API Key

	// Just For Test.
	@RequestMapping(value = "/hello/{msg}", method = RequestMethod.GET)
	@ResponseBody
	public String sayHello(HttpServletRequest request, @PathVariable String msg) throws CustomException {

		// HttpServletRequest.getAttribute Returns null if Values are not found
		if (request.getAttribute("FilterException") != null)
			throw customExceptionFactory.createCustomException((CustomExceptionValue) request.getAttribute("FilterException"));

		return msg;
	}

	// Just For Test.
	@RequestMapping(value = "/hello/post", method = RequestMethod.POST)
	@ResponseBody
	public RequestDataWrapper sayHelloPost(HttpServletRequest request, @RequestBody RequestDataWrapper requestDataWrapper) throws CustomException {
		if (request.getAttribute("FilterException") != null)
			throw customExceptionFactory.createCustomException((CustomExceptionValue) request.getAttribute("FilterException"));

		//		RequestDataWrapper requestDataWrapper = new RequestDataWrapper();
		//		HashMap<String, Object> someData = new HashMap<String, Object>();
		//		someData.put("(1)", "one");
		//		someData.put("(2)", "two");
		//		someData.put("(3)", "three");
		//		requestDataWrapper.setData(someData);
		return requestDataWrapper;
	}
	
	// Test
	@RequestMapping(value = "/hello/file")
	@ResponseBody
	public String uploadImage(@RequestParam("file") MultipartFile imageFile){
		return ""+imageFile.getSize();
		
	}
	
	

	@RequestMapping( value = "/user" , method = RequestMethod.GET )
	@ResponseBody
	public UserInfo getUserInfo( HttpServletRequest request ) throws CustomException {

		logger.info( "/user/log.GET" );

		// HttpServletRequest.getAttribute Returns null if Values are not found
		if (request.getAttribute("FilterException") != null)
			throw customExceptionFactory.createCustomException( (CustomExceptionValue) request.getAttribute("FilterException") );

		return restApiService.getUserInfo( request.getHeader(USERID_HEADER_NAME) );

	}
	
	/**
	 * RequestParam has every information to use on POST, DELETE Methods.
	 * Id, Revision is Needed in the Body
	 * @author susu
	 * Date Oct 30, 2014 3:23:20 PM
	 * @param requestParam
	 */
	@RequestMapping( value = "/user/log" , method = RequestMethod.DELETE )
	@ResponseBody
	public Map<String, Object> deleteUserLog( HttpServletRequest request, @RequestBody RawData rawData ) throws Exception {

		logger.info( "/user/log.DELETE" );

		// HttpServletRequest.getAttribute Returns null if Values are not found
		if (request.getAttribute("FilterException") != null)
			throw customExceptionFactory.createCustomException( (CustomExceptionValue) request.getAttribute("FilterException") );
		
		if( rawData.getId() == null || rawData.getRevision() == null )
			throw customExceptionFactory.createCustomException( CustomExceptionValue.Parameter_Insufficient_Exception );
		
		// Set Request Parameter from Request Header
		// These Attributes are always available because of the filter. 
		rawData.setUserId( request.getHeader(USERID_HEADER_NAME) );
		
		return restApiService.deleteUserData( rawData );
		
	}
	
	/**
	 * Updates User Log data. Id, Revision is Needed in the Body
	 * @author susu
	 * Date Oct 30, 2014 4:21:43 PM
	 * @param rawData
	 */
	@RequestMapping(value = "/user/log", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateUserLog(
			HttpServletRequest request,
			@RequestBody(required = true) RawData rawData) throws Exception {
		
		logger.info("/user/log.UPDATE");

		// HttpServletRequest.getAttribute Returns null if Values are not found
		if (request.getAttribute("FilterException") != null)
			throw customExceptionFactory.createCustomException((CustomExceptionValue) request.getAttribute("FilterException"));
		
		if( rawData.getId() == null || rawData.getRevision() == null )
			throw customExceptionFactory.createCustomException( CustomExceptionValue.Parameter_Insufficient_Exception );
		
		// Set Request Parameter from Request Header
		// These Attributes are always available because of the filter.
		rawData.setUserId( request.getHeader(USERID_HEADER_NAME) );
		rawData.setAppId( request.getHeader(APPID_HEADER_NAME) );

		return restApiService.updateUserData(rawData);
		
	}

	/**
	 * Author : RichardJ
	 * Date : Oct 17, 2014 1:55:06 PM
	 * Description : 클라이언트로부터 데이터 생성 요청을 처리하는 메소드입니다.
	 * Issue : 데이터 생성 성공시 클라이언트에 보내줘야할 정보는 ?
	 */
	@RequestMapping(value = "/user/app/log", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> postUserLog(
			HttpServletRequest request,
			@RequestBody( required = true ) RawData rawData ) throws Exception{

		logger.info("/user/app/log.POST");

		// HttpServletRequest.getAttribute Returns null if Values are not found
		if (request.getAttribute("FilterException") != null)
			throw customExceptionFactory.createCustomException((CustomExceptionValue) request.getAttribute("FilterException"));

		// These Attributes are always available because of the filter. 
		rawData.setUserId( request.getHeader(USERID_HEADER_NAME) );
		rawData.setAppId( request.getHeader(APPID_HEADER_NAME) );

		// Delegate Request to RestApiService Object
		return restApiService.postUserData( rawData );
	}

	/**
	 * Author : RichardJ
	 * Date : Oct 20, 2014 18:55:06
	 * Description : 해당 엑세스키를 사용하는 사용자의 전체 데이터 조회입니다.
	 */
	@RequestMapping(value = "/user/logs", method = RequestMethod.GET)
	@ResponseBody
	public ResponseDataWrapper getUserLog( HttpServletRequest request,
			@RequestParam(value = "limit", required = false, defaultValue="1000") String limit,
			@RequestParam(value = "startKey", required = false, defaultValue=ISO8601.FAR_FAR_AWAY) String startKey,
			@RequestParam(value = "endKey", required = false, defaultValue=ISO8601.LONG_LONG_AGO) String endKey) throws Exception{

		// HttpServletRequest.getAttribute Returns null if Values are not found
		if (request.getAttribute("FilterException") != null)
			throw customExceptionFactory.createCustomException((CustomExceptionValue) request.getAttribute("FilterException"));

		// Set Request Parameter from @RequestParam
		RequestParameter param = new RequestParameter();
		param.setUserHashId( request.getHeader(USERID_HEADER_NAME) );
		param.setAppId( request.getHeader(APPID_HEADER_NAME) );
		param.setStartKey(startKey);
		param.setEndKey(endKey);
		param.setLimit(limit);
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

	public ResponseDataWrapper getUserLogFromAppId( HttpServletRequest request,
			@RequestParam(value = "limit", required = false, defaultValue="1000") String limit,
			@RequestParam(value = "startKey", required = false, defaultValue=ISO8601.FAR_FAR_AWAY) String startKey,
			@RequestParam(value = "endKey", required = false, defaultValue=ISO8601.LONG_LONG_AGO) String endKey) throws Exception {

		// HttpServletRequest.getAttribute Returns null if Values are not found
		if (request.getAttribute("FilterException") != null)
			throw customExceptionFactory.createCustomException((CustomExceptionValue) request.getAttribute("FilterException"));

		// Set Request Parameter from @RequestParam
		RequestParameter param = new RequestParameter();

		param.setStartKey(startKey);
		param.setUserHashId( request.getHeader(USERID_HEADER_NAME) );
		param.setEndKey(endKey);
		param.setLimit(limit);
		param.setAppId( request.getHeader(APPID_HEADER_NAME) );

		// Delegate Request to RestApiService Object
		return restApiService.getUserDataFromAppId(param);
	}

}

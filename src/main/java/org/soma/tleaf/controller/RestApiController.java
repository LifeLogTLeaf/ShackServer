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
import org.soma.tleaf.domain.RequestParameter;
import org.soma.tleaf.domain.ResponseDataWrapper;
import org.soma.tleaf.domain.UserInfo;
import org.soma.tleaf.exception.CustomException;
import org.soma.tleaf.exception.CustomExceptionFactory;
import org.soma.tleaf.exception.CustomExceptionValue;
import org.soma.tleaf.service.RestApiService;
import org.soma.tleaf.util.ISO8601;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

/**
 * Handles API Calls Directly Related to User Data, And So Needs Access Headers(OauthFilter)
 * Created with Eclipse IDE
 * Author : RichardJ
 * Date : Oct 17, 2014 1:36:07 PM
 */
@RequestMapping(value = "api/*")
@Controller
@Api(value = "rest", description = "REST API that requires Authorization")
public class RestApiController {
	static Logger logger = LoggerFactory.getLogger(RestApiController.class);

	@Inject
	private RestApiService restApiService;

	@Inject
	private CustomExceptionFactory customExceptionFactory;

	private final String USERID_HEADER_NAME = "x-tleaf-user-id";
	private final String APPID_HEADER_NAME = "x-tleaf-application-id"; // Same as other company's API Key
	
	/** Fetches UserInfo Data
	 * @author susu
	 * Date Nov 1, 2014 2:27:43 PM
	 * @return UserInfo JSON String
	 * @throws CustomException if No User is matched
	 */
	@RequestMapping( value = "/user" , method = RequestMethod.GET )
	@ResponseBody
	@ApiOperation( httpMethod = "GET" , value = "Gets UserInfo" )
	public UserInfo getUserInfo( HttpServletRequest request ) throws CustomException {

		logger.info( "/user/log.GET" );

		// HttpServletRequest.getAttribute Returns null if Values are not found
		if (request.getAttribute("FilterException") != null)
			throw customExceptionFactory.createCustomException( (CustomExceptionValue) request.getAttribute("FilterException") );

		return restApiService.getUserInfo( request.getHeader(USERID_HEADER_NAME) );
	}
	
	/**
	 * Put an attachment info the Specific Document
	 * @author susu
	 * Date Nov 7, 2014 2:11:50 AM
	 * @param request
	 * @param rawData
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping( value = "/user/resource" , method = RequestMethod.POST )
	@ApiOperation( httpMethod = "POST" , value = "Creates Attachment for Specified _id" )
	public ResponseEntity<Map<String, Object>> postAttachment ( HttpServletRequest request, @RequestBody RawData rawData ) throws Exception {
		
		logger.info( "/user/resource.POST" );

		// HttpServletRequest.getAttribute Returns null if Values are not found
		if (request.getAttribute("FilterException") != null)
			throw customExceptionFactory.createCustomException( (CustomExceptionValue) request.getAttribute("FilterException") );
		
		// Basically this is an Update process so _id, _rev is needed.
		if( rawData.getId() == null || rawData.getRevision() == null )
			throw customExceptionFactory.createCustomException( CustomExceptionValue.Parameter_Insufficient_Exception );
		
		// Set Request Parameter from Request Header
		// These Attributes are always available because of the filter.
		rawData.setUserId( request.getHeader(USERID_HEADER_NAME) );
		rawData.setAppId( request.getHeader(APPID_HEADER_NAME) );

		return restApiService.postAttachment(rawData);
	}
	
	
	/**
	 * Finds the Document with Specific Id.
	 * @author susu
	 * Date Nov 1, 2014 2:45:18 PM
	 * @param rawDataId String "_id" to find the Document
	 * @return RawData Json String
	 * @throws CustomException NoSuchDocumentException if Document isn't Found
	 */
	@RequestMapping( value = "/user/log", method = RequestMethod.GET )
	@ResponseBody
	@ApiOperation( httpMethod = "GET" , value = "Fetches Document With Specific Id" )
	public RawData getRawData( HttpServletRequest request, @RequestParam String rawDataId ) throws CustomException {
		logger.info( "/user/log.GET" );
		// HttpServletRequest.getAttribute Returns null if Values are not found
		if (request.getAttribute("FilterException") != null)
			throw customExceptionFactory.createCustomException( (CustomExceptionValue) request.getAttribute("FilterException") );
		
		return restApiService.getRawData( rawDataId, request.getHeader(USERID_HEADER_NAME) );
	}
	
	/**
	 * RequestParam has every information to use on POST, DELETE Methods.
	 * @author susu
	 * Date Oct 30, 2014 3:23:20 PM
	 * @param RawData Id, Revision is Needed in the Body
	 */
	@RequestMapping( value = "/user/log" , method = RequestMethod.DELETE )
	@ResponseBody
	@ApiOperation( httpMethod = "DELETE" , value = "Deletes Specific User Log Data ( With _id, _rev )" )
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
	 * @param RawData Id, Revision is Needed in the Body
	 */
	@RequestMapping(value = "/user/log", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation( httpMethod = "POST" , value = "Updates User Log Data ( With _id, _rev )" )
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
	@ApiOperation( httpMethod = "POST" , value = "Creates User Log Data ( With _id , data )" )
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
	@ApiOperation( httpMethod = "GET" , value = "Fetches User Log Data ( Within the Given Time )" )
	public ResponseDataWrapper getUserLog( HttpServletRequest request,
			@RequestParam(value = "limit", required = false, defaultValue="1000") String limit,
			@RequestParam(value = "startKey", required = false, defaultValue=ISO8601.FAR_FAR_AWAY) String startKey,
			@RequestParam(value = "endKey", required = false, defaultValue=ISO8601.LONG_LONG_AGO) String endKey, 
			@RequestParam(value = "descend", required = false , defaultValue="true") boolean descend) throws Exception{

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
		param.setDescend(descend);
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
	@ApiOperation( httpMethod = "GET" , value = "Fetches User Log Data from AppId ( Within the Given Time AND )" )
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

package org.soma.tleaf.controller;

import javax.inject.Inject;

import org.soma.tleaf.service.RestApiService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

/**
 * Controls Resources such as Image. as Browser requests 
 * @author susu
 * Date : Nov 7, 2014 12:22:10 AM
 */

@Controller
@Api(value = "resource", description = "Resource API")
public class ResourceController {

	@Inject
	RestApiService restApiService;
	
	/**
	 * Returns the Resource. it is keeped inside the html for just resource url. Specially, this doesn't need Auth
	 * @author susu
	 * Date Nov 7, 2014 12:27:41 AM
	 * @param userId User HashId
	 * @param docId Id of the Documnet where Resource is stored
	 * @param attachmentId Resource's Id inside the document
	 * @return Need to Decide how are we going to return image resources
	 * @throws Exception 
	 */
	@RequestMapping( value = "user/resource" , method = RequestMethod.GET )
	@ApiOperation( httpMethod = "GET" , value = "Gets User's Media resources" )
	public ResponseEntity<byte[]> getResource ( @RequestParam String userId, @RequestParam String docId, @RequestParam String attachmentId ) throws Exception {
		return restApiService.getUserResource( userId, docId, attachmentId );
	}
	
}

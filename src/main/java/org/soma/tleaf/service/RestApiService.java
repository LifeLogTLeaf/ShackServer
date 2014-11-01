/**
 * 
 */
package org.soma.tleaf.service;

import java.util.Map;

import org.soma.tleaf.domain.RawData;
import org.soma.tleaf.domain.RequestParameter;
import org.soma.tleaf.domain.ResponseDataWrapper;
import org.soma.tleaf.domain.UserInfo;
import org.soma.tleaf.exception.CustomException;

/**
 * Created with Eclipse IDE
 * Author : RichardJ
 * Date : Oct 17, 2014 6:12:49 PM
 * Description :
 */
public interface RestApiService {
	public Map<String, Object> postUserData( RawData rawData ) throws Exception;
	public Map<String, Object> deleteUserData( RawData rawData ) throws Exception;
	public Map<String, Object> updateUserData( RawData rawData ) throws Exception;
	public ResponseDataWrapper getUserData(RequestParameter param) throws Exception;
	public ResponseDataWrapper getUserDataFromAppId(RequestParameter param) throws Exception;
	public UserInfo getUserInfo( String userId ) throws CustomException;
	public RawData getRawData( String rawDataId, String userId ) throws CustomException;
	
}

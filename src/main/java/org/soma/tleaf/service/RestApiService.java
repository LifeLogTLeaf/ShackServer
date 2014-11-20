/**
 * 
 */
package org.soma.tleaf.service;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.soma.tleaf.domain.RawData;
import org.soma.tleaf.domain.RequestParameter;
import org.soma.tleaf.domain.ResponseDataWrapper;
import org.soma.tleaf.domain.UserInfo;
import org.soma.tleaf.exception.CustomException;
import org.soma.tleaf.exception.DatabaseConnectionException;
import org.springframework.http.ResponseEntity;

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
	public UserInfo updateUserInfo(RawData rawData) throws CustomException;
	public RawData getRawData( String rawDataId, String userId ) throws CustomException;
	public ResponseEntity<byte[]> getAttachment( String userId, String docId, String attachmentId ) throws Exception;
	public ResponseEntity<Map<String,Object>> postAttachment( RawData[] rawData, List< InputStream > fileList ) throws Exception;
	public ResponseEntity<Map<String,Object>> deleteAttachment( RawData rawData ) throws Exception;
	public ResponseEntity<List<Map<String,Object>>> appCount ( RequestParameter param ) throws DatabaseConnectionException;
	public ResponseEntity<List<Map<String, Object>>> wordCount ( RequestParameter param ) throws DatabaseConnectionException, Exception;
	public ResponseEntity<List<Map<String, Object>>> tagCount ( RequestParameter param ) throws Exception;
	public ResponseEntity<List<RawData>> getUserDataFromDate ( RequestParameter param ) throws Exception;
	public ResponseEntity<List<Map<String, Object>>> getFacebookInfo ( RequestParameter param ) throws Exception;
}

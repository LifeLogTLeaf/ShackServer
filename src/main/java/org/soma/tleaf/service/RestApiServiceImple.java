/**
 * 
 */
package org.soma.tleaf.service;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.ektorp.AttachmentInputStream;
import org.ektorp.DocumentNotFoundException;
import org.ektorp.UpdateConflictException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soma.tleaf.dao.RestApiDao;
import org.soma.tleaf.domain.RawData;
import org.soma.tleaf.domain.RequestParameter;
import org.soma.tleaf.domain.ResponseDataWrapper;
import org.soma.tleaf.domain.UserInfo;
import org.soma.tleaf.exception.CustomException;
import org.soma.tleaf.exception.DatabaseConnectionException;
import org.soma.tleaf.util.ISO8601;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Created with Eclipse IDE
 * Author : RichardJ
 * Date : Oct 17, 2014 6:13:35 PM
 * Description : 컨트롤러로부터 받은 데이터를 비지니스로직에 맡게 처리한후 디비접근 객체에 넘기는 클래스입니다.
 */
public class RestApiServiceImple implements RestApiService {
	private Logger logger = LoggerFactory.getLogger(RestApiServiceImple.class);

	@Inject
	private RestApiDao restApiDao;

	/**
	 * Author : RichardJ
	 * Date : Oct 21, 2014 08:55:06
	 * Description : 클라이언트로부터 받은 데이터를 디비에 저장시키기전 디비 데이터 형식에 맞게 데이터를 가공합니다.
	 * Issue :
	 */
	@Override
	public Map<String, Object> postUserData( RawData rawData ) throws Exception {
		// Create Response Result
		Map<String, Object> result = new HashMap<String, Object>();
		if( rawData.getTime() == null ) rawData.setTime( ISO8601.now() );
		// Request Create Data and Set Response result
		restApiDao.postData(result, rawData);
		return result;
	}

	/**
	 * Author : RichardJ
	 * Date : Oct 21, 2014 08:55:06
	 * Description : 해당 사용자의 전체 데이터조회요청처리 로직입니다.
	 * Issue :
	 * @throws Exception 
	 */
	@Override
	public ResponseDataWrapper getUserData(RequestParameter param) throws Exception {
		// check Descend 
		if(!param.isDescend()){
			String temp = param.getStartKey();
			param.setStartKey(param.getEndKey());
			param.setEndKey(temp);
		}
		
		// Create Response Result
		ResponseDataWrapper result = new ResponseDataWrapper();
		
		// Set Date to Response
		result.setLogs(restApiDao.getAllData(param));
		return result;
	}
	
	/**
	 * Author : RichardJ
	 * Date : Oct 21, 2014 08:55:06
	 * Description : 해당 사용자의 해당 앱 아이디 전체 데이터조회요청처리 로직입니다.
	 * Issue :
	 * @throws Exception 
	 */
	@Override
	public ResponseDataWrapper getUserDataFromAppId(RequestParameter param) throws Exception {
		// check Descend 
		if(!param.isDescend()){
			String temp = param.getStartKey();
			param.setStartKey(param.getEndKey());
			param.setEndKey(temp);
		}
		
		// Create Response Result
		ResponseDataWrapper result = new ResponseDataWrapper();
		// Set Date to Response
		result.setLogs(restApiDao.getAllDataFromAppId(param));
		return result;
	}
	
	@Override
	public ResponseEntity<List<RawData>> getUserDataFromDate(RequestParameter param) throws Exception {
		return new ResponseEntity<List<RawData>>( restApiDao.getDataByDate(param), HttpStatus.OK );
	}

	/**
	 * Deletes User Log and Returns the _rev of the Deleted Doc.
	 * @author susu
	 * Date Oct 30, 2014
	 * @param rawData
	 * @throws Exception
	 */
	@Override
	public Map<String, Object> deleteUserData( RawData rawData )
			throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		restApiDao.deleteData(result, rawData);
		
		return result;
	}

	/**
	 * Does not return any Important Information, But Tells if it Succeded
	 * @author susu
	 * Date Oct 30, 2014
	 * @param rawData
	 * @return "update":"success"
	 * @throws Exception
	 */
	@Override
	public Map<String, Object> updateUserData(RawData rawData) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		restApiDao.updateData(result, rawData);

		return result;
	}

	/**
	 * Returns the UserInfo Class from DAO. Need to Decide on Profile Image ( URL or Data ).
	 * @author susu
	 * Date Nov 1, 2014
	 * @param userId
	 * @return UserInfo
	 * @throws CustomException
	 */
	@Override
	public UserInfo getUserInfo(String userId) throws CustomException {
		return restApiDao.getUserInfo( userId );
	}
	
	/** 
	 * @author : RichardJ
	 * Date    : Nov 11, 2014 6:21:11 PM
	 * @param RawData
	 * @return
	 * @throws CustomException
	 */
	@Override
	public UserInfo updateUserInfo(RawData rawData) throws CustomException {
		return restApiDao.updateUserInfo( rawData );
	}

	/**
	 * Fetches RawData with Document Id
	 * @author susu
	 * Date Nov 1, 2014
	 * @param rawDataId
	 * @param userId
	 * @return RawData Json String
	 * @throws CustomException
	 */
	@Override
	public RawData getRawData(String rawDataId, String userId)
			throws CustomException {
		return restApiDao.getRawData(rawDataId, userId);
	}
	
	/**
	 * Fetches Byte array Resource from user database Differs Status codes by Exception
	 * @author susu
	 * Date Nov 7, 2014
	 * @param userId
	 * @param docId
	 * @param attachmentId
	 * @return
	 * @throws Exception 
	 */
	@Override
	public ResponseEntity<byte[]> getAttachment(String userId, String docId,
			String attachmentId ) throws Exception {
		
		try {
			AttachmentInputStream attachmentStream = restApiDao.getAttachment(userId, docId, attachmentId);
			
			// Create a byte array output stream.
	        ByteArrayOutputStream bao = new ByteArrayOutputStream();
			
			byte[] attachmentBytes = new byte[ (int)attachmentStream.getContentLength() ];
			attachmentStream.read( attachmentBytes, 0, attachmentBytes.length + 100 );
			attachmentStream.close();
			
			HttpHeaders header = new HttpHeaders();
			// Set Custom Headers
			header.set("Content-Type", attachmentStream.getContentType() );
			header.set("Content-Length", String.valueOf( attachmentStream.getContentLength() ) );
			
//			return attachmentBytes;
			return new ResponseEntity<byte[]>( attachmentBytes, header, HttpStatus.FOUND );
		
		} catch (DocumentNotFoundException e) {
			e.printStackTrace();
			
//			return null;
			return new ResponseEntity<byte[]>( HttpStatus.NOT_FOUND );
		
		} catch (Exception e) {
			e.printStackTrace();
		
			throw e;
		
		}
	}
	
	/**
	 * Only uses RawData's id, rev, base64String to update a document and put an attachment
	 * @author susu
	 * Date Nov 7, 2014
	 * @param rawData
	 * @return
	 * @throws Exception
	 */
	@Override
	public ResponseEntity<Map<String, Object>> postAttachment( RawData[] rawData, List< InputStream > streamList )
			throws Exception {
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		// Check if the Client tried to Update a Document of other Applications
		String updatingDoc = getRawData( rawData[0].getId() , rawData[0].getUserId() ).getAppId();
		if ( !updatingDoc.matches(rawData[0].getAppId()) ) {
			result.put( "forbidden", "You can't update a Documnet of Other Application" );
			logger.info( rawData[0].getAppId() + " Tried to Update " + updatingDoc );
			
			// Or maybe Change it to CustomException??
			return new ResponseEntity<Map<String,Object>>( result, HttpStatus.FORBIDDEN );
		}
		
		// List of attachment Post Results
		List< Map<String,String> > bulkResult = new ArrayList< Map<String,String> >();
		
		int c = 0;
		String changedRev = rawData[0].getRevision();
		for ( InputStream i : streamList )
		{
			Map<String, String> tmp = new HashMap<String,String>();
			tmp.put( "filename" , rawData[c].getAttachmentId() );
			
			try {

				// Changes the Rivision of the Next attachment
				rawData[c].setRevision(changedRev);
				changedRev = restApiDao.postAttachment(rawData[c], i);
				tmp.put("_rev", changedRev );
			
			} catch ( DatabaseConnectionException e ) {
				e.printStackTrace();
				tmp.put("failed", "DatabaseConnectionException Occured" );
			} catch ( UpdateConflictException e ) {
				e.printStackTrace();
				tmp.put( "failed", "There was an Update Confilct" );
			} catch ( Exception e ) {
				e.printStackTrace();
			}
			
			bulkResult.add( tmp );
			c++;

		}
		
		result.put("File Upload Results", bulkResult);
		
		return new ResponseEntity<Map<String,Object>>( result, HttpStatus.CREATED );
	}

	/**
	 * Deletes the specific Attachment. If appId doesn't match, FORBID it.
	 * @author susu
	 * Date Nov 14, 2014
	 * @param rawData
	 * @return
	 * @throws Exception
	 */
	@Override
	public ResponseEntity<Map<String, Object>> deleteAttachment( RawData rawData ) throws Exception {
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		String updatingDoc = getRawData( rawData.getId() , rawData.getUserId() ).getAppId();
		logger.info( rawData.getAppId() + " Tried to Update " + updatingDoc );
		
		if ( !updatingDoc.matches(rawData.getAppId()) ) {
			
			result.put( "forbidden", "You can't update a Documnet of Other Application" );
			// Or maybe Change it to CustomException??
			return new ResponseEntity<Map<String,Object>>( result, HttpStatus.FORBIDDEN );
		}
		
		// DatabaseConnectionException, UpdateConfilctException
		result.put("Attachment Delete", restApiDao.deleteAttachment(rawData) );
		
		return new ResponseEntity<Map<String,Object>>( result, HttpStatus.OK );
	}

	/**
	 * Returns how much log data an application has made.
	 * @author susu
	 * Date Nov 14, 2014
	 * @param param
	 * @return
	 * @throws DatabaseConnectionException
	 */
	@Override
	public ResponseEntity<List<Map<String, Object>>> appCount(RequestParameter param) throws DatabaseConnectionException {
		return new ResponseEntity<List<Map<String,Object>>>( restApiDao.appCount(param), HttpStatus.OK );
	}

	
	@Override
	public ResponseEntity<List<Map<String,Object>>> wordCount(RequestParameter param)
			throws Exception {
		try {
			return new ResponseEntity<List<Map<String,Object>>>( restApiDao.wordCount(param) , HttpStatus.OK );
		} catch ( DocumentNotFoundException e) {
			e.printStackTrace();
			return new ResponseEntity<List<Map<String,Object>>>( HttpStatus.NOT_FOUND );
		}
	}

	@Override
	public ResponseEntity<List<Map<String, Object>>> tagCount(
			RequestParameter param) throws Exception {
		try {
			return new ResponseEntity<List<Map<String,Object>>>( restApiDao.tagCount(param) , HttpStatus.OK );
		} catch ( DocumentNotFoundException e) {
			e.printStackTrace();
			return new ResponseEntity<List<Map<String,Object>>>( HttpStatus.NOT_FOUND );
		}
	}

	@Override
	public ResponseEntity<List<Map<String, Object>>> getFacebookInfo(
			RequestParameter param) throws Exception {
		try{
			return new ResponseEntity<List<Map<String, Object>>>( restApiDao.facebookInfo(param) , HttpStatus.OK );
		} catch ( DocumentNotFoundException e ) {
			return new ResponseEntity<List<Map<String, Object>>>( HttpStatus.NOT_FOUND );
		}
	}

}

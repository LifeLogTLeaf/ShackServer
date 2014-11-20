/**
 * 
 */
package org.soma.tleaf.dao;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.ektorp.AttachmentInputStream;
import org.ektorp.ComplexKey;
import org.ektorp.CouchDbConnector;
import org.ektorp.DocumentNotFoundException;
import org.ektorp.ViewQuery;
import org.ektorp.ViewResult;
import org.ektorp.ViewResult.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soma.tleaf.couchdb.CouchDbConn;
import org.soma.tleaf.domain.RawData;
import org.soma.tleaf.domain.RequestParameter;
import org.soma.tleaf.domain.UserInfo;
import org.soma.tleaf.exception.CustomException;
import org.soma.tleaf.exception.CustomExceptionFactory;
import org.soma.tleaf.exception.CustomExceptionValue;
import org.soma.tleaf.exception.DatabaseConnectionException;

/**
 * Created with Eclipse IDE
 * Author : RichardJ
 * Date : Oct 17, 2014 6:14:35 PM
 * Description :
 */
public class RestApiDaoImple implements RestApiDao {
	private Logger logger = LoggerFactory.getLogger(RestApiDaoImple.class);
	private static String USER_DB_NAME = "tleaf_users";

	@Inject
	private CouchDbConn connector;
	
	@Inject
	private CustomExceptionFactory customExceptionFactory;
	
	/**
	 * Author : RichardJ
	 * Date : Oct 22, 2014 10:28:35 PM
	 * Description : 클라이언트로부터 받은 데이터를 디비에 저장합니다.
	 */
	@Override
	public void postData(Map<String,Object> result, RawData rawData) throws Exception {
		CouchDbConnector db = connector.getCouchDbConnetor("user_" + rawData.getUserId());
		db.create(rawData);
		result.put("_id", rawData.getId() );
		result.put("_rev", rawData.getRevision() );
	}

	/**
	 * Author : RichardJ
	 * Date : Oct 22, 2014 10:28:35 PM
	 * Description :
	 */
	@Override
	public RawData getData(RequestParameter param) {
		RawData data = null;
		return data;
	}

	/**
	 * Author : RichardJ
	 * Date : Oct 23, 2014 9:48:52 AM
	 * Description : 해당 사용자 데이터베이스에서 전체 데이터를 읽어옵니다.
	 */
	@Override
	public List<RawData> getAllData(RequestParameter param) throws Exception {
		CouchDbConnector db = connector.getCouchDbConnetor("user_" + param.getUserHashId());
		ViewQuery query = new ViewQuery()
				.designDocId("_design/shack")
				.viewName("time")
				.startKey(param.getStartKey())
				.endKey(param.getEndKey())
				.limit(Integer.valueOf(param.getLimit()))
				.descending(param.isDescend());

		List<RawData> rawDatas;
		try {
			rawDatas = db.queryView(query, RawData.class);
		} catch (DocumentNotFoundException e) {
			// if there were no documents to the specific query
			e.printStackTrace();
			throw e;
		}

		// For test print Code
		logger.info("" + rawDatas.size());
		for (RawData rd : rawDatas) {
			logger.info(rd.getTime());
		}
		return rawDatas;
	}

	/**
	 * Author : RichardJ
	 * Date : Oct 23, 2014 9:48:52 AM
	 * Description : 해당 사용자 데이터베이스에서 해당 앱의 아이디의 데이터만 읽어옵니다.
	 */
	@Override
	public List<RawData> getAllDataFromAppId(RequestParameter param) throws Exception {

		logger.info(param.getAppId() + " application query");
		logger.info("startKey = " + "[" + param.getAppId() + "," + param.getStartKey() + "]");
		logger.info("endKey = " + "[" + param.getAppId() + "," + param.getEndKey() + "]");

		CouchDbConnector db = connector.getCouchDbConnetor("user_" + param.getUserHashId());

		ComplexKey startKey = ComplexKey.of(param.getAppId(), param.getStartKey());
		ComplexKey endKey = ComplexKey.of(param.getAppId(), param.getEndKey());

		ViewQuery query = new ViewQuery().designDocId("_design/shack").viewName("app").startKey(startKey).endKey(endKey).descending(true);

		List<RawData> rawDatas;
		try {
			rawDatas = db.queryView(query, RawData.class);
		} catch (DocumentNotFoundException e) {
			// if there were no documents to the specific query
			e.printStackTrace();
			throw e;
		}

		// For test print Code
		logger.info("" + rawDatas.size());
		for (RawData rd : rawDatas) {
			logger.info(rd.getTime());
		}
				
		return rawDatas;
	}

	/**
	 * Deletes the Specific log data.
	 * 2014.10.30
	 * @author susu
	 */
	@Override
	public void deleteData( Map<String,Object> result, RawData rawData ) throws Exception {
		
		logger.info( rawData.getUserId() );
		
		// DatabaseConnectionException Can Occur
		CouchDbConnector couchDbConnector_user = connector.getCouchDbConnetor("user_" + rawData.getUserId());
		
		// UpdateConfilct Exception??
		result.put("_rev", couchDbConnector_user.delete( rawData.getId(), rawData.getRevision() ) );
		
		logger.info( "Deleted User Log. _rev = " + (String)result.get("_rev") );

	}

	/**
	 * Updates the Specific Log data.
	 * @author susu
	 * Date Oct 30, 2014
	 * @param rawData
	 */
	@Override
	public void updateData(Map<String, Object> result, RawData rawData)
			throws Exception {
		
		logger.info( rawData.getUserId() );
		logger.info( rawData.getAppId() );
		
		// DatabaseConnectionException Can Occur
		CouchDbConnector couchDbConnector_user = connector.getCouchDbConnetor("user_" + rawData.getUserId());
		
		// UpdateConflictException
		couchDbConnector_user.update(rawData);
		
		result.put("update", "success");
		logger.info( "User Log Update Complete" );
	}

	/**
	 * Fetches UserInfo Data
	 * @author susu
	 * Date Oct 31, 2014
	 * @param userId
	 * @throws CustomException 
	 */
	@Override
	public UserInfo getUserInfo(String userId) throws CustomException {
		
		logger.info( userId + "get UserInfo" );
		
		// DatabaseConnectionException Can Occur
		CouchDbConnector couchDbConnector_user = connector.getCouchDbConnetor( USER_DB_NAME );
		
		UserInfo userInfo = couchDbConnector_user.get( UserInfo.class, userId );
		if( userInfo == null )
			throw customExceptionFactory.createCustomException( CustomExceptionValue.No_Such_User_Exception );
		
		return userInfo;
	}
	
	/**@author : RichardJ
	 * Date    : Nov 11, 2014 6:22:16 PM
	 * @param RawData
	 * @return
	 * @throws CustomException
	 */
	public UserInfo updateUserInfo(RawData rawData) throws CustomException {
		CouchDbConnector couchDbConnector_user = connector.getCouchDbConnetor( USER_DB_NAME );
		String userId = rawData.getUserId();
		String appId = rawData.getAppId();
		
		// get user information from CouchDB
		UserInfo userInfo = couchDbConnector_user.get(UserInfo.class, userId);
		// get services data
		ArrayList<Map> services = userInfo.getServices();
				
		int index= -1;
		
		// if services doesn't exist, make new services array
		if(services == null){
			services = new ArrayList<Map>();
		}else {
			for(int i=0; i<services.size(); i++){
				if(services.get(i).get("appId").equals(appId)) index = i;
			}
		}
				
		// add app service data		
		if (index > -1){
			services.set(index, rawData.getData());
		} else {
			services.add(rawData.getData());
		}
				
		userInfo.setServices(services);
		couchDbConnector_user.update(userInfo);
		return userInfo;
	}

	/**
	 * Fetches the Specific Raw Data by _id
	 * @author susu
	 * Date Nov 1, 2014
	 * @param rawDataId
	 * @param userId
	 * @return RawData Found in the user Database
	 * @throws CustomException if no such Documnet was Found
	 */
	@Override
	public RawData getRawData( String rawDataId, String userId ) throws CustomException {
		
		logger.info( "getting RawData by Id " + rawDataId );
		
		// DatabaseConnectionException Can Occur
		CouchDbConnector couchDbConnector_user = connector.getCouchDbConnetor( "user_" + userId );
		
		RawData rawData = couchDbConnector_user.get( RawData.class, rawDataId );
		
		if ( rawData == null )
			throw customExceptionFactory.createCustomException( CustomExceptionValue.No_Such_Document_Exception );
		
		return rawData;
	}

	/**
	 * Returns User's resource in byte Array
	 * @author susu
	 * Date Nov 7, 2014
	 * @param userId
	 * @param docId
	 * @param attachmentId
	 * @return
	 * @throws Exception IOException or DatabaseConnectionException
	 */
	@Override
	public AttachmentInputStream getAttachment(String userId, String docId,
			String attachmentId) throws Exception {
		
		logger.info( "getting User Resource " + userId );
		
		// DatabaseConnectionException Can Occur
		CouchDbConnector couchDbConnector_user = connector.getCouchDbConnetor( "user_" + userId );
		
		AttachmentInputStream attachmentStream = couchDbConnector_user.getAttachment( docId, attachmentId );
		return attachmentStream;
		
	}

	/**
	 * Puts an attachment into the document by updating
	 * @author susu
	 * Date Nov 7, 2014
	 * @param result
	 * @param rawData
	 * @throws Exception
	 * @returns String the Document's Changed Revision
	 */
	@Override
	public String postAttachment( RawData rawData, InputStream inputStream )
			throws Exception {

		logger.info(rawData.getAppId() + " posting Attachment for "
				+ rawData.getUserId());

		// DatabaseConnectionException Can Occur
		CouchDbConnector couchDbConnector_user = connector
				.getCouchDbConnetor("user_" + rawData.getUserId());

		// UpdateConflictException Can Occur
		return
				couchDbConnector_user.createAttachment(
						
						rawData.getId(), 
						rawData.getRevision(),
						
						new AttachmentInputStream(
								rawData.getAttachmentId(), 
								inputStream,
								rawData.getAttachmentType()
						)
						
				);

	}

	/**
	 * Deletes the Specific Attachment. id, rev is both needed
	 * @author susu
	 * Date Nov 13, 2014
	 * @param rawData
	 * @return
	 * @throws DatabaseConnectionException
	 */
	@Override
	public String deleteAttachment( RawData rawData ) throws DatabaseConnectionException {
		
		logger.info(rawData.getAppId() + " deleting Attachment for "
				+ rawData.getUserId());

		// DatabaseConnectionException Can Occur
		CouchDbConnector couchDbConnector_user = connector
				.getCouchDbConnetor("user_" + rawData.getUserId());
		
		// UpdateConflictException Can Occur
		return 
				couchDbConnector_user.deleteAttachment(
						rawData.getId(), 
						rawData.getRevision(), 
						rawData.getAttachmentId() 
						);
	}

	/**
	 * Returns how much logs are stored in DB ( by applications )
	 * @author susu
	 * Date Nov 14, 2014
	 * @param param
	 * @return
	 * @throws DatabaseConnectionException
	 */
	@Override
	public Map<String, Object> appCount(RequestParameter param) throws DatabaseConnectionException {
		
		logger.info( param.getUserHashId() + " Statistic Query" );
		
		CouchDbConnector db = connector.getCouchDbConnetor("user_" + param.getUserHashId());
		
		ViewQuery query = new ViewQuery().designDocId("_design/shack").viewName("appcount").group(true);
		
		Map<String,Object> resultMap = new HashMap<String,Object>();
		
		try {
			List<Row> rows = db.queryView(query).getRows();
			
			for ( Row i : rows ) {
				resultMap.put( i.getKey(), i.getValue() );
			}
			
			return resultMap;
			
		} catch (DocumentNotFoundException e) {
			// if there were no documents to the specific query
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public List<RawData> getDataByDate(RequestParameter param) throws Exception {
		
		logger.info( param.getUserHashId() + " Data by Date" );
		
		CouchDbConnector db = connector.getCouchDbConnetor("user_" + param.getUserHashId());
		
		String[] date = param.getDate();
		
		String[] startDate = new String[7]; startDate[0] = param.getAppId();
		String[] endDate = new String[7]; endDate[0] = param.getAppId();
		
		int i;
		for ( i=0; i< date.length; i++ ) {
			startDate[i+1] = date[i];
			endDate[i+1] = date[i];
		}
		
		for ( i=date.length; i<6; i++ ) {
			startDate[i+1] = "00";
			endDate[i+1] = "99";
		}
		
		ComplexKey startKey = ComplexKey.of( startDate[0],startDate[1],startDate[2],startDate[3],startDate[4],startDate[5],startDate[6] );
		ComplexKey endKey = ComplexKey.of( endDate[0],endDate[1],endDate[2],endDate[3],endDate[4],endDate[5],endDate[6] );
		
		ViewQuery query = new ViewQuery().designDocId("_design/shack").viewName("appdate").startKey(startKey).endKey(endKey);
		
		List<RawData> rawDatas;
		try {
			rawDatas = db.queryView(query, RawData.class);
		} catch (DocumentNotFoundException e) {
			// if there were no documents to the specific query
			e.printStackTrace();
			throw e;
		}
		
		return rawDatas;
	}

	@Override
	public List<Map<String,Object>> wordCount(RequestParameter param)
			throws Exception { 

		logger.info( param.getUserHashId() + " Word Count" );
		
		CouchDbConnector db = connector.getCouchDbConnetor("user_" + param.getUserHashId());
		
//		ComplexKey startKey = ComplexKey.of( param.getDocumentId() );
//		ComplexKey endKey = ComplexKey.of( param.getDocumentId() + "a" );
		
		ViewQuery query = new ViewQuery().designDocId("_design/shack")
				.viewName("wordcount")
//				.startKey(startKey)
//				.endKey(endKey)
				.group(true);
		
		HashMap<String,Object> tmp;
		List< Map<String,Object> > result = new ArrayList<Map<String,Object>>();
		List<Row> viewResult;
		try {
			viewResult = db.queryView(query).getRows();
			
			tmp = new HashMap<String,Object>();
			tmp.put("rows", viewResult.size());
			result.add(tmp);
			
			for ( Row i : viewResult ) {
				tmp = new HashMap<String,Object>();
				tmp.put( i.getKey().substring( 2, i.getKey().length()-2 ), i.getValueAsInt() );
				result.add(tmp);
			}
			
		} catch (DocumentNotFoundException e) {
			// if there were no documents to the specific query
			e.printStackTrace();
			throw e;
		}
		
		return result;
	}

}

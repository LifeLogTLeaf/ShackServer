/**
 * 
 */
package org.soma.tleaf.service;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soma.tleaf.dao.RestApiDao;
import org.soma.tleaf.domain.RawData;
import org.soma.tleaf.domain.RequestParameter;
import org.soma.tleaf.domain.ResponseDataWrapper;
import org.soma.tleaf.domain.UserInfo;
import org.soma.tleaf.exception.CustomException;
import org.soma.tleaf.util.ISO8601;

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
		// Create Response Result
		ResponseDataWrapper result = new ResponseDataWrapper();
		// Set Date to Response
		result.setLogs(restApiDao.getAllDataFromAppId(param));
		return result;
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
	 * 
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

}

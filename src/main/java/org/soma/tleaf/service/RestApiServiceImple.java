/**
 * 
 */
package org.soma.tleaf.service;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soma.tleaf.accesskey.AccessKey;
import org.soma.tleaf.dao.RestApiDao;
import org.soma.tleaf.domain.RawData;
import org.soma.tleaf.domain.RequestDataWrapper;
import org.soma.tleaf.domain.RequestParameter;
import org.soma.tleaf.domain.ResponseDataWrapper;
import org.soma.tleaf.exception.ExpiredAccessKeyException;
import org.soma.tleaf.exception.InvalidAccessKeyException;
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
	public Map<String, Object> postUserData(RequestDataWrapper dataWrapper, RequestParameter param) throws Exception {
		// Create Response Result
		Map<String, Object> result = new HashMap<String, Object>();
		// Create Data
		RawData rawData = new RawData();
		rawData.setData(dataWrapper.getserviceData());
		rawData.setTime(ISO8601.now());
		rawData.setAppid(param.getAppId());
		// Request Create Data and Set Response result 
		result.put("result", "true");
		result.put("id", restApiDao.postData(rawData, param));
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
			param.setStartKey(ISO8601.LONG_LONG_AGO);
			param.setEndKey(ISO8601.FAR_FAR_AWAY);
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

}

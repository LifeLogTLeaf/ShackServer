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
	public ResponseDataWrapper postUserData(RequestDataWrapper dataWrapper, RequestParameter param) throws Exception {
		ResponseDataWrapper result = new ResponseDataWrapper();
		// Create Data
		RawData rawData = new RawData();
		rawData.setData(dataWrapper.getserviceData());
		rawData.setTime(ISO8601.now());
		rawData.setAppid(checkAppId(param.getAppId()));
		// Set UserHashId
		param.setUserHashId(getUserId(param.getAccessKey()));
		// Request Create Data 
		restApiDao.postData(rawData, param);
		return result;
	}

	/**
	 * Author : RichardJ
	 * Date : Oct 21, 2014 08:55:06
	 * Description :
	 * Issue :
	 */
	@Override
	public ResponseDataWrapper getUserData(RequestParameter param) {
		RawData logData = restApiDao.getData(param);
		ResponseDataWrapper data = new ResponseDataWrapper();

		data.setData(logData.getData());
		data.setVersion("1.0.0");

		return data;
	}

	/**
	 * Author : RichardJ
	 * Date : Oct 21, 2014 08:55:06
	 * Description : 클라이언트로부터 받은 엑세스키를 가지고 데이터를 생성한 해당 사용자의 데이터베이스이름을 가져옵니다.
	 * Issue : 인증키 검증부분은 아직 완성되지 않았습니다.
	 */
	public String getUserId(String key) throws Exception {
		AccessKey accessKey;
		accessKey = restApiDao.checkAccessKey(key);
		if (accessKey == null) {
			throw new InvalidAccessKeyException();
		}
		// getvalid()는 엑세스키에 대한 검증이 안들어간 것입니다.
		if (accessKey.getvalid() == false) {
			throw new ExpiredAccessKeyException();
		}
		return accessKey.getUserId();

	}

	/**
	 * Author : RichardJ
	 * Date   : Oct 22, 2014 2:46:06 PM
	 * Description : 클라이언트로부터 받은 서비스식별자를 체크합니다.
	 * issue : 아직 어떻게 식별할지 안나왔습니다.
	 */
	public String checkAppId(String appId) {
		return appId;
	}

}

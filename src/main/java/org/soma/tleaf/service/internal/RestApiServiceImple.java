/**
 * 
 */
package org.soma.tleaf.service.internal;

import java.util.HashMap;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soma.tleaf.dao.RestApiDao;
import org.soma.tleaf.domain.RequestDataWrapper;
import org.soma.tleaf.domain.ResponseDataWrapper;
import org.soma.tleaf.domain.UserLogData;
import org.soma.tleaf.service.RestApiService;

/**
 * Created with Eclipse IDE
 * Author : RichardJ
 * Date : Oct 17, 2014 6:13:35 PM
 * Description :
 */
public class RestApiServiceImple implements RestApiService {
	private Logger logger = LoggerFactory.getLogger(RestApiServiceImple.class);

	@Inject
	private RestApiDao restApiDao;

	public void postUserData(RequestDataWrapper dataWrapper) {
		UserLogData logData = new UserLogData();
		logData.setData(dataWrapper);
		logData.setTime(System.currentTimeMillis());
		// logger.info("In the Service Componet : " + logData.toString());

		// Before put data we will validate data ( it's planned )
		// 검정을 하기 위해서는 컴포지트 패턴이 필요할것 같다.
		restApiDao.postData(logData);
	}

	@Override
	public ResponseDataWrapper getUserData(String documentId) {
		UserLogData logData = restApiDao.getData(documentId);
		ResponseDataWrapper data = new ResponseDataWrapper();
		
		// Before send data we will validate data ( it's planned )
		// version check and data check .. etc
		data.setData(logData.getData().getserviceData());
		data.setVersion("1.0.0");
		//logger.info("In the Service Componet : " + data.toString());

		return data;
	}

}

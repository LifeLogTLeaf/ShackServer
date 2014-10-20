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
		// 데이터 정의에 의해서 변경될 예정.
		UserLogData logData = new UserLogData();
		logData.setData(dataWrapper.getserviceData());
		logData.setTime(System.currentTimeMillis());
		
		//테이블 형식으로 검증하는 기능을 넣을 예정.
		restApiDao.postData(logData);
	}

	@Override
	public ResponseDataWrapper getUserData(String documentId) {
		UserLogData logData = restApiDao.getData(documentId);
		ResponseDataWrapper data = new ResponseDataWrapper();
		
		//테이블 형식으로 검증하는 기능을 넣을 예정.
		data.setData(logData.getData());
		data.setVersion("1.0.0");

		return data;
	}

}

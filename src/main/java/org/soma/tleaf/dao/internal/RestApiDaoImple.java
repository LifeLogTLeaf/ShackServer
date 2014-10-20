/**
 * 
 */
package org.soma.tleaf.dao.internal;

import javax.inject.Inject;

import org.ektorp.CouchDbConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soma.tleaf.couchdb.CouchDbConn;
import org.soma.tleaf.dao.RestApiDao;
import org.soma.tleaf.domain.SimpleData;
import org.soma.tleaf.domain.UserLogData;

/**
 * Created with Eclipse IDE
 * Author : RichardJ
 * Date : Oct 17, 2014 6:14:35 PM
 * Description :
 */
public class RestApiDaoImple implements RestApiDao {
	private Logger logger = LoggerFactory.getLogger(RestApiDaoImple.class);

	@Inject
	private CouchDbConn connector;

	/**
	 * 예외처리 부분 넣어야한다.
	 * 데이터 베이스 이름을 어디서 받아서 처리해야하는
	 */
	@Override
	public void postData(UserLogData userlogData) {
		// logger.info("In the Dao Componet : " + userlogData.toString());

		// DB name will be changed!!!
		try {
			CouchDbConnector db = connector.getCouchDbConnetor("dairy");
			db.createDatabaseIfNotExists();
			db.create(userlogData);
			logger.info("created data : " + userlogData.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 예외처리 부분 넣어야한다.
	 * 데이터 베이스 이름을 어디서 받아서 처리해야하는
	 */
	@Override
	public UserLogData getData(String documentId) {
		UserLogData data = null;
		// DB name will be changed!!!
		try {
			CouchDbConnector db = connector.getCouchDbConnetor("dairy");
			data = db.get(UserLogData.class, documentId);
			//logger.info("retrieved data : " + data.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}

}

/**
 * 
 */
package org.soma.tleaf.dao;

import javax.inject.Inject;

import org.ektorp.CouchDbConnector;
import org.ektorp.DocumentNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soma.tleaf.accesskey.AccessKey;
import org.soma.tleaf.couchdb.CouchDbConn;
import org.soma.tleaf.domain.RawData;
import org.soma.tleaf.domain.RequestParameter;

/**
 * Created with Eclipse IDE
 * Author : RichardJ
 * Date : Oct 17, 2014 6:14:35 PM
 * Description :
 */
public class RestApiDaoImple implements RestApiDao {
	private Logger logger = LoggerFactory.getLogger(RestApiDaoImple.class);
	private static String ACCESSKEYS_DB_NAME = "accesskeys";

	@Inject
	private CouchDbConn connector;

	/**
	 * Author : RichardJ
	 * Date : Oct 22, 2014 10:28:35 PM
	 * Description : 클라이언트로부터 받은 데이터를 디비에 저장합니다.
	 */
	@Override
	public String postData(RawData rawData, RequestParameter param) throws Exception {
		CouchDbConnector db = connector.getCouchDbConnetor(param.getUserHashId());
		db.createDatabaseIfNotExists();
		db.create(rawData);;
		return rawData.getId();
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
	 * Date : Oct 22, 2014 10:28:35 PM
	 * Description : 클라이언트로부터 받은 인증키가 데이터베이스에 존재하는지 확인하는 메소드입니다.
	 */
	@Override
	public AccessKey checkAccessKey(String Key) throws Exception {
		CouchDbConnector db;
		db = connector.getCouchDbConnetor(ACCESSKEYS_DB_NAME);
		db.createDatabaseIfNotExists();
		
		AccessKey accessKey = null;
		try{
			accessKey = db.get(AccessKey.class, Key);
		} catch (DocumentNotFoundException e){
			
		}
		return accessKey;
	}

}

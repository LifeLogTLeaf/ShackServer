/**
 * 
 */
package org.soma.tleaf.dao;

import java.util.List;

import javax.inject.Inject;

import org.ektorp.CouchDbConnector;
import org.ektorp.DocumentNotFoundException;
import org.ektorp.ViewQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soma.tleaf.accesskey.AccessKey;
import org.soma.tleaf.couchdb.CouchDbConn;
import org.soma.tleaf.domain.RawData;
import org.soma.tleaf.domain.RequestParameter;
import org.soma.tleaf.util.ISO8601;

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
		CouchDbConnector db = connector.getCouchDbConnetor("user_"+param.getUserHashId());
		db.create(rawData);
		
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
	 * Date : Oct 23, 2014 9:48:52 AM
	 * Description : 해당 사용자 데이터베이스에서 전체 데이터를 읽어옵니다.
	 */
	@Override
	public List<RawData> getAllData(RequestParameter param) throws Exception {
		CouchDbConnector db = connector.getCouchDbConnetor("user_"+param.getUserHashId());
		ViewQuery query = new ViewQuery().designDocId("_design/all").viewName("time").startKey(param.getStartKey())
				.endKey(param.getEndKey()).limit(Integer.valueOf(param.getLimit())).descending(param.isDescend());

		List<RawData> rawDatas = db.queryView(query, RawData.class);
		// For test print Code
		logger.info(""+rawDatas.size());
		for (RawData rd : rawDatas) {
			logger.info(rd.getTime());
		}
		return rawDatas;
	}
	
	
	/**
	 * Author : RichardJ
	 * Date : Oct 23, 2014 9:48:52 AM
	 * Description : 해당 사용자 데이터베이스에서 해당 앱의 아이디의 데이터만 읽어옵니다.
	 * Issue : 앱아이디 쿼리 조회 디자인뷰가 아직 완성 안됬습니다.
	 */
	@Override
	public List<RawData> getAllDataFromAppId(RequestParameter param) throws Exception {
		logger.info(""+param.getAppId());
		CouchDbConnector db = connector.getCouchDbConnetor("user_"+param.getUserHashId());
		// 디자인뷰 쿼리 소스가 들어갈 공간
		// 요청 파라미터 인자를 이용해서 디자인 뷰에 인자값을 채워넣는다.
		return null;
	}


}

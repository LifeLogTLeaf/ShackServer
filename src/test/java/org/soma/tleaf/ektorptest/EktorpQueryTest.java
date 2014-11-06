/**
 * 
 */
package org.soma.tleaf.ektorptest;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.ektorp.CouchDbConnector;
import org.ektorp.CouchDbInstance;
import org.ektorp.ViewQuery;
import org.ektorp.http.StdHttpClient;
import org.ektorp.impl.StdCouchDbConnector;
import org.ektorp.impl.StdCouchDbInstance;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soma.tleaf.domain.SimpleRawData;
import org.soma.tleaf.repository.SimpleRawDataRepository;

/**
 * Created with Eclipse IDE
 * Author : RichardJ
 * Date : Oct 16, 2014 2:07:56 PM
 * Description :Ektorp라이브러리를 사용해서 쿼리문을 테스트하는 클래스입니다.
 */
public class EktorpQueryTest {
	private static String url;
	private static String user;
	private static String password;
	private static String docId;
	private static String DbName = "simpledb";

	static Logger logger = LoggerFactory.getLogger(EktorpQueryTest.class);

	@BeforeClass
	public static void setup() throws Exception {
		Configuration config = new PropertiesConfiguration("couchdb.properties");
		url = config.getString("url");
		user = config.getString("user");
		password = config.getString("password");
		CouchDbInstance dbInstance = createDbInstance();
		CouchDbConnector db = new StdCouchDbConnector(DbName, dbInstance);

		// Create DB
		db.createDatabaseIfNotExists();
		
		// Add Bulk Dummy data
		//db.executeBulk( makedummy());
		//db.executeBulk( makedummy2());
		
	}

	// After finish unit test we will tear down database which we have created
	@AfterClass
	public static void teardown() throws MalformedURLException {
		CouchDbInstance dbInstance = createDbInstance();
		List<String> dbs = dbInstance.getAllDatabases();
		for (String dbname : dbs) {
			// logger.info("database name : " + dbname);
			if (dbname.equals(DbName)) {
				//dbInstance.deleteDatabase(dbname);
			}
		}
	}

	// Query with Embedded definition design document
	//@Test
	public void testGetAllLogByAppId() throws MalformedURLException {
		CouchDbInstance dbInstance = createDbInstance();
		CouchDbConnector db = new StdCouchDbConnector(DbName, dbInstance);
		SimpleRawDataRepository repository = new SimpleRawDataRepository(db);

		List<SimpleRawData> simpleDatas = repository.getLogByAppId();
		for (SimpleRawData data : simpleDatas) {
			logger.info("(all)data time : " + data.getTime() + ", appId : " + data.getAppId());
		}
	}

	// Query with Embedded definition design document
	//@Test
	public void testGetAllLog() throws MalformedURLException {
		CouchDbInstance dbInstance = createDbInstance();
		CouchDbConnector db = new StdCouchDbConnector(DbName, dbInstance);
		SimpleRawDataRepository repository = new SimpleRawDataRepository(db);

		// Query All data
		List<SimpleRawData>  datas= repository.getAll();
		for (SimpleRawData data : datas) {
			logger.info("(all)data time : " + data.getTime() + ", appId : " + data.getAppId());
		}
	}
	
	//@Test
	public void testGetAllByViewQuery2() throws MalformedURLException {
		CouchDbInstance dbInstance = createDbInstance();
		CouchDbConnector db = new StdCouchDbConnector(DbName, dbInstance);
		ViewQuery query = new ViewQuery().designDocId("_design/shack").viewName("time").descending(true);
		
		List<SimpleRawData> datas = db.queryView(query, SimpleRawData.class);

		for (SimpleRawData data : datas) {
			logger.info("(ViewQuery)data time : " + data.getTime());
		}
	}
	
	
	// Get CouchDbInstance
	private static CouchDbInstance createDbInstance() throws MalformedURLException {
		StdHttpClient httpClient = (StdHttpClient) new StdHttpClient.Builder().username(user).password(password).url(url)
				.connectionTimeout(100000).build();

		return new StdCouchDbInstance(httpClient);
	}
	
	private static List<SimpleRawData> makedummy(){
		String[] timeSet = {"2014-10-30T20:02:48+09:00",
				"2014-10-30T20:02:48+09:01",
				"2014-10-30T20:02:48+09:03",
				"2014-10-30T20:02:48+09:05",
				"2014-10-30T20:02:48+09:07",
				"2014-10-30T20:02:48+09:09",
				"2014-10-30T20:02:48+09:11",
				"2014-10-30T20:02:48+09:13",
				"2014-10-30T20:02:48+09:15",
				"2014-10-30T20:02:48+09:17",
				};
		List<SimpleRawData> result = new ArrayList<SimpleRawData>();
		Map<String, Object>data = new HashMap<String, Object>();
		ArrayList<String> array = new ArrayList<String>();
		data.put("type", "normal");
		data.put("title", "오늘은 너무 행복한 하루였다.");
		data.put("content", "난 오늘 11시에 태평역에서 가산디지털단지역으로 지하철을 타고 갔다. 도착하고 나서 짜장범벅을 먹었고 일하고 일하고 일하고 일하고 전화하고 하다가 오후 10시 37분인데 일하고 서류 만들고 이러고 있다. 배가 고파서 뭘 먹을까 배달의 민족을 10분전에 찾아보다가 별로 땡기는게 없어서 편의점에 갈까 고민중이다.");
		data.put("emotion", "happy");
		array.add("날씨");
		array.add("장소");
		array.add("만남사람이름");
		data.put("tag", array);
		
		for(int i=0; i<10; i++){
			SimpleRawData d = new SimpleRawData();
			d.setAppId("tiary");
			d.setData(data);
			d.setTime(timeSet[i]);
			result.add(d);
		}
		
		
		return result;
	}
	
	private static List<SimpleRawData> makedummy2(){
		String[] timeSet = {"2014-10-30T20:02:48+09:00",
				"2014-10-30T20:02:48+09:02",
				"2014-10-30T20:02:48+09:04",
				"2014-10-30T20:02:48+09:06",
				"2014-10-30T20:02:48+09:08",
				"2014-10-30T20:02:48+09:10",
				"2014-10-30T20:02:48+09:12",
				"2014-10-30T20:02:48+09:14",
				"2014-10-30T20:02:48+09:16",
				"2014-10-30T20:02:48+09:18",
				};
		List<SimpleRawData> result = new ArrayList<SimpleRawData>();
		Map<String, Object>data = new HashMap<String, Object>();
		data.put("type", "sns");
		data.put("action", "post");
		data.put("content", "햄버거 맛있다 꿀맛이다. 과제하는데 딴 친구가 말썽이다 이상한걸 시킨다. 하는 나도 바보같다. 일기끝.");
		
		for(int i=0; i<10; i++){
			SimpleRawData d = new SimpleRawData();
			d.setAppId("collector");
			d.setData(data);
			d.setTime(timeSet[i]);
			result.add(d);
		}
		
		
		return result;
	}
}

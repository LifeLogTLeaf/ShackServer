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
import org.ektorp.http.StdHttpClient;
import org.ektorp.impl.StdCouchDbConnector;
import org.ektorp.impl.StdCouchDbInstance;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soma.tleaf.domain.SimpleUserInfo;
import org.soma.tleaf.util.ISO8601;

/**
 * Created with Eclipse IDE
 * Author : RichardJ
 * Date : Oct 16, 2014 12:26:14 PM
 * Description : Ektorp라이브러리를 사용해서 기본적인 CRUD를 테스트하는 클래스입니다.
 */
public class EktorpTest {
	private static String url;
	private static String user;
	private static String password;
	private static String docId;
	private static String DbName = "swimyoung_test";

	static Logger logger = LoggerFactory.getLogger(EktorpTest.class);

	// Before start unit test we have to set configuration
	@BeforeClass
	public static void setup() throws Exception {
		Configuration config = new PropertiesConfiguration("couchdb.properties");
		url = config.getString("url");
		user = config.getString("user");
		password = config.getString("password");
	}

	// After finish unit test we will teardown database which we have created
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

	// test for create Database
	//@Test
	public void testCrateDatabase() throws MalformedURLException {
		CouchDbInstance dbInstance = createDbInstance();
		CouchDbConnector db = new StdCouchDbConnector(DbName, dbInstance);

		// Create Database
		db.createDatabaseIfNotExists();
	}

	// test for Create Document
	//@Test
	public void testCreateDocument() throws MalformedURLException {
		CouchDbInstance dbInstance = createDbInstance();
		CouchDbConnector db = new StdCouchDbConnector(DbName, dbInstance);

		// Create Dummy data
		SimpleUserInfo userInfo = new SimpleUserInfo();
		userInfo.setAge(26);
		userInfo.setEmail("hulk@avengers.org");
		userInfo.setGender("man");
		userInfo.setPassword("secreat");
		userInfo.setNickname("red_hulk");
		
		
		// Add Data
		db.createDatabaseIfNotExists();
		db.create(userInfo);
		// will use for another unit test
		
	}

	// test for Retrieve Document
	//@Test
	public void testRetrieveDocument() throws MalformedURLException {
		CouchDbInstance dbInstance = createDbInstance();
		CouchDbConnector db = new StdCouchDbConnector(DbName, dbInstance);

		// Get Specific document using document id
		SimpleUserInfo userInfo = db.get(SimpleUserInfo.class, "e756171d1eb520baecff8c1d1b01a36d");
	}

	// test for Update Document
	//@Test
	public void testUpdateDocumnet() throws MalformedURLException {
		CouchDbInstance dbInstance = createDbInstance();
		CouchDbConnector db = new StdCouchDbConnector(DbName, dbInstance);
		String user_id = "e756171d1eb520baecff8c1d1b01b199";
		String app_id = "twitter";
		
		// Front will create this data.
		Map<String, Object> serviceData = new HashMap<String, Object>();
		serviceData.put("appId", app_id);
		serviceData.put("fbAccesskey", "asjdhbajshbdhjasbkjgblajdlkanslkgnlkabk");
		serviceData.put("lastCreatedTime", ISO8601.LONG_LONG_AGO);
		serviceData.put("lastUpdatedTime", ISO8601.LONG_LONG_AGO);
		
		// get services 
		SimpleUserInfo userInfo = db.get(SimpleUserInfo.class, user_id);
		ArrayList<Map> services = userInfo.getServices();
		
		// if services doesn't exist, make new services array
		if(services == null) services = new ArrayList<Map>();
		
		// add app service data
		int index= -1;
		for(int i=0; i<services.size(); i++){
			logger.info(""+services.get(i).get("appId"));
			if(services.get(i).get("appId").equals(app_id)) index = i;
		}
		
		if (index > -1){
			services.set(index, serviceData);
		} else {
			services.add(serviceData);
		}
		
		userInfo.setServices(services);
		
		
		db.update(userInfo);

	}

	// test for Delete Document
	//@Test
	public void testDeleteDocument() throws MalformedURLException {
		CouchDbInstance dbInstance = createDbInstance();
		CouchDbConnector db = new StdCouchDbConnector(DbName, dbInstance);

		SimpleUserInfo userInfo = db.get(SimpleUserInfo.class, docId);
		db.delete(userInfo);
	}

	// Get CouchDbInstance
	private static CouchDbInstance createDbInstance() throws MalformedURLException {
		StdHttpClient httpClient = (StdHttpClient) new StdHttpClient.Builder().username(user).password(password).url(url)
				.connectionTimeout(100000).build();

		return new StdCouchDbInstance(httpClient);
	}

}

/**
 * 
 */
package org.soma.tleaf.ektorptest;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.ektorp.CouchDbConnector;
import org.ektorp.CouchDbInstance;
import org.ektorp.http.StdHttpClient;
import org.ektorp.impl.StdCouchDbConnector;
import org.ektorp.impl.StdCouchDbInstance;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soma.tleaf.domain.SimpleData;
import org.soma.tleaf.repository.SimpleDataRepository;

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
	private static String DbName = "simpledb";

	static Logger logger = LoggerFactory.getLogger(EktorpTest.class);

	// Before start unit test we have to set configuration
	@BeforeClass
	public static void setup() throws Exception {
		Configuration config = new PropertiesConfiguration("couchdb.properties");
		url = config.getString("url");
		user = config.getString("id");
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
				dbInstance.deleteDatabase(dbname);
			}
		}
	}

	// test for create Database
	@Test
	public void testCrateDatabase() throws MalformedURLException {
		CouchDbInstance dbInstance = createDbInstance();
		CouchDbConnector db = new StdCouchDbConnector(DbName, dbInstance);

		// Create Database
		db.createDatabaseIfNotExists();
	}

	// test for Create Document
	@Test
	public void testCreateDocument() throws MalformedURLException {
		CouchDbInstance dbInstance = createDbInstance();
		CouchDbConnector db = new StdCouchDbConnector(DbName, dbInstance);
		SimpleDataRepository repository = new SimpleDataRepository(db);

		// Create Dummy data
		SimpleData data = new SimpleData();
		data.setTime(System.currentTimeMillis());
		data.setType("init");
		ArrayList<Object> datas = new ArrayList<Object>();
		HashMap<String, Object> someData = new HashMap<String, Object>();
		someData.put("(1)", "one");
		someData.put("(2)", "two");
		someData.put("(3)", "three");
		
		datas.add("1");
		datas.add("2");
		datas.add("3");
		datas.add(someData);
		
		data.setDatas(datas);
		

		// Add Data
		repository.add(data); // can be db.create(data);
		logger.info("Created document : " + data);

		// will use for another unit test
		docId = data.getId();

	}

	// test for Retrieve Document
	@Test
	public void testRetrieveDocument() throws MalformedURLException {
		CouchDbInstance dbInstance = createDbInstance();
		CouchDbConnector db = new StdCouchDbConnector(DbName, dbInstance);
		SimpleDataRepository repository = new SimpleDataRepository(db);

		// Get Specific document using document id
		// db.get(SimpleData.class, docId)
		SimpleData data = repository.get(docId);
		logger.info("Retrieved document : " + data);
		logger.info("Retrieved document datas size : " + data.getDatas().size());
		logger.info("Retrieved document datas data : " + data.getDatas().get(3));
		
	}

	// test for Update Document
	@Test
	public void testUpdateDocumnet() throws MalformedURLException {
		CouchDbInstance dbInstance = createDbInstance();
		CouchDbConnector db = new StdCouchDbConnector(DbName, dbInstance);
		SimpleDataRepository repository = new SimpleDataRepository(db);

		// Get Specific document using document id
		SimpleData data = repository.get(docId);
		data.setType("chaged");

		// Update Document
		//db.update(data);
		repository.update(data);
		logger.info("Updated document : " + data);

		// Get Specific document using document id
		data = repository.get(docId);
		Assert.assertEquals(data.getType(), "chaged");
	}

	// test for Delete Document
	@Test
	public void testDeleteDocument() throws MalformedURLException {
		CouchDbInstance dbInstance = createDbInstance();
		CouchDbConnector db = new StdCouchDbConnector(DbName, dbInstance);
		SimpleDataRepository repository = new SimpleDataRepository(db);

		SimpleData data = repository.get(docId);
		data.setType("deleted");
		//db.delete(data);
		repository.remove(data);
		logger.info("Deleted document : " + data);

		Assert.assertNull(db.get(SimpleData.class, docId));
	}

	// Get CouchDbInstance
	private static CouchDbInstance createDbInstance() throws MalformedURLException {
		StdHttpClient httpClient = (StdHttpClient) new StdHttpClient.Builder().username(user).password(password).url(url)
				.connectionTimeout(100000).build();

		return new StdCouchDbInstance(httpClient);
	}

}

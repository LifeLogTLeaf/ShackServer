/**
 * 
 */
package org.soma.tleaf.ektorptest;

import java.io.File;
import java.net.MalformedURLException;
import java.util.List;

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
import org.soma.tleaf.domain.SimpleData;
import org.soma.tleaf.repository.SimpleDataRepository;

/**
 * Created with Eclipse IDE
 * Author : RichardJ
 * Date : Oct 16, 2014 2:51:52 PM
 * Description : Ektrop라이브러리를 사용해서 이미지파일을 처리하는것을 테스트하는 클래스입니다.
 */
public class EktorpAttachmentTest {
	private static String url;
	private static String user;
	private static String password;
	private static String docId;
	private static String DbName = "simpledb";

	static Logger logger = LoggerFactory.getLogger(EktorpTest.class);

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

	// test for Create Document and Attachment in one operation
	@Test
	public void testCreateDocument() throws MalformedURLException {
		CouchDbInstance dbInstance = createDbInstance();
		CouchDbConnector db = new StdCouchDbConnector(DbName, dbInstance);
		SimpleDataRepository repository = new SimpleDataRepository(db);

		// Create Dummy data
		SimpleData data = new SimpleData();
		data.setTime(System.currentTimeMillis());
		data.setType("init");
		data.setAppAuthor("picture");

		// Add Data
		repository.add(data); // can be db.create(data);
		logger.info("Created document : " + data);

		// will use for another unit test
		docId = data.getId();
		
	}

	// Get CouchDbInstance
	private static CouchDbInstance createDbInstance() throws MalformedURLException {
		StdHttpClient httpClient = (StdHttpClient) new StdHttpClient.Builder().username(user).password(password).url(url)
				.connectionTimeout(100000).build();

		return new StdCouchDbInstance(httpClient);
	}

}

/**
 * 
 */
package org.soma.tleaf.ektorptest;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.List;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.ektorp.AttachmentInputStream;
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
	private static String docId, revId;
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

	// test for Create Document and Attachment in one operation
	//@Test
	public void testCreateDocumentAndAttachment() throws MalformedURLException {
		CouchDbInstance dbInstance = createDbInstance();
		CouchDbConnector db = new StdCouchDbConnector(DbName, dbInstance);
		db.createDatabaseIfNotExists();
		
		// Create Dummy data
		SimpleData data = new SimpleData();
		data.setType("image");

		// Add Data
		db.create(data); // can be db.create(data);
		logger.info("Created document : " + data);

		// will use for another unit test
		docId = data.getId();
		revId = data.getRevision();
		

		InputStream image = null;
		try {
			image = new FileInputStream("/Users/jangyoungjin/Downloads/1.png");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		AttachmentInputStream attachment = new AttachmentInputStream(docId, image, "image/png");
		db.createAttachment(docId, revId, attachment);
	}
	
	//@Test
	public void testGetAttachment() throws MalformedURLException{
		CouchDbInstance dbInstance = createDbInstance();
		CouchDbConnector db = new StdCouchDbConnector(DbName, dbInstance);
		AttachmentInputStream data = db.getAttachment("e0da8f9f1fe2b3ca4d8872de6f05aad7", "e0da8f9f1fe2b3ca4d8872de6f05aad7");
		logger.info("length : " + data.getContentLength());
		logger.info("length : " + data.getId());
		
		try {
			logger.info("read : " + data.read());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

	//@Test
	public void readFild() {

	}

	// Get CouchDbInstance
	private static CouchDbInstance createDbInstance() throws MalformedURLException {
		StdHttpClient httpClient = (StdHttpClient) new StdHttpClient.Builder().username(user).password(password).url(url).connectionTimeout(100000)
				.build();

		return new StdCouchDbInstance(httpClient);
	}

}

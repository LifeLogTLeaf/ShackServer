/**
 * 
 */
package org.soma.tleaf.ektorptest;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.codehaus.jackson.JsonNode;
import org.ektorp.CouchDbConnector;
import org.ektorp.CouchDbInstance;
import org.ektorp.ViewQuery;
import org.ektorp.ViewResult;
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

		// Create Bulk Dummy data
		List<SimpleData> simpleDatas = new ArrayList<SimpleData>();
//		simpleDatas.add(new SimpleData(System.currentTimeMillis(), "post", "crawler"));
//		simpleDatas.add(new SimpleData(System.currentTimeMillis(), "picture", "crawler"));
//		simpleDatas.add(new SimpleData(System.currentTimeMillis(), "post", "crawler"));
//		simpleDatas.add(new SimpleData(System.currentTimeMillis(), "picture", "crawler"));
//		simpleDatas.add(new SimpleData(System.currentTimeMillis(), "post", "crawler"));
//		simpleDatas.add(new SimpleData(System.currentTimeMillis(), "contents", "dairy"));
//		simpleDatas.add(new SimpleData(System.currentTimeMillis(), "custom", "dairy"));
//		simpleDatas.add(new SimpleData(System.currentTimeMillis(), "custom", "dairy"));
//		simpleDatas.add(new SimpleData(System.currentTimeMillis(), "contents", "dairy"));
//		simpleDatas.add(new SimpleData(System.currentTimeMillis(), "contents", "dairy"));

		// Add Bulk Dummy data
		db.executeBulk(simpleDatas);
	}

	// After finish unit test we will tear down database which we have created
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

	// Query with Embedded definition design document
	@Test
	public void testGetSpecificDataByViewQuery() throws MalformedURLException {
		CouchDbInstance dbInstance = createDbInstance();
		CouchDbConnector db = new StdCouchDbConnector(DbName, dbInstance);
		SimpleDataRepository repository = new SimpleDataRepository(db);

		List<SimpleData> simpleDatas = repository.findByAppAuthor("dairy");
		for (SimpleData data : simpleDatas) {
			logger.info("Specific Query data : " + data.toString());
		}
	}

	// Query with Embedded definition design document
	@Test
	public void testGetAllByViewQuery() throws MalformedURLException {
		CouchDbInstance dbInstance = createDbInstance();
		CouchDbConnector db = new StdCouchDbConnector(DbName, dbInstance);
		SimpleDataRepository repository = new SimpleDataRepository(db);

		// Query All data
		List<SimpleData> simpleDatas = repository.getAll();
		for (SimpleData data : simpleDatas) {
			logger.info("All data : " + data.toString());
		}
	}

	// Query with PreDefined view which is in Couch DB 
	@Test
	public void testScalarQuery() throws MalformedURLException {
		CouchDbInstance dbInstance = createDbInstance();
		CouchDbConnector db = new StdCouchDbConnector(DbName, dbInstance);

		ViewQuery query = new ViewQuery().designDocId("_design/SimpleData").viewName("all");
		ViewResult result = db.queryView(query);
		for (ViewResult.Row row : result) {
			logger.info("row data : " + row.getValue());
		    JsonNode keyNode = row.getKeyAsNode();
		    JsonNode valueNode = row.getValueAsNode();
		    JsonNode docNode = row.getDocAsNode();
		}
	}

	// Pagination Query
	@Test
	public void testPaginationViewQuery() throws MalformedURLException {
		CouchDbInstance dbInstance = createDbInstance();
		CouchDbConnector db = new StdCouchDbConnector(DbName, dbInstance);

		/*
		PageRequest pageRequest = PageRequest.firstPage(3);

		ViewQuery query = new ViewQuery().designDocId("SimpleData").viewName("all").includeDocs(true);
		Page<SimpleData> result = db.queryForPage(query, pageRequest, SimpleData.class);

		for (SimpleData data : result) {
			logger.info("paged data : " + data.toString());
		}

		Page<SimpleData> previousPage = result;
		PageRequest nextPageRequest = previousPage.getNextPageRequest();
		Page<SimpleData> nextPage = db.queryForPage(query, nextPageRequest, SimpleData.class);

		for (SimpleData data : nextPage) {
			logger.info("next paged data : " + data.toString());
		}*/
	}

	// Get CouchDbInstance
	private static CouchDbInstance createDbInstance() throws MalformedURLException {
		StdHttpClient httpClient = (StdHttpClient) new StdHttpClient.Builder().username(user).password(password).url(url)
				.connectionTimeout(100000).build();

		return new StdCouchDbInstance(httpClient);
	}
}

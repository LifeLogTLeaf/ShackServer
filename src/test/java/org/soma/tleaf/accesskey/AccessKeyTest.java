/**
 * 
 */
package org.soma.tleaf.accesskey;
import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

import java.io.IOException;
import java.net.MalformedURLException;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.ektorp.CouchDbConnector;
import org.ektorp.CouchDbInstance;
import org.ektorp.http.StdHttpClient;
import org.ektorp.impl.StdCouchDbConnector;
import org.ektorp.impl.StdCouchDbInstance;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soma.tleaf.domain.HashId;
import org.soma.tleaf.domain.SimpleRawData;
import org.soma.tleaf.domain.UserInfo;
import org.soma.tleaf.util.ISO8601;

/**
 * Created with Eclipse IDE
 * Author : RichardJ
 * Date : Oct 16, 2014 2:07:15 PM
 * Description :
 */
public class AccessKeyTest {

	private static String url;
	private static String user;
	private static String password;
	private static String docId;
	private static String USERS_DB = "tleaf_users";
	private static String HASHIDS_DB = "tleaf_hashIds";
	private static String ACCESSKEYS_DB = "tleaf_apikey";

	static Logger logger = LoggerFactory.getLogger(AccessKeyTest.class);

	// Before start unit test we have to set configuration
	@BeforeClass
	public static void setup() throws Exception {
		Configuration config = new PropertiesConfiguration("couchdb.properties");
		url = config.getString("url");
		user = config.getString("user");
		password = config.getString("password");
	}

	//@Test
	public void createUser() throws MalformedURLException {
		CouchDbInstance dbInstance = createDbInstance();
		CouchDbConnector userInfoDb = new StdCouchDbConnector(USERS_DB, dbInstance);
		CouchDbConnector hashIdDb = new StdCouchDbConnector(HASHIDS_DB, dbInstance);
		CouchDbConnector accessKeys = new StdCouchDbConnector(ACCESSKEYS_DB, dbInstance);

		// 1. 사용자 아이디를 생성한다.
		// Create User
		UserInfo userInfo = new UserInfo();
		userInfo.setEmail("zzang@gmail.com");
		userInfo.setGender("boy");
		userInfo.setNickname("Richard");
		userInfo.setAge(18);
		userInfo.setPassword("hashedPassword");

		// Create Users Database
		userInfoDb.createDatabaseIfNotExists();

		// Create User Document in Users DB
		userInfoDb.create(userInfo);

		// 2. 사용자 아이디에 해당하는 해쉬키를 생성한다.
		// Create HashId
		HashId hashId = new HashId();
		hashId.setEmail("zzang@gmail.com");
		hashId.setHashId(userInfo.getHashId());

		// Create hashIds Database
		hashIdDb.createDatabaseIfNotExists();

		// Create User Document in Users DB
		hashIdDb.create(hashId);

		// 3. 사용자 데이터베이스를 사용자 아이디에 해당하는 해쉬키를 이름으로해서 생성한다.
		// Create User Database
		dbInstance.createDatabase("user_" + hashId.getHashId());

		// 4. 사용자 데이터베이스와 일레스틱 데이터베이스를 리버 연결한다.
		initEsDB("user_" + hashId.getHashId());
		
		// 5. 인증키를 생성하고 사용자와 매칭시킨다.
		// Create AccessKey
		AccessKey accessKey = new AccessKey();
		accessKey.setUserId("user_" + hashId.getHashId());
		accessKey.setValidFrom(ISO8601.LONG_LONG_AGO);
		accessKey.setValidTo(ISO8601.FAR_FAR_AWAY);
		accessKey.setValid(true);

		// Create hashIds Database
		accessKeys.createDatabaseIfNotExists();

		// Create Access Key
		accessKeys.create(accessKey);
	}
	
	public void initEsDB(String userDb){
		XContentBuilder builder = null;
		try {
			 builder = jsonBuilder()
				    .startObject()
				        .field("type", "couchdb")
				        .startObject("couchdb")
				        	.field("host", "localhost")
				        	.field("port", 5984)
				        	.field("db", userDb)
				        .endObject()	
				        .startObject("index")
				        	.field("index", userDb)
				        	.field("type", userDb)
				        	.field("bulk_size", "100")
				        	.field("bulk_timeout", "10ms")
				        .endObject()		
				    .endObject();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String json = null;
		try {
			json = builder.string();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Settings settings = ImmutableSettings
                .settingsBuilder()
                .put("cluster.name","elasticsearch_jangyoungjin")
                .build();
		
		Client client = new TransportClient(settings).addTransportAddress(new InetSocketTransportAddress("localhost",9300));
		
		IndexResponse response = client.prepareIndex("_river", userDb, "_meta")
                .setSource(json)
                .execute()
                .actionGet();
	}

	// @Test
	public void getUserHashIdFromAccessKey() throws MalformedURLException {
		CouchDbInstance dbInstance = createDbInstance();
		CouchDbConnector db = new StdCouchDbConnector(ACCESSKEYS_DB, dbInstance);
		AccessKey accessKey = db.get(AccessKey.class, "e309674c935107822fc5b15b8e0d648d");
		logger.info("" + accessKey.getUserId());
		logger.info("" + accessKey.getAccessKey());

		// 가져온 엑세스키에서 사용자 데이터베이스 이름을 가지고 요청을 처리한다.
		CouchDbConnector userDb = new StdCouchDbConnector(accessKey.getUserId(), dbInstance);

		// Dummy 생성
		SimpleRawData data = new SimpleRawData();
		userDb.create(data);
	}

	// Get CouchDbInstance
	private static CouchDbInstance createDbInstance() throws MalformedURLException {
		StdHttpClient httpClient = (StdHttpClient) new StdHttpClient.Builder().username(user).password(password)
				.url(url).connectionTimeout(100000).build();

		return new StdCouchDbInstance(httpClient);
	}

}
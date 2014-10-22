/**
 * 
 */
package org.soma.tleaf.accesskey;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.ektorp.CouchDbConnector;
import org.ektorp.CouchDbInstance;
import org.ektorp.ViewQuery;
import org.ektorp.ViewResult;
import org.ektorp.http.StdHttpClient;
import org.ektorp.impl.StdCouchDbConnector;
import org.ektorp.impl.StdCouchDbInstance;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soma.tleaf.domain.HashId;
import org.soma.tleaf.domain.SimpleData;
import org.soma.tleaf.domain.UserInfo;
import org.soma.tleaf.ektorptest.EktorpQueryTest;

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
	private static String USERS_DB = "users";
	private static String HASHIDS_DB = "hashIds";
	private static String ACCESSKEYS_DB = "accesskeys";
	

	static Logger logger = LoggerFactory.getLogger(AccessKeyTest.class);

	// Before start unit test we have to set configuration
	@BeforeClass
	public static void setup() throws Exception {
		Configuration config = new PropertiesConfiguration("couchdb.properties");
		url = config.getString("url");
		user = config.getString("id");
		password = config.getString("password");
	}

	@Test
	public void createUser() throws MalformedURLException {
		CouchDbInstance dbInstance = createDbInstance();
		CouchDbConnector userInfoDb = new StdCouchDbConnector(USERS_DB, dbInstance);
		CouchDbConnector hashIdDb = new StdCouchDbConnector(HASHIDS_DB, dbInstance);
		CouchDbConnector accessKeys = new StdCouchDbConnector(ACCESSKEYS_DB, dbInstance);

		// 1. 사용자 아이디를 생성한다.
		// Create User
		UserInfo userInfo = new UserInfo();
		userInfo.setEmail("swimyoung@gmail.com");
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
		hashId.setEmail("swimyoung@gmail.com");
		hashId.setHashId(userInfo.getHashId());

		// Create hashIds Database
		hashIdDb.createDatabaseIfNotExists();

		// Create User Document in Users DB
		hashIdDb.create(hashId);

		// 3. 사용자 데이터베이스를 사용자 아이디에 해당하는 해쉬키를 이름으로해서 생성한다.
		// Create User Database
		dbInstance.createDatabase("z" + hashId.getHashId());

		// 4. 인증키를 생성하고 사용자와 매칭시킨다.
		// Create AccessKey
		AccessKey accessKey = new AccessKey();
		accessKey.setUserId("z" + hashId.getHashId());
		accessKey.setValid(true);

		// Create hashIds Database
		accessKeys.createDatabaseIfNotExists();

		// Create Access Key
		accessKeys.create(accessKey);
	}

	//@Test
	public void getUserHashIdFromAccessKey() throws MalformedURLException {
		CouchDbInstance dbInstance = createDbInstance();
		CouchDbConnector db = new StdCouchDbConnector(ACCESSKEYS_DB, dbInstance);
		AccessKey accessKey = db.get(AccessKey.class, "e309674c935107822fc5b15b8e0d648d");
		logger.info("" + accessKey.getUserId());
		logger.info("" + accessKey.getAccessKey());
		
		// 가져온 엑세스키에서 사용자 데이터베이스 이름을 가지고 요청을 처리한다.
		CouchDbConnector userDb = new StdCouchDbConnector(accessKey.getUserId(), dbInstance);
		
		// Dummy 생성
		SimpleData data = new SimpleData();
		userDb.create(data);
	}

	// Get CouchDbInstance
	private static CouchDbInstance createDbInstance() throws MalformedURLException {
		StdHttpClient httpClient = (StdHttpClient) new StdHttpClient.Builder().username(user).password(password)
				.url(url).connectionTimeout(100000).build();

		return new StdCouchDbInstance(httpClient);
	}

}
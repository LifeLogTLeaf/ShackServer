package org.soma.tleaf.couchdb;

import javax.inject.Inject;

import org.ektorp.CouchDbConnector;
import org.ektorp.CouchDbInstance;
import org.ektorp.DocumentNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soma.tleaf.accesskey.AccessKey;
import org.soma.tleaf.accesskey.AccessKeyManager;
import org.soma.tleaf.domain.HashId;
import org.soma.tleaf.domain.UserInfo;
import org.soma.tleaf.esdb.EsdbConn;
import org.soma.tleaf.exception.CustomException;
import org.soma.tleaf.exception.CustomExceptionFactory;
import org.soma.tleaf.exception.CustomExceptionValue;

public class UserDaoImpl implements UserDao {

	@Inject
	private CouchDbConn couchDbConn;
	
	@Inject
	private AccessKeyManager accessKeyManager;

	@Inject
	private CustomExceptionFactory customExceptionFactory;
	
	@Inject
	private EsdbConn esdbConn;
	
	private CouchDbConnector couchDbConnector_hashid;
	private CouchDbConnector couchDbConnector_users;
	private CouchDbInstance couchDbInstance;

	private static final Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);
	
	/**
	 * 2014.10.15
	 */
	@Override
	public AccessKey userLogin(String email, String password) throws CustomException {

		HashId hashId;
		
		// DatabaseConnection Exception can be Thrown
		couchDbConnector_hashid = couchDbConn
				.getCouchDbConnetor("tleaf_hashid");

		couchDbConnector_users = couchDbConn
				.getCouchDbConnetor("tleaf_users");

		try {
			hashId = couchDbConnector_hashid.get(HashId.class, email);
		} catch ( DocumentNotFoundException e ) {
			e.printStackTrace();
			// This is the case where user isn't signed up
			throw customExceptionFactory.createCustomException( CustomExceptionValue.No_Such_User_Exception );
		}

		logger.info( "Email : " + email + "\nPassWord : " + password );
		
		UserInfo userInfo = couchDbConnector_users.get( UserInfo.class, hashId.getHashId().toString() );

		if ( password.equals( userInfo.getPassword() ) ) {
			return accessKeyManager.createAccessKey( userInfo.getHashId(), (long)86400000, true);
		}

		throw customExceptionFactory.createCustomException( CustomExceptionValue.Wrong_Authentication_Exception );
		//return "Your Password is Wrong";
	}

	/**
	 * 2014.10.16
	 * @throws CustomException 
	 */

	@Override
	public String userSignUp( UserInfo userInfo ) throws CustomException {

		// 1. Create User Database  2. Create User HashKey  3. Create UserInfo Document in User DB

		// DatabaseConnection Exception can be Thrown
		couchDbConnector_hashid = couchDbConn
				.getCouchDbConnetor("tleaf_hashid");

		couchDbConnector_users = couchDbConn
				.getCouchDbConnetor("tleaf_users");

		couchDbInstance = couchDbConn.getCouchDbInstance();

		// Checks if the E-mail already exists. ( HashId class has E-mail as the Document Id )
		if ( couchDbConnector_hashid.find( HashId.class, userInfo.getEmail() ) != null ) {
			throw customExceptionFactory.createCustomException( CustomExceptionValue.Email_Already_Exist_Exception );
		}

		couchDbConnector_users.create( userInfo );
		// create userinfo data
		// couchDb Automatically gives out _id ( hashId ), _rev to userInfo

		logger.info( userInfo.getEmail() + " User is Created. Hash ID is " + userInfo.getHashId() );

		HashId hashId = new HashId();
		hashId.setEmail( userInfo.getEmail() ); hashId.setHashId( userInfo.getHashId() );

		couchDbConnector_hashid.create( hashId );
		// makes mapping on email and user hashid

		couchDbInstance.createDatabase( "user_" + userInfo.getHashId() );
		// Because a database name should start with an letter

		/* Young edited */
		// elasticSearch DB create
		esdbConn.replication("user_" + userInfo.getHashId());
		
		return "{ \"signup\" : \"sucess\" , \"userId\" : \"" + userInfo.getHashId() + "\" }";
	}

	@Override
	public String userSignOut(String email, String pw) throws CustomException {
		// TODO Auto-generated method stub
		// 1. Check if email&pw is Correct.  2. if Correct, delete Hashid, Database, UserInfo

		// 1. check Account Info
		userLogin( email, pw );

		// DatabaseConnection Exception can be Thrown
		couchDbConnector_hashid = couchDbConn
				.getCouchDbConnetor("tleaf_hashid");

		couchDbConnector_users = couchDbConn
				.getCouchDbConnetor("tleaf_users");

		couchDbInstance = couchDbConn.getCouchDbInstance();


		HashId hashId = new HashId();
		UserInfo userInfo = new UserInfo();

		// 2. delete user data.
		try {

			hashId = couchDbConnector_hashid.get( HashId.class, email );
			userInfo = couchDbConnector_users.get( UserInfo.class, hashId.getHashId() );
			
			couchDbInstance.deleteDatabase( "user_" + userInfo.getHashId() );
			
			couchDbConnector_hashid.delete( hashId.getEmail(), hashId.getRev() );
			couchDbConnector_users.delete( userInfo.getHashId(), userInfo.getRev() );

		} catch ( Exception e ) {
			e.printStackTrace();
			return "Failed to Delete User Data";
		}

		return "{ \"signout\" : \"sucess\" , \"userId\" : \"" + userInfo.getHashId() + "\" }";
	}
	
	

}
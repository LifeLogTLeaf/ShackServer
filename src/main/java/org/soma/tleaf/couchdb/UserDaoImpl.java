package org.soma.tleaf.couchdb;

import javax.inject.Inject;

import org.ektorp.CouchDbConnector;
import org.ektorp.CouchDbInstance;
import org.ektorp.DocumentNotFoundException;
import org.soma.tleaf.domain.HashId;
import org.soma.tleaf.domain.UserInfo;
import org.soma.tleaf.exception.CustomException;
import org.soma.tleaf.exception.EmailAlreadyExistException;
import org.soma.tleaf.exception.NoSuchUserException;
import org.soma.tleaf.exception.WrongAuthenticationInfoException;

public class UserDaoImpl implements UserDao {

	@Inject
	private CouchDbConn couchDbConn;

	private CouchDbConnector couchDbConnector_hashid;
	private CouchDbConnector couchDbConnector_users;
	private CouchDbInstance couchDbInstance;

	/**
	 * 2014.10.15
	 */
	@Override
	public String userLogin(String email, String password) throws CustomException {

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
			throw new NoSuchUserException();
		}

		System.out.println(hashId.getHashId());

		UserInfo userInfo = couchDbConnector_users.get( UserInfo.class, hashId.getHashId().toString() );

		System.out.println(userInfo.getNickname() + "\n" + userInfo.getPassword() );

		if ( password.equals( userInfo.getPassword() ) ) {
			return "{ \"login\" : \"sucess\" , \"userId\" : \"" + userInfo.getHashId() + "\" }";
		}

		throw new WrongAuthenticationInfoException();
		//return "Your Password is Wrong";
	}

	/**
	 * 2014.10.16
	 * @throws CustomException 
	 */

	@Override
	public String userSignUp(String email, String pw, String nickname,
			String gender, Integer age) throws CustomException {

		// 1. Create User Database  2. Create User HashKey  3. Create UserInfo Document in User DB

		// DatabaseConnection Exception can be Thrown
		couchDbConnector_hashid = couchDbConn
				.getCouchDbConnetor("tleaf_hashid");

		couchDbConnector_users = couchDbConn
				.getCouchDbConnetor("tleaf_users");

		couchDbInstance = couchDbConn.getCouchDbInstance();

		// Checks if the E-mail already exists
		if ( couchDbConnector_users.find( UserInfo.class, email ) != null ) {
			throw new EmailAlreadyExistException();
		}

		UserInfo userInfo = new UserInfo();
		userInfo.setEmail(email); userInfo.setGender(gender);
		userInfo.setNickname(nickname); userInfo.setAge(age);
		userInfo.setPassword(pw);

		couchDbConnector_users.create( userInfo );
		// create userinfo data
		// couchDb Automatically gives out _id, _rev to userInfo

		System.out.println( userInfo.getHashId() );
		System.out.println( userInfo.getRev() );

		HashId hashId = new HashId();
		hashId.setEmail( email ); hashId.setHashId( userInfo.getHashId() );

		couchDbConnector_hashid.create( hashId );
		// makes mapping on email and user hashid

		couchDbInstance.createDatabase( "user_" + userInfo.getHashId() );
		// because a database name should start with an letter

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
			couchDbConnector_hashid.delete( hashId.getEmail(), hashId.getRev() );

			userInfo = couchDbConnector_users.get( UserInfo.class, hashId.getHashId() );
			couchDbConnector_users.delete( userInfo.getHashId(), userInfo.getRev() );

			couchDbInstance.deleteDatabase( "user_" + userInfo.getHashId() );

		} catch ( Exception e ) {
			e.printStackTrace();
			return "Failed to Delete User Data";
		}

		return "{ \"signout\" : \"sucess\" , \"userId\" : \"" + userInfo.getHashId() + "\" }";
	}

}
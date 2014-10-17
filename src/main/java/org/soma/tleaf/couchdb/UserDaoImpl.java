package org.soma.tleaf.couchdb;

import javax.inject.Inject;

import org.ektorp.CouchDbConnector;
import org.ektorp.CouchDbInstance;
import org.ektorp.DocumentNotFoundException;
import org.soma.tleaf.domain.HashId;
import org.soma.tleaf.domain.UserInfo;

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
	public String userLogin(String email, String password) {
		// 1. Create User Database  2. Create User HashKey  3. Create UserInfo Document in User DB

		HashId hashId;
		
		try {
			
			couchDbConnector_hashid = couchDbConn
					.getCouchDbConnetor("tleaf_hashid");
			
			couchDbConnector_users = couchDbConn
					.getCouchDbConnetor("tleaf_users");
			
		} catch (Exception e) {
			e.printStackTrace();
			return "Failed to Connect to User Database";
		}
		
		try {
			hashId = couchDbConnector_hashid.get(HashId.class, email);
		} catch ( DocumentNotFoundException e ) {
			e.printStackTrace();
			return "Email Doesn't Exist. Please sign up first";
		}
		
		System.out.println(hashId.getHashId());
		
		UserInfo userInfo = couchDbConnector_users.get( UserInfo.class, hashId.getHashId().toString() );
		
		System.out.println(userInfo.getNickname() + "\n" + userInfo.getPassword() );
		
		if ( password.equals( userInfo.getPassword() ) ) {
			return "Sucessfully Logged in"; // 홈 화면으로 리다이렉팅 해주면서 로그인 상태를 유지하는 방법을 생각해보자.
		}

		return "Your Password is Wrong";
	}
	
	/**
	 * 2014.10.16
	 */

	@Override
	public String userSignUp(String email, String pw, String nickname,
			String gender, Integer age) {

		// 1. Create User Database  2. Create User HashKey  3. Create UserInfo Document in User DB
		
		try {

			couchDbConnector_hashid = couchDbConn
					.getCouchDbConnetor("tleaf_hashid");
			
			couchDbConnector_users = couchDbConn
					.getCouchDbConnetor("tleaf_users");
			
			couchDbInstance = couchDbConn.getCouchDbInstance();
			
		} catch (Exception e) {
			e.printStackTrace();
			return "Failed to Connect to User Database";
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
		
		couchDbInstance.createDatabase( "a" + userInfo.getHashId() );
		// because a database name should start with an letter
		
		return "SignUp Successful";
	}

	@Override
	public String userSignOut(String email, String pw) {
		// TODO Auto-generated method stub
		// 1. Check if email&pw is Correct.  2. if Correct, delete Hashid, Database, UserInfo
		
		// 1. check Account Info
		if ( !userLogin( email, pw ).equals("Sucessfully Logged in") ) return "Account Info is Wrong";
		
		try {
			
			couchDbConnector_hashid = couchDbConn
					.getCouchDbConnetor("tleaf_hashid");
			
			couchDbConnector_users = couchDbConn
					.getCouchDbConnetor("tleaf_users");
			
			couchDbInstance = couchDbConn.getCouchDbInstance();
			
		} catch (Exception e) {
			e.printStackTrace();
			return "Failed to Connect to User Database";
		}
		
		HashId hashId = new HashId();
		UserInfo userInfo = new UserInfo();
		
		// 2. delete user data.
		try {
			
			hashId = couchDbConnector_hashid.get( HashId.class, email );
			couchDbConnector_hashid.delete( hashId.getEmail(), hashId.getRev() );
			
			userInfo = couchDbConnector_users.get( UserInfo.class, hashId.getHashId() );
			couchDbConnector_users.delete( userInfo.getHashId(), userInfo.getRev() );
			
			couchDbInstance.deleteDatabase( "a" + userInfo.getHashId() );
			
			
		} catch ( Exception e ) {
			e.printStackTrace();
			return "Failed to Delete User Data";
		}
		
		return "Completely deleted User data";
	}

}
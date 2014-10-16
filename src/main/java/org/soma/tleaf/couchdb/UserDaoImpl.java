package org.soma.tleaf.couchdb;

import javax.inject.Inject;

import org.ektorp.CouchDbConnector;
import org.ektorp.DocumentNotFoundException;
import org.soma.tleaf.domain.HashId;
import org.soma.tleaf.domain.UserInfo;

public class UserDaoImpl implements UserDao {

	@Inject
	private CouchDbConn couchDbConn;
	
	private CouchDbConnector couchDbConnector_hashid;
	private CouchDbConnector couchDbConnector_users;

	@Override
	public String userSignUp(String email, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String userLogin(String email, String password) {
		// TODO Auto-generated method stub

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

}
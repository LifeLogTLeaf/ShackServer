package org.soma.tleaf.couchdb;

import org.soma.tleaf.accesskey.AccessKey;
import org.soma.tleaf.exception.CustomException;

public interface UserDao {
	
	public String userSignUp( String email, String pw, String nickname, String gender, Integer age  ) throws CustomException;
	public AccessKey userLogin( String email, String password ) throws CustomException;
	public String userSignOut( String email, String pw ) throws CustomException;

	// Giving out Cookies
}
package org.soma.tleaf.couchdb;

public interface UserDao {
	
	public String userSignUp( String email, String pw, String nickname, String gender, Integer age  );
	public String userLogin( String email, String password );
	public String userSignOut( String email, String pw );
	
	// TODO userLogout, userSignOut.
	// Giving out Cookies and asdfasd
	
}

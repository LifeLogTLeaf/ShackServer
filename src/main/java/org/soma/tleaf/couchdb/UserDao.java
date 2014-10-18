package org.soma.tleaf.couchdb;

import javax.servlet.http.HttpServletResponse;

public interface UserDao {
	
	public String userSignUp( String email, String pw, String nickname, String gender, Integer age  );
	public String userLogin( String email, String password, HttpServletResponse response );
	public String userSignOut( String email, String pw, HttpServletResponse response );
	
	// TODO userLogout, userSignOut.
	// Giving out Cookies and asdfasd
	
}

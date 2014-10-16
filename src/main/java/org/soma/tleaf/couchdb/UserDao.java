package org.soma.tleaf.couchdb;

public interface UserDao {
	
	public String userSignUp( String email, String password );
	public String userLogin( String email, String password );
	
}

package org.soma.tleaf.exception;

public class AuthInfoInsufficientException extends CustomException {

	@Override
	public String getExceptionName() {
		return "Authorization Information is Insufficient";
	}
	
	@Override
	public String getMessage() {
		return "Authorization Info ( Access Token, User Id, Application Id ) is Missing";
	}

}

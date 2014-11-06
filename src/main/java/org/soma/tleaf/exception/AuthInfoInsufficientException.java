package org.soma.tleaf.exception;

public class AuthInfoInsufficientException extends CustomException {

	/**
	 * added Because it Causes build error from Jenkins
	 */
	private static final long serialVersionUID = 78808580436465289L;

	@Override
	public String getExceptionName() {
		return "Authorization Information is Insufficient";
	}
	
	@Override
	public String getMessage() {
		return "Authorization Info ( Access Token, User Id, Application Id ) is Missing";
	}

}

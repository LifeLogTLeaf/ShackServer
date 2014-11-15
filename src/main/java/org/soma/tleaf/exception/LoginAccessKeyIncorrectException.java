package org.soma.tleaf.exception;

public class LoginAccessKeyIncorrectException extends CustomException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5775834336304109892L;

	@Override
	public String getExceptionName() {
		return "Login AccessKey Incorrect Exception";
	}
	
	@Override
	public String getMessage() {
		return "Your Login AccessKey and ApplicationId Does not match.";
	}

}

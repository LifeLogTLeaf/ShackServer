package org.soma.tleaf.exception;

public class LoginAccessCodeNotFoundException extends CustomException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2685388565170045865L;

	@Override
	public String getExceptionName() {
		return "Login Access Code Not Found";
	}
	
	@Override
	public String getMessage() {
		return "Your Login Access Code was not found. Maybe login took too long. Try again";
	}

}

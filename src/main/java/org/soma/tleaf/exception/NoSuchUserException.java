package org.soma.tleaf.exception;

public class NoSuchUserException extends CustomException {

	/**
	 * added Because it Causes build error from Jenkins
	 */
	private static final long serialVersionUID = 5461729020625263338L;

	@Override
	public String getMessage() {
		return "There is No User with the Email. Check again, Or sign up first";
	}
	
	@Override
	public String getExceptionName() {
		return "There is No Such User with the Email";
	}

}

package org.soma.tleaf.exception;

public class DatabaseConnectionException extends CustomException {

	/**
	 * added Because it Causes build error from Jenkins 
	 */
	private static final long serialVersionUID = 9195570305241172930L;

	@Override
	public String getMessage() {
		return "Failed to Connect to User Database";
	}
	
	@Override
	public String getExceptionName() {
		return "DatabaseConnectionException";
	}

}

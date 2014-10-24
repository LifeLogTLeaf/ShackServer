package org.soma.tleaf.exception;

public class DatabaseConnectionException extends CustomException {

	@Override
	public String getMessage() {
		return "Failed to Connect to User Database";
	}
	
	@Override
	public String getExceptionName() {
		return "DatabaseConnectionException";
	}

}

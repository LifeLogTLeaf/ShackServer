package org.soma.tleaf.exception;

public class ParameterInsufficientException extends CustomException {

	@Override
	public String getExceptionName() {
		return "ParameterInsufficientException";
	}
	
	@Override
	public String getMessage() {
		return "Parameters Included in Servlet Request isn't enough";
	}

}

package org.soma.tleaf.exception;

public class ParameterInsufficientException extends CustomException {

	/**
	 * added Because it Causes build error from Jenkins
	 */
	private static final long serialVersionUID = 2053030261276967431L;

	@Override
	public String getExceptionName() {
		return "ParameterInsufficientException";
	}
	
	@Override
	public String getMessage() {
		return "Parameters Included in Servlet Request isn't enough";
	}

}

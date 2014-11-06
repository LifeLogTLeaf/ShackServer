package org.soma.tleaf.exception;

public class EmailAlreadyExistException extends CustomException {

	/**
	 * added Because it Causes build error from Jenkins
	 */
	private static final long serialVersionUID = 8849078958711993216L;

	@Override
	public String getMessage() {
		return "Email Already Exists. Try an another Email or Find out your account";
	}
	
	@Override
	public String getExceptionName() {
		return "Email Already Exists";
	}

}

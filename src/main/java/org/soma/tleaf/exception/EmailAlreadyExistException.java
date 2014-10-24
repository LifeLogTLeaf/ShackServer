package org.soma.tleaf.exception;

public class EmailAlreadyExistException extends CustomException {

	@Override
	public String getMessage() {
		return "Email Already Exists. Try an another Email or Find out your account";
	}
	
	@Override
	public String getExceptionName() {
		return "Email Already Exists";
	}

}

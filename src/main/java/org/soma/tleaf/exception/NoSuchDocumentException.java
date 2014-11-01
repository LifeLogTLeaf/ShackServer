package org.soma.tleaf.exception;

public class NoSuchDocumentException extends CustomException {

	@Override
	public String getExceptionName() {
		return "No Such Document was Found";
	}
	
	@Override
	public String getMessage() {
		return "No Documnet matching the Given information was Found";
	}

}

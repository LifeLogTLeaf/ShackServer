package org.soma.tleaf.exception;

public class NoSuchDocumentException extends CustomException {

	/**
	 * added Because it Causes build error from Jenkins
	 */
	private static final long serialVersionUID = -5954539943375904334L;

	@Override
	public String getExceptionName() {
		return "No Such Document was Found";
	}
	
	@Override
	public String getMessage() {
		return "No Documnet matching the Given information was Found";
	}

}

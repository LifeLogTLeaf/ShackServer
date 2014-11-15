package org.soma.tleaf.exception;

public class InvalidAppIdException extends CustomException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7351183059363040630L;

	@Override
	public String getExceptionName() {
		return "Invalid Application Id Exception";
	}
	
	@Override
	public String getMessage() {
		return "Your Application Id doesn't Exist. Register your Application First on TLeaf";
	}

}

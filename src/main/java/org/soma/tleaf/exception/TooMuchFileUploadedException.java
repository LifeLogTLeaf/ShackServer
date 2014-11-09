package org.soma.tleaf.exception;

public class TooMuchFileUploadedException extends CustomException {

	private static final long serialVersionUID = 3708439195427965903L;

	@Override
	public String getExceptionName() {
		return "TooMuchFileUploadedException";
	}
	
	@Override
	public String getMessage() {
		return "There were too much file uploads at once. Seperate request";
	}

}

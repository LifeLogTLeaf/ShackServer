package org.soma.tleaf.exception;

public class JsonDataMappingException extends CustomException {

	/**
	 * added Because it Causes build error from Jenkins
	 */
	private static final long serialVersionUID = -2890710414703169324L;

	@Override
	public String getMessage() {
		return "Sent Json Data Doesn't match with the Server's need";
	}
	
	@Override
	public String getExceptionName() {
		return "Json Data Mapping Exception";
	}

}

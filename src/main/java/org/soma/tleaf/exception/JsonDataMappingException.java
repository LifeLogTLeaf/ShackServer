package org.soma.tleaf.exception;

public class JsonDataMappingException extends CustomException {

	@Override
	public String getMessage() {
		return "Sent Json Data Doesn't match with the Server's need";
	}
	
	@Override
	public String getExceptionName() {
		return "Json Data Mapping Exception";
	}

}

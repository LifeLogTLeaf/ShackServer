package org.soma.tleaf.exception;
/**
 * Thrown if users ID, PASSWORD is wrong
 * @author susu
 * Date : Oct 23, 2014 5:10:23 PM
 */
public class WrongAuthenticationInfoException extends CustomException {

	/**
	 * added Because it Causes build error from Jenkins
	 */
	private static final long serialVersionUID = -7452287261410045916L;

	@Override
	public String getExceptionName() {
		return "WrongAuthenticationInfoException Error";
	}
	
	@Override
	public String getMessage() {
		return "Id or Password is Incorrect";
	}
}

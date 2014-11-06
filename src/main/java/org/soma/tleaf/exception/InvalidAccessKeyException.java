/**
 * 
 */
package org.soma.tleaf.exception;

/**
 * Created with Eclipse IDE
 * Author : RichardJ
 * Date : Oct 22, 2014 10:50:45 AM
 * Description : 유요하지 않은 인증키에 관한 예외처리 입니다.
 */
public class InvalidAccessKeyException extends CustomException {
	/**
	 * added Because it Causes build error from Jenkins
	 */
	private static final long serialVersionUID = -53763364833949039L;

	@Override
	public String getMessage() {
		return "accessKey is not valid";
	}

	@Override
	public String getExceptionName() {
		return "InvalidAccessKey error";
	}

}

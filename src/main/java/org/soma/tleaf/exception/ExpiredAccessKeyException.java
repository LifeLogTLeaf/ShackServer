/**
 * 
 */
package org.soma.tleaf.exception;

/**
 * Created with Eclipse IDE
 * Author : RichardJ
 * Date : Oct 22, 2014 10:57:30 AM
 * Description : 인증키 파기에 대한 예외처리 클래스입니다. 
 */

public class ExpiredAccessKeyException extends CustomException {
	@Override
	public String getMessage() {
		return "accessKey is expired";
	}

	@Override
	public String getExceptionName() {
		return "ExpiredAccessKey error";
	}
}

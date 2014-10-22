/**
 * 
 */
package org.soma.tleaf.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created with Eclipse IDE
 * Author : RichardJ 
 * Date   : Oct 22, 2014 7:39:58 PM
 * Description : 요청처리 도중 발생한 에러를 저장하는 클래스입니다.
 */
public class ErrorResponse {
	private String error;
	private String reason;
	
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
}

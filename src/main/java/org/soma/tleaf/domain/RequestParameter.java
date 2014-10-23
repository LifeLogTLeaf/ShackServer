/**
 * 
 */
package org.soma.tleaf.domain;

/**
 * Created with Eclipse IDE
 * Author : RichardJ 
 * Date   : Oct 22, 2014 8:25:29 AM
 * Description : 클라이언트로 부터 받은 요청에 관한 파라미터를 저장하는 클래스입니다.
 */
public class RequestParameter {
	private String AccessKey;
	private String startKey;
	private String endKey;
	private String documentId;
	private String appId;
	private String userHashId;
	private String limit;
	
	
	public String getAccessKey() {
		return AccessKey;
	}
	public void setAccessKey(String accessKey) {
		AccessKey = accessKey;
	}
	public String getStartKey() {
		return startKey;
	}
	public void setStartKey(String startKey) {
		this.startKey = startKey;
	}
	public String getEndKey() {
		return endKey;
	}
	public void setEndKey(String endKey) {
		this.endKey = endKey;
	}
	public String getDocumentId() {
		return documentId;
	}
	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getUserHashId() {
		return userHashId;
	}
	public void setUserHashId(String userHashId) {
		this.userHashId = userHashId;
	}
	public String getLimit() {
		return limit;
	}
	public void setLimit(String limit) {
		this.limit = limit;
	}
	
}

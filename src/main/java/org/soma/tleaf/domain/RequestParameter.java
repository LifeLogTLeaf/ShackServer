/**
 * 
 */
package org.soma.tleaf.domain;

import java.util.Map;

/**
 * Created with Eclipse IDE
 * Author : RichardJ 
 * Date   : Oct 22, 2014 8:25:29 AM
 * Description : 클라이언트로 부터 받은 요청에 관한 파라미터를 저장하는 클래스입니다.
 */
public class RequestParameter {
	private String startKey;
	private String endKey;
	private String documentId;
	private String documentRev;
	private String appId;
	private String userHashId;
	private String limit;
	private boolean descend;
	
	private Map<String, Object> serviceData;

	public Map<String, Object> getserviceData() {
		return serviceData;
	}
	public void setData(Map<String, Object> serviceData) {
		this.serviceData = serviceData;
	}
	public String getDocumentRev() {
		return documentRev;
	}
	public void setDocumentRev(String documentRev) {
		this.documentRev = documentRev;
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
	public boolean isDescend() {
		return descend;
	}
	public void setDescend(boolean descend) {
		this.descend = descend;
	}
	
}

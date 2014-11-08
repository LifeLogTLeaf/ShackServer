/**
 * 
 */
package org.soma.tleaf.domain;

import java.util.Map;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created with Eclipse IDE
 * Author : RichardJ
 * Date : Oct 17, 2014 1:32:05 PM
 * Description : 카우치데이터베이스에 저장되는 도큐먼트 형식을 표현하는 클래스입니다.
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class RawData {
	
	@JsonProperty("_id")
	private String id;
	
	@JsonProperty("_rev")
	private String revision;
	
	@JsonProperty("time")
	private String time;
	
	@JsonProperty("app_id")
	private String appId;
	
	@JsonProperty("user_id")
	private String userId;
	
	@JsonProperty("data")
	private Map<String, Object> data;
	
	private String attachmentId;
	private String attachmentType;

	public String getAttachmentType() {
		return attachmentType;
	}
	public void setAttachmentType(String attachmentType) {
		this.attachmentType = attachmentType;
	}
	public String getAttachmentId() {
		return attachmentId;
	}
	public void setAttachmentId(String attachmentId) {
		this.attachmentId = attachmentId;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRevision() {
		return revision;
	}
	public void setRevision(String revision) {
		this.revision = revision;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Map<String, Object> getData() {
		return data;
	}
	public void setData(Map<String, Object> data) {
		this.data = data;
	}
}

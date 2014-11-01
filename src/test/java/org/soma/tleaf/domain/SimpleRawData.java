/**
 * 
 */
package org.soma.tleaf.domain;

import java.util.Map;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created with Eclipse IDE
 * Author : RichardJ 
 * Date   : Oct 30, 2014 8:23:32 PM
 * Description : 
 */
public class SimpleRawData {
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

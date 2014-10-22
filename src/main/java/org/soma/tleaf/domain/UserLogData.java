/**
 * 
 */
package org.soma.tleaf.domain;

import java.util.Map;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created with Eclipse IDE
 * Author : RichardJ
 * Date : Oct 17, 2014 1:32:05 PM
 * Description :
 */
public class UserLogData {
	@JsonProperty("_id")
	private String id;
	@JsonProperty("_rev")
	private String revision;
	private long time;
	private Map<String, Object> data;

	@Override
	public String toString() {
		return "id : " + id + ", " + "rev : " + revision + ", " + "time : " + time;
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

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}
}

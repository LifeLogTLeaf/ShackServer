/**
 * 
 */
package org.soma.tleaf.domain;

import java.util.ArrayList;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created with Eclipse IDE
 * Author : RichardJ
 * Date : Oct 16, 2014 12:25:00 PM
 * Description :
 */

public class SimpleData {
	@JsonProperty("_id")
	private String id;
	@JsonProperty("_rev")
	private String revision;
	private String time;
	private String type;
	
	public SimpleData() {
		super();
	}

	/**
	 * @param time
	 * @param type
	 * @param appAuthor
	 */
	public SimpleData(String time, String type, String appAuthor, ArrayList<Object> datas) {
		super();
		this.time = time;
		this.type = type;
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

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "SimpelData {" +
				"id = " + id +
				", rev = " + revision +
				", time = "+ time
				;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}

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
	private long time;
	private String type;
	private String appAuthor;
	private ArrayList<Object> datas;
	
	public SimpleData() {
		super();
	}

	/**
	 * @param time
	 * @param type
	 * @param appAuthor
	 */
	public SimpleData(long time, String type, String appAuthor, ArrayList<Object> datas) {
		super();
		this.time = time;
		this.type = type;
		this.appAuthor = appAuthor;
		this.datas = datas;
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
				", time = "+ time +
				", type = "+ type +
				", appAuthor = "+ appAuthor +
				"}";
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAppAuthor() {
		return appAuthor;
	}

	public void setAppAuthor(String appAuthor) {
		this.appAuthor = appAuthor;
	}

	public ArrayList<Object> getDatas() {
		return datas;
	}

	public void setDatas(ArrayList<Object> datas) {
		this.datas = datas;
	}

}

/**
 * 
 */
package org.soma.tleaf.domain;

import java.util.Map;

/**
 * Created with Eclipse IDE
 * Author : RichardJ 
 * Date   : Oct 17, 2014 10:57:29 PM
 * Description : 
 */
public class ResponseDataWrapper {
	private String version;
	private Map<String,Object> data;
	
	// just for test
	@Override
	public String toString() {
		return "version : " + version + ", " + "data : " + data.size();
	}
	
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public Map<String,Object> getData() {
		return data;
	}
	public void setData(Map<String,Object> data) {
		this.data = data;
	}
	
}

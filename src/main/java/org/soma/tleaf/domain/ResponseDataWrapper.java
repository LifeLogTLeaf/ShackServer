/**
 * 
 */
package org.soma.tleaf.domain;

import java.util.List;

/**
 * Created with Eclipse IDE
 * Author : RichardJ 
 * Date   : Oct 17, 2014 10:57:29 PM
 * Description : 클라이언트에 데이터를전달할때 사용하는 클래스입니다. 
 */
public class ResponseDataWrapper {
	private String version = "1.0.0";
	private List<RawData> logs;
	
	// just for test
	@Override
	public String toString() {
		return "version : " + version + ", " + "logs : " + logs.size();
	}
	
	public String getVersion() {
		return version;
	}

	public List<RawData> getLogs() {
		return logs;
	}
	public void setLogs(List<RawData> logs) {
		this.logs = logs;
	}
	
}

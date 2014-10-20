/**
 * 
 */
package org.soma.tleaf.domain;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with Eclipse IDE
 * Author : RichardJ 
 * Date   : Oct 17, 2014 9:01:57 PM
 * Description : 
 */
public class RequestDataWrapper {
	private HashMap<String, Object> serviceData;
	private Map<String, Object> dummyMap;

	public HashMap<String, Object> getserviceData() {
		return serviceData;
	}

	public void setData(HashMap<String, Object> serviceData) {
		this.serviceData = serviceData;
	}	
	
	public int checkSize(){
		if(serviceData !=null) return serviceData.size();
		else return -1;
	}

	public Map<String, Object> getDummyMap() {
		return dummyMap;
	}

	public void setDummyMap(Map<String, Object> dummyMap) {
		this.dummyMap = dummyMap;
	}
}

/**
 * 
 */
package org.soma.tleaf.domain;

import java.util.HashMap;

/**
 * Created with Eclipse IDE
 * Author : RichardJ 
 * Date   : Oct 17, 2014 9:01:57 PM
 * Description : 클라이언트로부터 요청받은 데이터를 저장하는 클래스입니다.
 */
public class RequestDataWrapper {
	private HashMap<String, Object> serviceData;

	public HashMap<String, Object> getserviceData() {
		return serviceData;
	}

	public void setData(HashMap<String, Object> serviceData) {
		this.serviceData = serviceData;
	}	

}

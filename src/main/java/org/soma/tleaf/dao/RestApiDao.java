/**
 * 
 */
package org.soma.tleaf.dao;

import java.util.List;

import org.soma.tleaf.accesskey.AccessKey;
import org.soma.tleaf.domain.RequestParameter;
import org.soma.tleaf.domain.RawData;

/**
 * Created with Eclipse IDE
 * Author : RichardJ
 * Date : Oct 17, 2014 6:14:13 PM
 * Description :
 */
public interface RestApiDao {
	public String postData(RawData rawData, RequestParameter param) throws Exception;
	public RawData getData(RequestParameter param);
	public AccessKey checkAccessKey(String param) throws Exception;
	public List<RawData> getAllData(RequestParameter param) throws Exception;
	public List<RawData> getAllDataFromAppId(RequestParameter param) throws Exception;
}

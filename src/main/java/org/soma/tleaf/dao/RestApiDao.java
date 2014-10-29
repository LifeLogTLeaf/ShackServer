/**
 * 
 */
package org.soma.tleaf.dao;

import java.util.List;

import org.soma.tleaf.domain.RawData;
import org.soma.tleaf.domain.RequestParameter;

/**
 * Created with Eclipse IDE
 * Author : RichardJ
 * Date : Oct 17, 2014 6:14:13 PM
 * Description :
 */
public interface RestApiDao {
	public String postData(RawData rawData, RequestParameter param) throws Exception;
	public RawData getData(RequestParameter param);
	public List<RawData> getAllData(RequestParameter param) throws Exception;
	public List<RawData> getAllDataFromAppId(RequestParameter param) throws Exception;
}

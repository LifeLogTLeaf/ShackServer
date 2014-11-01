/**
 * 
 */
package org.soma.tleaf.dao;

import java.util.List;
import java.util.Map;

import org.soma.tleaf.domain.RawData;
import org.soma.tleaf.domain.RequestParameter;
import org.soma.tleaf.domain.UserInfo;
import org.soma.tleaf.exception.CustomException;

/**
 * Created with Eclipse IDE
 * Author : RichardJ
 * Date : Oct 17, 2014 6:14:13 PM
 * Description :
 */
public interface RestApiDao {
	public void postData(Map<String,Object> result, RawData rawData) throws Exception;
	public void deleteData(Map<String,Object> result, RawData rawData ) throws Exception;
	public void updateData(Map<String,Object> result, RawData rawData ) throws Exception;
	public RawData getData(RequestParameter param);
	public List<RawData> getAllData(RequestParameter param) throws Exception;
	public List<RawData> getAllDataFromAppId(RequestParameter param) throws Exception;
	public UserInfo getUserInfo( String userId ) throws CustomException;
}

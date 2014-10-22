/**
 * 
 */
package org.soma.tleaf.service;

import org.soma.tleaf.domain.RequestDataWrapper;
import org.soma.tleaf.domain.RequestParameter;
import org.soma.tleaf.domain.ResponseDataWrapper;
import org.soma.tleaf.domain.RawData;

/**
 * Created with Eclipse IDE
 * Author : RichardJ
 * Date : Oct 17, 2014 6:12:49 PM
 * Description :
 */
public interface RestApiService {
	public ResponseDataWrapper postUserData(RequestDataWrapper dataWrapper, RequestParameter param) throws Exception;
	public ResponseDataWrapper getUserData(RequestParameter param);
	
}

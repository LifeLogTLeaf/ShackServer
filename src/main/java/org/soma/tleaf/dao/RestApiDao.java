/**
 * 
 */
package org.soma.tleaf.dao;

import org.soma.tleaf.domain.UserLogData;

/**
 * Created with Eclipse IDE
 * Author : RichardJ
 * Date : Oct 17, 2014 6:14:13 PM
 * Description :
 */
public interface RestApiDao {
	public void postData(UserLogData userLogData);
	public UserLogData getData(String documentId);

}

package org.soma.tleaf.accesskey;

import org.soma.tleaf.exception.DatabaseConnectionException;
import org.soma.tleaf.exception.InvalidAccessKeyException;

/**
 * 2014.10.17
 * @author susu
 * returns inValid AccessKey if exceptions are made
 */
public interface AccessKeyManager {
	
	public boolean isAccessKeyValid ( String accessKey, String appId, String userId ) throws InvalidAccessKeyException,DatabaseConnectionException;
	
	// Makes access Keys. If String is in wrong format, it returns an invalid accessKey.
	public AccessKey createAccessKey ( String userId, String vaildFrom, String vaildTo, boolean isValid ) throws DatabaseConnectionException ;
	public AccessKey createAccessKey ( String userId, String vaildFrom, Long vaildForMillis, boolean isValid ) throws DatabaseConnectionException ;
	public AccessKey createAccessKey ( String userId, String vaildTo, boolean isValid ) throws DatabaseConnectionException ;
	public AccessKey createAccessKey ( String userId, Long vaildForMillis, boolean isValid ) throws DatabaseConnectionException ;

}
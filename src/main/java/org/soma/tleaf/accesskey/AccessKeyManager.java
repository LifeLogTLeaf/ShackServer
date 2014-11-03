package org.soma.tleaf.accesskey;

import org.soma.tleaf.exception.DatabaseConnectionException;
import org.soma.tleaf.exception.InvalidAccessKeyException;

/**
 * 2014.10.17
 * @author susu
 * returns inValid AccessKey if exceptions are made
 */
public interface AccessKeyManager {
	
	/** Checks If the Given AccessKey and UserId, AppId Matches. Also checks if the Token Expired at the Time
	 * 
	 * @author susu
	 * Date Nov 3, 2014 4:27:12 PM
	 * @param accessKey
	 * @param appId
	 * @param userId
	 * @return boolean True if Valid and Usable, False if inVaild and Unusable
	 * @throws InvalidAccessKeyException If AccessKey doesn't exist
	 * @throws DatabaseConnectionException If Failed to Connect to the Database
	 */
	public boolean isAccessKeyValid ( String accessKey, String appId, String userId ) throws InvalidAccessKeyException,DatabaseConnectionException;
	
	// Makes access Keys. If String is in wrong format, it returns an invalid accessKey	
	public AccessKey createAccessKey ( String userId, Long vaildForMillis, boolean isValid ) throws DatabaseConnectionException ;
	public AccessKey createAccessKey ( String userId, String vaildFrom, String vaildTo, boolean isValid, String appId ) throws DatabaseConnectionException ;
	public AccessKey createAccessKey ( String userId, String vaildFrom, Long vaildForMillis, boolean isValid, String appId ) throws DatabaseConnectionException ;
	public AccessKey createAccessKey ( String userId, String vaildTo, boolean isValid, String appId ) throws DatabaseConnectionException ;
	
	/** Creates an Access Token Valid from the time of the Method Call, Lasting For given Milliseconds
	 * 
	 * @author susu
	 * Date Nov 3, 2014 4:29:11 PM
	 * @param userId
	 * @param vaildForMillis
	 * @param isValid
	 * @param appId
	 * @return AccessKey AccessKey Object
	 * @throws DatabaseConnectionException
	 */
	public AccessKey createAccessKey ( String userId, Long vaildForMillis, boolean isValid, String appId ) throws DatabaseConnectionException ;

}
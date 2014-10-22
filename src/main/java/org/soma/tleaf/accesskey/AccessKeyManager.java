package org.soma.tleaf.accesskey;

/**
 * 2014.10.17
 * @author susu
 * returns inValid AccessK
 */
public interface AccessKeyManager {
	
	public boolean isAccessKeyValid ( String accessKey, String userId );
	
	public AccessKey createAccessKey ( String userId, String vaildFrom, String vaildTo, boolean isValid );
	public AccessKey createAccessKey ( String userId, String vaildFrom, Long vaildForMillis, boolean isValid );
	public AccessKey createAccessKey ( String userId, String vaildTo, boolean isValid );
	public AccessKey createAccessKey ( String userId, Long vaildForMillis, boolean isValid );

}
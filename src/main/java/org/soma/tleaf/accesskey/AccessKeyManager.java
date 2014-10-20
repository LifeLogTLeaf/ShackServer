package org.soma.tleaf.accesskey;

/**
 * 2014.10.17
 * @author susu
 * returns inValid AccessK
 */
public interface AccessKeyManager {
	
	public boolean isAccessKeyValid ( String accessKey, String userId );
	
	public AccessKey createAccessKey ( String userId, String vaildFrom, String vaildTo, boolean isVaild );
	public AccessKey createAccessKey ( String userId, String vaildFrom, Long vaildForMillis );
	public AccessKey createAccessKey ( String userId, String vaildTo );
	public AccessKey createAccessKey ( String userId, Long vaildForMillis );

}
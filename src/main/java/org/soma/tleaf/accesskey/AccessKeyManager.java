package org.soma.tleaf.accesskey;

/**
 * 2014.10.17
 * @author susu
 *
 */
public interface AccessKeyManager {
	
	public String isAccessKeyValid ( String accessKey, String hashId );
	
	public AccessKey createAccessKey ( String apiKey, String vaildFrom, String vaildTo );
	public AccessKey createAccessKey ( String apiKey, String vaildFrom, Long vaildFor );
	public AccessKey createAccessKey ( String apiKey, String vaildTo );
	public AccessKey createAccessKey ( String apiKey, Long vaildFor );
	
	
}
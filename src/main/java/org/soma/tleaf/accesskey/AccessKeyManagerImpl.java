package org.soma.tleaf.accesskey;

/**
 * 2014.10.17
 * @author susu
 *
 */
public class AccessKeyManagerImpl implements AccessKeyManager {

	@Override
	public String isAccessKeyValid(String accessKey, String hashId) {
		// TODO Check if the accessKey is Correct
		return null;
	}

	@Override
	public AccessKey createAccessKey(String apiKey, String vaildFrom,
			String vaildTo, String Enabled) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AccessKey createAccessKey(String apiKey, String vaildFrom,
			Long vaildFor, String Enabled) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AccessKey createAccessKey(String apiKey, String vaildTo,
			String Enabled) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AccessKey createAccessKey(String apiKey, Long vaildFor,
			String Enabled) {
		// TODO Auto-generated method stub
		return null;
	}

}
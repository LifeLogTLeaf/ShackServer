package org.soma.tleaf.accesskey;

import java.text.ParseException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.inject.Inject;

import org.ektorp.CouchDbConnector;
import org.soma.tleaf.couchdb.CouchDbConn;
import org.soma.tleaf.util.ISO8601;

/**
 * 2014.10.17
 * @author susu
 *
 */
public class AccessKeyManagerImpl implements AccessKeyManager {
	
	@Inject
	private CouchDbConn couchDbConn;
	
	private CouchDbConnector couchDbConnector_apikey;
	
	final String API_KEY_DB_NAME = "tleaf_apikey";
	
	public AccessKeyManagerImpl () throws Exception {
		// couchDbConnector_apikey = couchDbConn.getCouchDbConnetor( API_KEY_DB_NAME );
		// 연결이 되지 않을 경우 서버 내부 오류로 통보하고, 잠시 후에 다시 시도하는 것으로 해보자 ㅠㅠㅠㅠㅠㅠ
	}
	
	@Override
	public boolean isAccessKeyValid(String accessKey, String userId) {
		AccessKey tmpAccessKey = couchDbConnector_apikey.get( AccessKey.class, accessKey );
		
		// Checks if the Access Key is Valid, including times
		return tmpAccessKey.isValid( userId );
	}

	@Override
	public AccessKey createAccessKey(String userId, String validFrom,
			String validTo, boolean isValid) {
		
		AccessKey accessKey = new AccessKey();
		
		accessKey.setUserId(userId);
		accessKey.setValidFrom(validFrom);
		accessKey.setValidTo(validTo);
		accessKey.setValid(isValid);
		
		couchDbConnector_apikey.create(accessKey);
		
		return accessKey;
	}

	@Override
	public AccessKey createAccessKey(String userId, String validFrom,
			Long validForMillis, boolean isValid) {

		Calendar calendar;
		
		try {
			calendar = ISO8601.toCalendar( validFrom );
		} catch (ParseException e) {
			e.printStackTrace();
			return createAccessKey( userId, validFrom, ISO8601.LONG_LONG_AGO, false );
		}
		
		calendar.setTimeInMillis( calendar.getTimeInMillis() + validForMillis );
		
		return createAccessKey( userId, validFrom, ISO8601.fromCalendar(calendar), isValid );
	}

	@Override
	public AccessKey createAccessKey(String userId, String validTo, boolean isValid) {
		return createAccessKey( userId, ISO8601.now(), validTo, isValid );
	}

	@Override
	public AccessKey createAccessKey(String userId, Long validForMillis, boolean isValid) {
		
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTimeInMillis( calendar.getTimeInMillis() + validForMillis );
		
		return createAccessKey( userId, ISO8601.now(), ISO8601.fromCalendar(calendar), isValid );
	}

}

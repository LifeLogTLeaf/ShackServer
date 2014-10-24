package org.soma.tleaf.accesskey;

import java.text.ParseException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.inject.Inject;

import org.ektorp.CouchDbConnector;
import org.ektorp.DocumentNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soma.tleaf.couchdb.CouchDbConn;
import org.soma.tleaf.exception.DatabaseConnectionException;
import org.soma.tleaf.exception.InvalidAccessKeyException;
import org.soma.tleaf.util.ISO8601;
import org.soma.tleaf.util.OauthFilter;

/**
 * 2014.10.17
 * @author susu
 *
 */
public final class AccessKeyManagerImpl implements AccessKeyManager {
	
	@Inject
	private CouchDbConn couchDbConn;

	private static Logger logger = LoggerFactory.getLogger(AccessKeyManagerImpl.class);
	
	private CouchDbConnector couchDbConnector_apikey = null;
	private final String API_KEY_DB_NAME = "tleaf_apikey";
	
	public synchronized void setCouchDbConnector () throws DatabaseConnectionException {
		if( couchDbConnector_apikey == null ) {
			couchDbConnector_apikey = couchDbConn.getCouchDbConnetor(API_KEY_DB_NAME);
		}
	}
	
	@Override
	public boolean isAccessKeyValid(String accessKey, String appId, String userId) throws InvalidAccessKeyException, DatabaseConnectionException {

		setCouchDbConnector();
		AccessKey tmpAccessKey;
		try {
			tmpAccessKey = couchDbConnector_apikey.get( AccessKey.class, accessKey );
		} catch( DocumentNotFoundException e ) {
			e.printStackTrace();
			throw new InvalidAccessKeyException();
		}
		
		// Checks if the Access Key is Valid, including times
		if ( tmpAccessKey.isValid( userId, appId ) )
			return true;
		else 
			throw new InvalidAccessKeyException();
	}

	@Override
	public AccessKey createAccessKey(String userId, String validFrom,
			String validTo, boolean isValid) throws DatabaseConnectionException {
		
		AccessKey accessKey = new AccessKey();
		
		accessKey.setUserId(userId);
		accessKey.setValidFrom(validFrom);
		accessKey.setValidTo(validTo);
		accessKey.setValid(isValid);
		
		setCouchDbConnector();
		couchDbConnector_apikey.create(accessKey);
		
		return accessKey;
	}

	@Override
	public AccessKey createAccessKey(String userId, String validFrom,
			Long validForMillis, boolean isValid) throws DatabaseConnectionException {

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
	public AccessKey createAccessKey(String userId, String validTo, boolean isValid) throws DatabaseConnectionException {
		return createAccessKey( userId, ISO8601.now(), validTo, isValid );
	}

	@Override
	public AccessKey createAccessKey(String userId, Long validForMillis, boolean isValid) throws DatabaseConnectionException {
		
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTimeInMillis( calendar.getTimeInMillis() + validForMillis );
		
		return createAccessKey( userId, ISO8601.now(), ISO8601.fromCalendar(calendar), isValid );
	}

}

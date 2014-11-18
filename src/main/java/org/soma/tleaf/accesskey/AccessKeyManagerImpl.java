package org.soma.tleaf.accesskey;

import java.text.ParseException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.inject.Inject;

import org.ektorp.CouchDbConnector;
import org.ektorp.DocumentNotFoundException;
import org.ektorp.Revision;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soma.tleaf.couchdb.CouchDbConn;
import org.soma.tleaf.dao.OauthDao;
import org.soma.tleaf.exception.CustomException;
import org.soma.tleaf.exception.CustomExceptionFactory;
import org.soma.tleaf.exception.CustomExceptionValue;
import org.soma.tleaf.exception.DatabaseConnectionException;
import org.soma.tleaf.exception.InvalidAccessKeyException;
import org.soma.tleaf.redis.RedisCache;
import org.soma.tleaf.util.ISO8601;

/**
 * Deals with CRUD of AccessKeys
 * 2014.10.17
 * @author susu
 */
public class AccessKeyManagerImpl implements AccessKeyManager, OauthManager {
	
	@Inject
	private CouchDbConn couchDbConn;
	
	@Inject
	private OauthDao oauthDao;
	
	@Inject
	private CustomExceptionFactory customExceptionFactory;
	
	@Inject
	private RedisCache redisCache;

	private static Logger logger = LoggerFactory.getLogger(AccessKeyManagerImpl.class);
	
	private CouchDbConnector couchDbConnector_apikey = null;
	private final String API_KEY_DB_NAME = "tleaf_apikey";
	
	public synchronized void setCouchDbConnector () throws DatabaseConnectionException {
		if( couchDbConnector_apikey == null ) {
			couchDbConnector_apikey = couchDbConn.getCouchDbConnetor(API_KEY_DB_NAME);
		}
	}
	
	@Override
	public boolean isAccessKeyValid( AccessKey accessKey ) throws InvalidAccessKeyException, DatabaseConnectionException {

		setCouchDbConnector();
		AccessKey tmpAccessKey;
		try {
			tmpAccessKey = couchDbConnector_apikey.get( AccessKey.class, accessKey.getAccessKey() );
		} catch( DocumentNotFoundException e ) {
			e.printStackTrace();
			throw new InvalidAccessKeyException();
		}
		
		// Checks if the Access Key is Valid, including times
		if ( tmpAccessKey.isValid( accessKey ) )
			return true;
		else 
			return false;
	}

	@Override
	public boolean isAccessKeyValid(String accessKey, String appId,
			String userId) throws InvalidAccessKeyException, DatabaseConnectionException {
		
		AccessKey tmp = new AccessKey(); tmp.setAccessKey(accessKey); tmp.setAppId(appId); tmp.setUserId(userId);
		
		return isAccessKeyValid(tmp);
	}

	@Override
	public AccessKey createAccessKey(String userId, String validFrom,
			String validTo, boolean isValid, String appId) throws DatabaseConnectionException {
		
		AccessKey accessKey = new AccessKey();
		
		accessKey.setUserId(userId);
		accessKey.setAppId(appId);
		accessKey.setValidFrom(validFrom);
		accessKey.setValidTo(validTo);
		accessKey.setValid(isValid);
		
		setCouchDbConnector();
		couchDbConnector_apikey.create(accessKey);
		
		logger.info( userId + " User, " + appId + " Access Token Created" );
		
		return accessKey;
	}
	
	@Override
	public AccessKey createAccessKey(String userId, Long vaildForMillis,
			boolean isValid) throws DatabaseConnectionException {
		return createAccessKey( userId, vaildForMillis, isValid, "shack" );
	}

	@Override
	public AccessKey createAccessKey(String userId, String validFrom,
			Long validForMillis, boolean isValid, String appId) throws DatabaseConnectionException {

		Calendar calendar;
		
		try {
			calendar = ISO8601.toCalendar( validFrom );
		} catch (ParseException e) {
			e.printStackTrace();
			return createAccessKey( userId, validFrom, ISO8601.LONG_LONG_AGO, false, appId );
		}
		
		calendar.setTimeInMillis( calendar.getTimeInMillis() + validForMillis );
		
		return createAccessKey( userId, validFrom, ISO8601.fromCalendar(calendar), isValid, appId );
	}

	@Override
	public AccessKey createAccessKey(String userId, String validTo, boolean isValid, String appId) throws DatabaseConnectionException {
		return createAccessKey( userId, ISO8601.now(), validTo, isValid, appId );
	}

	@Override
	public AccessKey createAccessKey(String userId, Long validForMillis, boolean isValid, String appId) throws DatabaseConnectionException {
		
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTimeInMillis( calendar.getTimeInMillis() + validForMillis );
		
		return createAccessKey( userId, ISO8601.now(), ISO8601.fromCalendar(calendar), isValid ,appId );
	}

	@Override
	public void deleteAccessKey( String accessKey, String revision ) {
		
		List<Revision> revList = couchDbConnector_apikey.getRevisions(accessKey);
		
		for( Revision rev : revList ) {
			if ( rev.isOnDisk() )
				couchDbConnector_apikey.delete( accessKey, rev.getRev() );
		}
		
		logger.info("Deleted Access Token " + accessKey );
	}

	@Override
	public AccessKey findAccessKey(String accessKey) throws DatabaseConnectionException {
		
		setCouchDbConnector();
		
		try {
			return couchDbConnector_apikey.get( AccessKey.class, accessKey );
		} catch ( DocumentNotFoundException e ) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String createLoginAccessCode(String appId) {
		String rnd = String.valueOf( (int)( Math.random() * 10000000 ) );
		redisCache.put(rnd, appId); // User has to login within 5 minutes
		return rnd;
	}

	@Override
	public boolean checkLoginAccessCode(String accessCode ,String appId) throws CustomException {
		
		String tmp = (String)redisCache.get( accessCode ).get();
		if ( tmp == null ) {
			throw customExceptionFactory.createCustomException( CustomExceptionValue.Login_Access_Code_Not_Found_Exception );
		}
		else if ( appId.matches(tmp) ) {
			redisCache.delete( accessCode );
			return true;
		}
		
		throw customExceptionFactory.createCustomException( CustomExceptionValue.Login_Access_Code_Incorrect_Exception );
	}

	@Override
	public boolean isAppIdValid(String appId) throws DatabaseConnectionException {
		return oauthDao.isAppIdValid(appId);
	}

}
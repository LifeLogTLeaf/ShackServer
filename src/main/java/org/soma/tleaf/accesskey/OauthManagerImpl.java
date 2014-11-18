package org.soma.tleaf.accesskey;

import javax.inject.Inject;

import org.soma.tleaf.dao.OauthDao;
import org.soma.tleaf.exception.CustomException;
import org.soma.tleaf.exception.CustomExceptionFactory;
import org.soma.tleaf.exception.CustomExceptionValue;
import org.soma.tleaf.exception.DatabaseConnectionException;
import org.soma.tleaf.redis.LoginRedisCache;

public class OauthManagerImpl implements OauthManager{

	@Inject
	private OauthDao oauthDao;
	
	@Inject
	private CustomExceptionFactory customExceptionFactory;
	
	@Inject
	private LoginRedisCache redisCache;
	
	@Override
	public boolean isAppIdValid(String appId)
			throws DatabaseConnectionException {
		return oauthDao.isAppIdValid(appId);
	}

	@Override
	public String createLoginAccessCode(String appId) {
		String rnd = String.valueOf( (int)( Math.random() * 10000000 ) );
		redisCache.put(rnd, appId); // User has to login within 5 minutes
		return rnd;
	}

	@Override
	public boolean checkLoginAccessCode(String accessCode, String appId)
			throws CustomException {
		
		String tmp;
		
		try{
			tmp = (String) redisCache.get( accessCode ).get();
		} catch( NullPointerException e ) {
			e.printStackTrace();
			return false;
		}
		
		if ( tmp == null ) {
			throw customExceptionFactory.createCustomException( CustomExceptionValue.Login_Access_Code_Not_Found_Exception );
		}
		else if ( appId.matches( tmp ) ) {
			redisCache.delete( accessCode );
			return true;
		}
		
		throw customExceptionFactory.createCustomException( CustomExceptionValue.Login_Access_Code_Incorrect_Exception );
	}

}

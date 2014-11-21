package org.soma.tleaf.redis;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soma.tleaf.accesskey.AccessKey;
import org.soma.tleaf.accesskey.AccessKeyManager;
import org.soma.tleaf.exception.DatabaseConnectionException;
import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisConnectionException;

import com.google.gson.Gson;

/**
 * Customized actual Cache that We will use.
 * @author susu
 * Date : Nov 12, 2014 12:32:37 PM
 */
public class RedisCache implements Cache {

	private String name;
	private int expireSeconds;

	@Inject
	private AccessKeyManager accessKeyManager;

	private static Jedis jedis;

	@Inject
	private JedisPool jedisPool;
	
	private static final Logger logger = LoggerFactory.getLogger(RedisCache.class);
	private static Gson gson = new Gson();

	private static final int VAILD_FOR_SEC = 300; // 5 min
	private static final String CACHE_NAME = "accesskey";

	public RedisCache () {
		this ( CACHE_NAME , VAILD_FOR_SEC );
	}

	public RedisCache ( String name, int expireSeconds ) {

		this.name = name;
		this.expireSeconds = expireSeconds;

	}

	public boolean isAccessKeyValid ( String accessKey, String appId, String userId ) {

		AccessKey accessKey_server = (AccessKey) get ( accessKey ).get();

		// if accessKey was not found
		if( accessKey_server == null ) {
			return false;
		}

		return accessKey_server.isValid(accessKey, appId, userId);

	}
	
	/**
	 * For MultiThreading. To use Jedis from Pool
	 * @author susu
	 * Date Nov 17, 2014 10:45:13 PM
	 */
	private void setJedisFromPool() {
		jedis = jedisPool.getResource();
	}
	
	/**
	 * Returning Resource
	 * @author susu
	 * Date Nov 17, 2014 11:24:26 PM
	 */
//	private void returnResource () {
//		jedisPool.returnResource(jedis);
//	}

	/**
	 * Deletes AccessKey in the Cache
	 * @author susu
	 * Date Nov 12, 2014
	 * @param key String
	 */
	@Override
	public void evict(Object key) {
		setJedisFromPool();

		try { 
			jedis.del( (String)key );
			logger.info("Successfully deleted AccessKey");
		} catch ( JedisConnectionException e ) {

			logger.info("LoginRedisCache evict Method JedisConnectionException");

			// returnBrokenResource when the state of the object is unrecoverable
			if (null != jedis) {
				jedisPool.returnBrokenResource(jedis);
				jedis = null;
			}

		} finally {
			/// ... it's important to return the Jedis instance to the pool once you've finished using it
			if (null != jedis)
				jedisPool.returnResource(jedis);
		}
	}
	public void delete( String key ) {
		evict ( key );
	}

	/**
	 * Gets AccessKey with String key
	 * @author susu
	 * Date Nov 12, 2014
	 * @param key String id
	 * @return ValueWrapper If AccessKey wasn't found in both Redis and Couch ( or Failed to Connect to DB ), return null
	 */
	@Override
	public ValueWrapper get(Object key) {
		setJedisFromPool();

		logger.info("retriving Value by Key...");

		String accessKeyString = null; AccessKey accessKey = null;
			
		try {
			accessKeyString = jedis.get( (String)key );
		} catch ( JedisConnectionException e ) {

			logger.info("RedisCache get Method JedisConnectionException");

			// returnBrokenResource when the state of the object is unrecoverable
			if (null != jedis) {
				jedisPool.returnBrokenResource(jedis);
				jedis = null;
			}
			
		} finally {
			/// ... it's important to return the Jedis instance to the pool once you've finished using it
			if (null != jedis)
				jedisPool.returnResource(jedis);
		}
		
		if ( accessKeyString == null ) { 

			logger.info("There was no AccessKey matching the Key in Redis. Searching in CouchDB...");

			try {
				accessKey = accessKeyManager.findAccessKey( (String) key );
			} catch (DatabaseConnectionException e) {
				e.printStackTrace();
				return new SimpleValueWrapper(null);
			}
			if ( accessKey == null ) { /*returnResource();*/ return new SimpleValueWrapper(null); }

			// Put it back again in redis.
			put ( accessKey.getAccessKey() , accessKey );
		}
		else {

			logger.info("Found Key in Redis. Mapping.");
			accessKey = gson.fromJson(accessKeyString, AccessKey.class);
		}
		
		return new SimpleValueWrapper( accessKey );
	}

	/**
	 * Save AccesKey into Redis Cache
	 * @author susu
	 * Date Nov 12, 2014
	 * @param key String id
	 * @param value AccessKey Object
	 */
	@Override
	public void put(Object key, Object value) {
		setJedisFromPool();

		logger.info("Inserting Value...");

		if ( !(key instanceof String) || !(value instanceof AccessKey) ) {
			logger.info("Parameters are not Appropriate");
			return ;
		}

		String sKey = (String)key;
		AccessKey accessKey = (AccessKey)value;
		String accessKeyString = gson.toJson(accessKey);
		
		try{
			jedis.setex( sKey, expireSeconds, accessKeyString );
		} catch ( JedisConnectionException e ) {

			logger.info("RedisCache get Method JedisConnectionException");

			// returnBrokenResource when the state of the object is unrecoverable
			if (null != jedis) {
				jedisPool.returnBrokenResource(jedis);
				jedis = null;
			}
		} finally {
			/// ... it's important to return the Jedis instance to the pool once you've finished using it
			if (null != jedis)
				jedisPool.returnResource(jedis);
		}
	}

	/**
	 * Deletes all data in the Cache
	 * @author susu
	 * Date Nov 12, 2014
	 */
	@Override
	public void clear() {
		setJedisFromPool();
		try{

			jedis.flushAll();

		} catch( JedisConnectionException e ) {

			logger.info("RedisCache clear Method JedisConnectionException");

			// returnBrokenResource when the state of the object is unrecoverable
			if (null != jedis) {
				jedisPool.returnBrokenResource(jedis);
				jedis = null;
			}

		} finally {
			/// ... it's important to return the Jedis instance to the pool once you've finished using it
			if (null != jedis)
				jedisPool.returnResource(jedis);
		}
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Object getNativeCache() {
		return jedis;
	}

}

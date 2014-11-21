package org.soma.tleaf.redis;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisConnectionException;

public class LoginRedisCache implements Cache {

	private String name;
	private int expireSeconds;

	private static Jedis jedis;

	@Inject
	private JedisPool jedisPool;

	private static final Logger logger = LoggerFactory.getLogger(LoginRedisCache.class);

	private static final int VAILD_FOR_SEC = 300; // 5 min
	private static final String CACHE_NAME = "login";

	public LoginRedisCache () {
		this ( CACHE_NAME , VAILD_FOR_SEC );
	}

	public LoginRedisCache ( String name, int expireSeconds ) {

		this.name = name;
		this.expireSeconds = expireSeconds;

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
	 * Returning Resource to the Pool
	 * @author susu
	 * Date Nov 17, 2014 11:24:26 PM
	 */
//	private void returnResource () {
//		jedisPool.returnResource(jedis);
//	}

	@Override
	public void clear() {
		setJedisFromPool();
		try{

			jedis.flushAll();

		} catch( JedisConnectionException e ) {

			logger.info("LoginRedisCache clear Method JedisConnectionException");

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
	public void evict(Object key) {

		if ( !(key instanceof String) ) {
			logger.info("Parameters are not Appropriate");
			return ;
		}

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

	@Override
	public ValueWrapper get(Object key) {

		if ( !(key instanceof String) ) {
			logger.info("Parameters are not Appropriate");
			return new SimpleValueWrapper(null);
		}

		setJedisFromPool();

		String tmp;
		try {
			tmp = jedis.get( (String)key );
		} catch ( JedisConnectionException e ) {

			logger.info("LoginRedisCache get Method JedisConnectionException");

			// returnBrokenResource when the state of the object is unrecoverable
			if (null != jedis) {
				jedisPool.returnBrokenResource(jedis);
				jedis = null;
			}
			return new SimpleValueWrapper(null);
		} finally {
			/// ... it's important to return the Jedis instance to the pool once you've finished using it
			if (null != jedis)
				jedisPool.returnResource(jedis);
		}

		return new SimpleValueWrapper( tmp );
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Object getNativeCache() {
		return jedis;
	}

	@Override
	public void put(Object key, Object value) {

		logger.info("Inserting Value...");

		if ( !(key instanceof String) || !(value instanceof String) ) {
			logger.info("Parameters are not Appropriate");
			return ;
		}

		setJedisFromPool();

		try{
			jedis.setex( (String)key, expireSeconds, (String) value );
		} catch ( JedisConnectionException e ) {

			logger.info("LoginRedisCache get Method JedisConnectionException");

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

}

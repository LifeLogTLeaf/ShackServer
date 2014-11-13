package org.soma.tleaf.redis;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soma.tleaf.accesskey.AccessKey;
import org.soma.tleaf.accesskey.AccessKeyManager;
import org.soma.tleaf.configuration.WebAppConfig;
import org.soma.tleaf.exception.DatabaseConnectionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { WebAppConfig.class })
public class RedisTest {

	@Autowired
	private RedisTemplate<String, AccessKey> redisTemplate;
	
	@Autowired
	private RedisCache redisCache;
	
	@Autowired
	private AccessKeyManager accessKeyManager;
    
    private static final String userId = "344bc889c8bb44dd6e4bb845d40007b9";
    private static final String appId = "6b22f647ef8f2f3278a1322d8b000f81";
    
    private static final Logger logger = LoggerFactory.getLogger( RedisTest.class );
    
    /*
    @BeforeClass
    public static void beforeRedisTest () {
    	// Check Injection
    	Assert.assertNotNull(redisTemplate);
    	Assert.assertNotNull(redisCache);
    	Assert.assertNotNull(accessKeyManager);
    }
    */
    
    /*
    @AfterClass
    public static void afterRedisTest () {
    	redisCache.clear();
    }
    */
    
    @Test
    public void redisTest () {
    	
    	// Check Injection
    	Assert.assertNotNull(redisTemplate);
    	Assert.assertNotNull(redisCache);
    	Assert.assertNotNull(accessKeyManager);
    	
    	logger.info("Creating AccessKey Object in Redis");
    	
    	// Create AccessKey Object in Redis
    	AccessKey accessKey = createDummyAccessKey();
    	redisCache.put( accessKey.getAccessKey(), accessKey );
    	logger.info( "Created AccessKey " + accessKey.getAccessKey() );
    	
    	// Retriving AccessKey Object
    	AccessKey retrivedAccessKey = (AccessKey) redisCache.get( accessKey.getAccessKey() );
    	logger.info("Retrivied AccessKey Object. Now Checking");
    	logger.info( "Original " + accessKey.getAccessKey() + "Fetched " + retrivedAccessKey.getAccessKey() );
    	logger.info( "Original " + accessKey.getAppId() + "Fetched " + retrivedAccessKey.getAppId() );
    	logger.info( "Original " + accessKey.getUserId() + "Fetched " + retrivedAccessKey.getUserId() );
    	
    	// Delete AccessKey Object
    	logger.info( "Deleting AccessKey " + retrivedAccessKey.getAccessKey() );
    	redisCache.delete( retrivedAccessKey.getAccessKey() );
    	
    	// clear all data
    	redisCache.clear();
    }
    
    public AccessKey createDummyAccessKey () {
    	
    	try {
			return accessKeyManager.createAccessKey(userId, (long)86400000, true, appId);
		} catch (DatabaseConnectionException e) {
			e.printStackTrace();
			return null;
		}

    }
	
}

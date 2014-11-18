package org.soma.tleaf.configuration;

import org.soma.tleaf.redis.LoginRedisCache;
import org.soma.tleaf.redis.RedisCache;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
@ComponentScan(basePackages = {"org.soma.tleaf"})
@PropertySource("classpath:redis.properties")
public class RedisConfig {
	
	private @Value("${redis.host-name}") String redisHostName;
	private @Value("${redis.port}") int redisPort;
	
	@Bean
	public JedisPool jedisPool() {
		JedisPoolConfig config = new JedisPoolConfig();
		
		// Maximum active connections to Redis instance
		config.setMaxIdle(20);
        // Tests whether connection is dead when connection
        // retrieval method is called
		config.setTestOnBorrow(true);
        /* Some extra configuration */
        // Tests whether connection is dead when returning a connection to the pool
		config.setTestOnReturn(true);
        // Number of connections to Redis that just sit there and do nothing
        config.setMaxIdle(6);
        // Minimum number of idle connections to Redis These can be seen as always open and ready to serve
        config.setMinIdle(3);
        // Tests whether connections are dead during idle periods
        config.setTestWhileIdle(true);
        // Maximum number of connections to test in each idle check
        config.setNumTestsPerEvictionRun(20);
        // Idle connection checking period
        config.setTimeBetweenEvictionRunsMillis(60000);
        // Create the jedisPool
        return new JedisPool( config, redisHostName, redisPort);
	}
	
	@Bean
	public RedisCache redisCache () {
		return new RedisCache();
	}
	@Bean
	public LoginRedisCache loginRedisCache () {
		return new LoginRedisCache();
	}

}

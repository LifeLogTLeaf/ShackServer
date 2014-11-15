package org.soma.tleaf.configuration;

import org.soma.tleaf.redis.RedisCache;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import redis.clients.jedis.Jedis;

@Configuration
@ComponentScan(basePackages = {"org.soma.tleaf"})
@PropertySource("classpath:redis.properties")
public class RedisConfig {
	
	private @Value("${redis.host-name}") String redisHostName;
	private @Value("${redis.port}") int redisPort;
	
	@Bean
	public RedisCache redisCache () {
		return new RedisCache( jedis() );
	}
	
	@Bean
	public Jedis jedis () {
		return new Jedis( redisHostName, redisPort );
	}

}

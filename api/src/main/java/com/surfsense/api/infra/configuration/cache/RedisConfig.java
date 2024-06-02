package com.surfsense.api.infra.configuration.cache;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
@EnableCaching
public class RedisConfig {
  @Bean
  RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
    RedisTemplate<Object, Object> template = new RedisTemplate<>();
    template.setConnectionFactory(connectionFactory);
    return template;
  }

  @Bean
  JedisPool jedisPool(@Value("${spring.data.redis.port}") String port, @Value("${spring.data.redis.host}") String host, @Value("${spring.data.redis.password}") String password) {
    final JedisPoolConfig poolConfig = buildPoolConfig();
    return new JedisPool(poolConfig, host, Integer.valueOf(port), 0, password);
  }

  private JedisPoolConfig buildPoolConfig() {
    final JedisPoolConfig poolConfig = new JedisPoolConfig();
    poolConfig.setJmxEnabled(false);
    return poolConfig;
  }
}

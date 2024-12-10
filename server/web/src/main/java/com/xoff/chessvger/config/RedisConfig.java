package com.xoff.chessvger.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.GenericToStringSerializer;


@ComponentScan("com.xoff.chessvger.config")
@Configuration
public class RedisConfig {

  @Bean
  JedisConnectionFactory jedisConnectionFactory() {
    RedisStandaloneConfiguration
        redisStandaloneConfiguration = new RedisStandaloneConfiguration("redis", 6379);
    return new JedisConnectionFactory(redisStandaloneConfiguration);

  }

  @Bean
  public RedisTemplate<String, Object> redisTemplate() {
    final RedisTemplate<String, Object> template = new RedisTemplate<>();
    template.setConnectionFactory(jedisConnectionFactory());
    template.setValueSerializer(new GenericToStringSerializer<>(Object.class));
    return template;
  }
  @Bean
  MessageListenerAdapter messageListener() {
    return new MessageListenerAdapter(new RedisMessageSubscriber());
  }


  @Bean
  RedisMessageListenerContainer redisContainer() {
    final RedisMessageListenerContainer container = new RedisMessageListenerContainer();
    container.setConnectionFactory(jedisConnectionFactory());
    //container.addMessageListener(messageListener(), topic());
    return container;
  }

  @Bean
  MessagePublisher redisPublisher() {
    return new RedisMessagePublisher(redisTemplate(), topic());
  }

  @Bean
  ChannelTopic topic() {
    return new ChannelTopic("pubsub:queue");
  }
}
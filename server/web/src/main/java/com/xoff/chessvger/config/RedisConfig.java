package com.xoff.chessvger.config;


import com.xoff.chessvger.util.Topic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
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
    //container.addMessageListener(messageListener(), topicPlayer());
    return container;
  }

  @Bean
  MessagePublisher redisPublisherPlayer() {
    return new RedisMessagePublisherPlayer(redisTemplate(), topicPlayer());
  }

  @Bean
  ChannelTopic topicPlayer() {
    return new ChannelTopic(Topic.TOPIC_PLAYER);
  }
  @Bean
  MessagePublisher redisPublisherGame() {
    return new RedisMessagePublisherPlayer(redisTemplate(), topicGame());
  }

  @Bean
  ChannelTopic topicGame() {
    return new ChannelTopic(Topic.TOPIC_GAME);
  }
}
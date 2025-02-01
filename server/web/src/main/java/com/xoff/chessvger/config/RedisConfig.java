package com.xoff.chessvger.config;


import com.xoff.chessvger.topic.Topic;
import com.xoff.chessvger.service.ApiService;
import com.xoff.chessvger.service.RedisMessageSubscriber;
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
import org.springframework.web.client.RestTemplate;


@ComponentScan("com.xoff.chessvger.config")
@Configuration
public class RedisConfig {

  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }

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
    return new MessageListenerAdapter(new RedisMessageSubscriber(new ApiService(restTemplate())));
  }


  @Bean
  RedisMessageListenerContainer redisContainer() {
    final RedisMessageListenerContainer container = new RedisMessageListenerContainer();
    container.setConnectionFactory(jedisConnectionFactory());
    container.addMessageListener(messageListener(), topicFromQueue());
    return container;
  }

  @Bean
  MessagePublisher redisMessageReceiverFromParser() {
    return new RedisMessageReceiver(redisTemplate(), topicFromQueue());
  }

  @Bean
  ChannelTopic topicFromQueue() {
    return new ChannelTopic(Topic.TOPIC_FROM_QUEUE);
  }

  @Bean
  MessagePublisher redisMessagePublisherToParser() {
    return new RedisMessageReceiver(redisTemplate(), topicToQueue());
  }

  @Bean
  ChannelTopic topicToQueue() {
    return new ChannelTopic(Topic.TOPIC_TO_QUEUE);
  }
}
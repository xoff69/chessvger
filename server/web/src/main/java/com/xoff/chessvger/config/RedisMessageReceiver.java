package com.xoff.chessvger.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

@Service
public class RedisMessageReceiver implements MessagePublisher {

  @Autowired
  private RedisTemplate<String, Object> redisTemplate;
  @Autowired
  private ChannelTopic topicFromQueue;

  public RedisMessageReceiver() {
  }

  public RedisMessageReceiver(
      RedisTemplate<String, Object> redisTemplate, ChannelTopic topic) {
    this.redisTemplate = redisTemplate;
    this.topicFromQueue = topic;
  }

  public void publish(String message) {
    redisTemplate.convertAndSend(topicFromQueue.getTopic(), message);
  }
}
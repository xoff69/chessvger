package com.xoff.chessvger.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

@Service
public class RedisMessagePublisher implements MessagePublisher {

  @Autowired
  private RedisTemplate<String, Object> redisTemplate;
  @Autowired
  private ChannelTopic topicToQueue;

  public RedisMessagePublisher() {
  }

  public RedisMessagePublisher(
      RedisTemplate<String, Object> redisTemplate, ChannelTopic topic) {
    this.redisTemplate = redisTemplate;
    this.topicToQueue = topic;
  }

  public void publish(String message) {
    redisTemplate.convertAndSend(topicToQueue.getTopic(), message);
  }
}
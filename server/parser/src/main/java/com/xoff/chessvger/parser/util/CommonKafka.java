package com.xoff.chessvger.queues.util;

import com.xoff.chessvger.queues.Main;
import java.util.Collections;
import java.util.Properties;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

public class CommonKafka {



  public static KafkaConsumer getConsumer(String topic, String groupId) {
    System.out.println("consumer kafka " + topic);
    Properties properties = new Properties();
    properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, Main.getKafkaHost());
    properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
        StringDeserializer.class.getName());
    properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
        StringDeserializer.class.getName());
    properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupId);
    properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
    // create consumer
    KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);
    // subscribe consumer to our topic(s)
    consumer.subscribe(Collections.singletonList(topic));
    System.out.println("<<<consumer kafka " + topic);
    return consumer;
  }

  public static Producer getProducer() {
    System.out.println("getProducer kafka ");
    try {
      Properties properties = new Properties();
      properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, Main.getKafkaHost());
      properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
      properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
      System.out.println("AppProducerPlayer avant !");
      Producer<String, String> producer = new KafkaProducer<>(properties);
      System.out.println("AppProducerPlayer getProducer!" + producer);
      return producer;
    } catch (Exception e) {
      System.out.println("erre " + e.getMessage());
      e.printStackTrace();
    }
    return null;
  }
}

package com.xoff.chessvger.queues.gameofplayer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xoff.chessvger.queues.util.CommonKafka;
import com.xoff.chessvger.queues.util.KafkaConstants;
import java.time.Duration;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

public class GameOfAPlayerConsumer implements Runnable {

  @Override
  public void run() {
    System.out.println("GameOfAPlayerConsumer started");
    KafkaConsumer consumer =
        CommonKafka.getConsumer(KafkaConstants.TOPIC_GAMEOFAPLAYER, "xoff-gameofaplayer");

    GameOfAPlayerDao gameOfAPlayerDao = new GameOfAPlayerDao();
    ObjectMapper objectMapper = new ObjectMapper();

    while (true) {
      ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));

      for (ConsumerRecord<String, String> record : records) {

        try {
          GameOfAPlayer gameOfAPlayer = objectMapper.readValue(record.value(), GameOfAPlayer.class);
          //gameOfAPlayerDao.insertEntity(gameOfAPlayer);

        } catch (JsonProcessingException e) {
          throw new RuntimeException(e);
        }

      }
    }
  }
}
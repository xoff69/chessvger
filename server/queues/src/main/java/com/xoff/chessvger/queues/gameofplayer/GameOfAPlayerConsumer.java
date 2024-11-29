package com.xoff.chessvger.queues.gameofplayer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xoff.chessvger.queues.util.CommonKafka;
import com.xoff.chessvger.queues.util.KafkaConstants;
import com.xoff.chessvger.queues.util.Runner;
import java.sql.SQLException;
import java.time.Duration;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

@Slf4j
public class GameOfAPlayerConsumer implements Runner {

  @Override
  public void run() {
    log.info("GameOfAPlayerConsumer started");
    KafkaConsumer consumer =
        CommonKafka.getConsumer(KafkaConstants.TOPIC_GAMEOFAPLAYER, "xoff-material");

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
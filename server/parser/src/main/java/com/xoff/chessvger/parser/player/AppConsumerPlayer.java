package com.xoff.chessvger.queues.player;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xoff.chessvger.queues.util.CommonKafka;
import com.xoff.chessvger.queues.util.KafkaConstants;
import java.sql.SQLException;
import java.time.Duration;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

public class AppConsumerPlayer implements Runnable {
  public void run() {

    KafkaConsumer consumer = CommonKafka.getConsumer(KafkaConstants.TOPIC_PLAYER, "xoff-player");

    CommonPlayerDao commonPlayerDao = new CommonPlayerDao();
    ObjectMapper objectMapper = new ObjectMapper();

    while (true) {
      ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));

      for (ConsumerRecord<String, String> record : records) {

        try {
          CommonPlayer player = objectMapper.readValue(record.value(), CommonPlayer.class);

          commonPlayerDao.insertCommonPlayer(player);

        } catch (JsonProcessingException | ClassNotFoundException | SQLException e) {
          throw new RuntimeException(e);
        }

      }
    }
  }
}

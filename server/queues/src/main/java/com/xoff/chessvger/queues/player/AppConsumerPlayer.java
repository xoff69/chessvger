package com.xoff.chessvger.queues.player;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xoff.chessvger.queues.util.KafkaConstants;
import java.sql.SQLException;
import java.time.Duration;
import com.xoff.chessvger.queues.util.CommonKafka;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
@Slf4j
public class AppConsumerPlayer {
  public static void runAppConsumerPlayer() {
log.info("start AppConsumerPlayer");
    KafkaConsumer consumer= CommonKafka.getConsumer(KafkaConstants.TOPIC_PLAYER,"xoff-player");

    CommonPlayerDao commonPlayerDao=new CommonPlayerDao();
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

package com.xoff.chessvger.queues.position;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xoff.chessvger.queues.util.CommonKafka;
import com.xoff.chessvger.queues.util.KafkaConstants;
import java.sql.SQLException;
import java.time.Duration;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;


public class PositionConsumer implements Runnable {
  public void run() {

    KafkaConsumer consumer =
        CommonKafka.getConsumer(KafkaConstants.TOPIC_POSITION, "xoff-position");

    PositionDao positionDao = new PositionDao();
    ObjectMapper objectMapper = new ObjectMapper();

    while (true) {
      ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));

      for (ConsumerRecord<String, String> record : records) {

        try {
          PositionEntity positionEntity =
              objectMapper.readValue(record.value(), PositionEntity.class);
       //   positionDao.insertEntity(positionEntity);

        } catch (JsonProcessingException  e) {
          throw new RuntimeException(e);
        }

      }
    }
  }
}

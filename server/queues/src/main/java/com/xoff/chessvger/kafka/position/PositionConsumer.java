package com.xoff.chessvger.kafka.position;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xoff.chessvger.chess.board.CoupleZobristMaterial;
import com.xoff.chessvger.kafka.util.CommonKafka;
import com.xoff.chessvger.kafka.util.KafkaConstants;
import java.sql.SQLException;
import java.time.Duration;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

public class PositionConsumer {
  public static void main(String[] args) {

    KafkaConsumer consumer= CommonKafka.getConsumer(KafkaConstants.TOPIC_POSITION,"xoff-position");

    PositionDao positionDao=new PositionDao();
    ObjectMapper objectMapper = new ObjectMapper();

    while (true) {
      ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));

      for (ConsumerRecord<String, String> record : records) {

        try {
          PositionEntity positionEntity = objectMapper.readValue(record.value(), PositionEntity.class);
          positionDao.insertEntity(positionEntity);

        } catch (JsonProcessingException | SQLException e) {
          throw new RuntimeException(e);
        }

      }
    }
  }
}

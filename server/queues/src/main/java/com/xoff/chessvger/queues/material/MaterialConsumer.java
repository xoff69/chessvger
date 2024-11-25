package com.xoff.chessvger.queues.material;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xoff.chessvger.queues.position.PositionDao;
import com.xoff.chessvger.queues.position.PositionEntity;
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
public class MaterialConsumer implements Runner {
  public void run() {

    KafkaConsumer consumer= CommonKafka.getConsumer(KafkaConstants.TOPIC_POSITION,"xoff-material");

    MaterialDao materialDao=new MaterialDao();
    ObjectMapper objectMapper = new ObjectMapper();

    while (true) {
      ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));

      for (ConsumerRecord<String, String> record : records) {

        try {
          MaterialEntity materialEntity = objectMapper.readValue(record.value(), MaterialEntity.class);
          materialDao.insertEntity(materialEntity);

        } catch (JsonProcessingException | SQLException e) {
          throw new RuntimeException(e);
        }

      }
    }
  }
}

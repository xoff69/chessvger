package com.xoff.chessvger.queues.position;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xoff.chessvger.chess.board.CoupleZobristMaterial;
import com.xoff.chessvger.queues.util.CommonKafka;
import com.xoff.chessvger.queues.util.KafkaConstants;
import java.util.List;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

public class PositionMaterialProducer {
  public static void enqueuePositionMaterial(long gameId, List<CoupleZobristMaterial> list) {


    Producer<String, String> producer = CommonKafka.getProducer();
    ObjectMapper objectMapper = new ObjectMapper();


    long id = 1L;
    for (CoupleZobristMaterial coupleZobristMaterial : list) {

      PositionEntity positionEntity = new PositionEntity();
      MaterialEntity materialEntity = new MaterialEntity();
      positionEntity.setId(id);
      materialEntity.setId(id);
      positionEntity.setPosition(coupleZobristMaterial.getZobrist());
      materialEntity.setMaterial(coupleZobristMaterial.getMaterial());
      positionEntity.getGames().add(gameId);
      materialEntity.getGames().add(gameId);

      id++;
      try {
        String jsonPosition = objectMapper.writeValueAsString(positionEntity);
        ProducerRecord<String, String> record =
            new ProducerRecord<>(KafkaConstants.TOPIC_POSITION, null, jsonPosition);

        producer.send(record);
        producer.flush();

      } catch (JsonProcessingException e) {
        e.printStackTrace();
      }

      try {
        String jsonMaterial = objectMapper.writeValueAsString(materialEntity);
        ProducerRecord<String, String> record =
            new ProducerRecord<>(KafkaConstants.TOPIC_MATERIAL, null, jsonMaterial);

        producer.send(record);
        producer.flush();

      } catch (JsonProcessingException e) {
        e.printStackTrace(); // Gestion de l'erreur en cas de problème de conversion
      }

    }
    System.out.println("positions/material in queue: " + list.size());
  }
}

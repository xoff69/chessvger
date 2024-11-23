package com.xoff.chessvger.queues.game;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xoff.chessvger.chess.board.CoupleZobristMaterial;
import com.xoff.chessvger.queues.material.MaterialPositionsUtil;
import com.xoff.chessvger.queues.materialposition.PositionMaterialProducer;
import com.xoff.chessvger.queues.util.KafkaConstants;
import java.sql.SQLException;
import java.time.Duration;
import com.xoff.chessvger.queues.util.CommonKafka;
import java.util.List;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

public class AppConsumerGame {
  public static void main(String[] args) {

  KafkaConsumer consumer= CommonKafka.getConsumer(KafkaConstants.TOPIC_GAME,"xoff-game");

    CommonGameDao commonGameDao=new CommonGameDao();
    ObjectMapper objectMapper = new ObjectMapper();

    while (true) {
      ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));

      for (ConsumerRecord<String, String> record : records) {
        try {
          CommonGame game = objectMapper.readValue(record.value(), CommonGame.class);
          commonGameDao.insertCommonGame(game);

          List<CoupleZobristMaterial> list= MaterialPositionsUtil.parseMoves2(game.getMoves());
          PositionMaterialProducer.enqueuePositionMaterial(game.getId(),list);

        } catch (JsonProcessingException | ClassNotFoundException | SQLException e) {
          throw new RuntimeException(e);
        }

      }
    }
  }
}

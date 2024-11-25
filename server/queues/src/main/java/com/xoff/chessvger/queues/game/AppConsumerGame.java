package com.xoff.chessvger.queues.game;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xoff.chessvger.chess.board.CoupleZobristMaterial;
import com.xoff.chessvger.queues.materialposition.MaterialPositionsUtil;
import com.xoff.chessvger.queues.materialposition.PositionMaterialProducer;
import com.xoff.chessvger.queues.util.CommonKafka;
import com.xoff.chessvger.queues.util.KafkaConstants;
import com.xoff.chessvger.queues.util.Runner;
import java.sql.SQLException;
import java.time.Duration;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
@Slf4j
public class AppConsumerGame implements Runner {

  @Override
  public void run() {

    log.info("runAppConsumerGame starts ");
    KafkaConsumer consumer = CommonKafka.getConsumer(KafkaConstants.TOPIC_GAME, "xoff-game");

    CommonGameDao commonGameDao = new CommonGameDao();
    ObjectMapper objectMapper = new ObjectMapper();

    while (true) {
      ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));

      for (ConsumerRecord<String, String> record : records) {
        try {
          CommonGame game = objectMapper.readValue(record.value(), CommonGame.class);
          commonGameDao.insertCommonGame(game);
          // mettre a jour le reconciliation manager
          // envoyer sur les stats browser
          // envoyer game of player
          List<CoupleZobristMaterial> list = MaterialPositionsUtil.parseMoves2(game.getMoves());
          PositionMaterialProducer.enqueuePositionMaterial(game.getId(), list);

        } catch (JsonProcessingException | ClassNotFoundException | SQLException e) {
          throw new RuntimeException(e);
        }

      }
    }
  }
}

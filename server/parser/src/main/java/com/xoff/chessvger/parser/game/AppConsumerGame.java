package com.xoff.chessvger.queues.game;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xoff.chessvger.chess.board.CoupleZobristMaterial;
import com.xoff.chessvger.queues.gameofplayer.GameOfAPlayer;
import com.xoff.chessvger.queues.materialposition.MaterialPositionsUtil;
import com.xoff.chessvger.queues.materialposition.PositionMaterialProducer;
import com.xoff.chessvger.queues.reconciliation.ReconciliationManager;
import com.xoff.chessvger.queues.reconciliation.ReconciliationType;
import com.xoff.chessvger.queues.util.CommonKafka;
import com.xoff.chessvger.queues.util.KafkaConstants;
import java.sql.SQLException;
import java.time.Duration;
import java.util.List;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

public class AppConsumerGame implements Runnable {

  @Override
  public void run() {

    System.out.println("runAppConsumerGame starts ");
    KafkaConsumer consumer = CommonKafka.getConsumer(KafkaConstants.TOPIC_GAME, "xoff-game");

    CommonGameDao commonGameDao = new CommonGameDao();
    ObjectMapper objectMapper = new ObjectMapper();

    while (true) {
      ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));

      for (ConsumerRecord<String, String> record : records) {
        try {
          CommonGame game = objectMapper.readValue(record.value(), CommonGame.class);
          // TODO recuperer l id du game
          commonGameDao.insertCommonGame(game);
          // mettre a jour le reconciliation manager
          // envoyer sur les stats browser
          // envoyer game of player
         // List<CoupleZobristMaterial> list = MaterialPositionsUtil.parseMoves2(game.getMoves());
         // PositionMaterialProducer.enqueuePositionMaterial(game.getId(), list);
          //enqueueGameOfAPlayer(game.getId(), game.getWhiteFideId());
          //enqueueGameOfAPlayer(game.getId(), game.getBlackFideId());
          //ReconciliationManager.update(game.getId(), ReconciliationType.GAME);

        } catch (JsonProcessingException | ClassNotFoundException | SQLException e) {
          throw new RuntimeException(e);
        }

      }
    }
  }

  private void enqueueGameOfAPlayer(long gameId, long playerId) {
    Producer<String, String> producer = CommonKafka.getProducer();
    ObjectMapper objectMapper = new ObjectMapper();
    try {
      GameOfAPlayer gameOfAPlayer = new GameOfAPlayer();
      gameOfAPlayer.setGameId(gameId);
      gameOfAPlayer.setPlayerId(playerId);


      String jsonPosition = objectMapper.writeValueAsString(gameOfAPlayer);
      ProducerRecord<String, String> record =
          new ProducerRecord<>(KafkaConstants.TOPIC_GAMEOFAPLAYER, null, jsonPosition);

      producer.send(record);
      producer.flush();

    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
  }
}

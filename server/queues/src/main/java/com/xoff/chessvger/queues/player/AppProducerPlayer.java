/*
 * This source file was generated by the Gradle 'init' task
 */

package com.xoff.chessvger.queues.player;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import com.xoff.chessvger.queues.util.CommonKafka;
import com.xoff.chessvger.queues.util.KafkaConstants;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
public class AppProducerPlayer {
  public static void main(String[] args) {

    Producer<String, String> producer = CommonKafka.getProducer();
    ObjectMapper objectMapper = new ObjectMapper();

    PlayerParser playerParser=new PlayerParser();
    List<CommonPlayer> players= playerParser.parse();
    long id=1L;
    for (CommonPlayer player : players) {

      player.setId(id++);
      try {
        String jsonPlayer = objectMapper.writeValueAsString(player);
        ProducerRecord<String, String> record = new ProducerRecord<>(KafkaConstants.TOPIC_PLAYER, null, jsonPlayer);

        producer.send(record);
        producer.flush();

      } catch (JsonProcessingException e) {
        e.printStackTrace(); // Gestion de l'erreur en cas de problème de conversion
      }
    }
    System.out.println("players in queue: "+players.size());


  }

}
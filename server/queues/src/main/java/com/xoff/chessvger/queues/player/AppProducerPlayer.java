/*
 * This source file was generated by the Gradle 'init' task
 */

package com.xoff.chessvger.queues.player;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xoff.chessvger.queues.util.CommonKafka;
import com.xoff.chessvger.queues.util.KafkaConstants;
import com.xoff.chessvger.queues.util.Runner;
import java.time.Duration;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

@Slf4j
public class AppProducerPlayer implements Runner {
  public void run() {
    log.info("Start runAppProducerPlayer");
    KafkaConsumer consumer =
        CommonKafka.getConsumer(KafkaConstants.TOPIC_RUN_PARSERPLAYER, "xoff-parserplayer");

    ObjectMapper objectMapper = new ObjectMapper();
    while (true) {
      ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));

      for (ConsumerRecord<String, String> record : records) {
        try {
          String filename = objectMapper.readValue(record.value(), String.class);
          manageFile(filename);
          log.info("Start to parse:" + filename);

        } catch (JsonProcessingException e) {
          throw new RuntimeException(e);
        }

      }
    }
  }

  /**
   * @param filedir ex "data/players_list_xml_foa.xml"
   */
  private void manageFile(String filedir) {

    Producer<String, String> producer = CommonKafka.getProducer();
    ObjectMapper objectMapper = new ObjectMapper();

    PlayerParser playerParser = new PlayerParser();
    List<CommonPlayer> players = playerParser.parse(filedir);
    long id = 1L;
    for (CommonPlayer player : players) {

      player.setId(id++);
      try {
        String jsonPlayer = objectMapper.writeValueAsString(player);
        ProducerRecord<String, String> record =
            new ProducerRecord<>(KafkaConstants.TOPIC_PLAYER, null, jsonPlayer);

        producer.send(record);
        producer.flush();

      } catch (JsonProcessingException e) {
        e.printStackTrace(); // Gestion de l'erreur en cas de problème de conversion
      }
    }
    log.info("players in queue: " + players.size());


  }

}

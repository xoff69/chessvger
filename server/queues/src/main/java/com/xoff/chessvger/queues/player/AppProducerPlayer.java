/*
 * This source file was generated by the Gradle 'init' task
 */

package com.xoff.chessvger.queues.player;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xoff.chessvger.queues.util.CommonKafka;
import com.xoff.chessvger.queues.util.KafkaConstants;
import java.time.Duration;
import java.util.List;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

public class AppProducerPlayer implements Runnable {
  @Override
  public void run() {

    System.out.println("Start runAppProducerPlayer");
    // TODO : a virer debug
    Producer<String, String> producer = CommonKafka.getProducer();
    String topicName = "mon_topic"; // Nom du topic Kafka
    String key = "messageKey";    // Clé (facultatif)
    String value = "Hello, Kafka!"; // Message à envoyer
    System.out.println("Start runAppProducerPlayer " + producer);
    // Création et envoi du message
    ProducerRecord<String, String> record1 = new ProducerRecord<>(topicName, key, value);
    try {
      // Envoi synchrone (attend la confirmation)
      RecordMetadata metadata = producer.send(record1).get();
      System.out.printf("Message envoyé avec succès au topic %s, partition %d, offset %d%n",
          metadata.topic(), metadata.partition(), metadata.offset());
    } catch (Exception e) {
      System.out.printf("Erreur lors de l'envoi du message : %s%n", e.getMessage());
    } finally {
      producer.close(); // Libération des ressources
    }
    System.out.println("avant le consumer player");
    KafkaConsumer consumer =
        CommonKafka.getConsumer(KafkaConstants.TOPIC_RUN_PARSERPLAYER, "xoff-parserplayer");

    ObjectMapper objectMapper = new ObjectMapper();
    while (true) {
      ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));

      for (ConsumerRecord<String, String> record : records) {
        try {
          String filename = objectMapper.readValue(record.value(), String.class);
          manageFile(filename);
          System.out.println("Start to parse:" + filename);

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
    System.out.println("players in queue: " + players.size());


  }

}

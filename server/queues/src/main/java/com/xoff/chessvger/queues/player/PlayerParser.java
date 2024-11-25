package com.xoff.chessvger.queues.player;


import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PlayerParser {
  public  List<CommonPlayer> parse(String fileName) {
    try {
      File inputFile = new File( fileName);
      //C:\home\developpement\chessvger\server\data\reference\players_list_xml_foa.xml
      //server/data/reference/players_list_xml_foa.xml
      SAXParserFactory factory = SAXParserFactory.newInstance();
      SAXParser saxParser = factory.newSAXParser();
      PlayersListHandler handler = new PlayersListHandler();
      saxParser.parse(inputFile, handler);

     log.info("parse players done");
      List<CommonPlayer> players = handler.getPlayers();

      return players;

    } catch (Exception e) {
      log.error(e.getMessage());
    }
    return new ArrayList<>();
  }
}
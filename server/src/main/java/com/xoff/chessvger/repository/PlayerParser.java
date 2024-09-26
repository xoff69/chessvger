package com.xoff.chessvger.repository;


import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.util.List;

public class SAXParserExample {

  public static long parse() {
    try {
      File inputFile = new File( "data/reference/players_list_xml_foa.xml");
      //C:\home\developpement\chessvger\server\data\reference\players_list_xml_foa.xml
      //server/data/reference/players_list_xml_foa.xml
      SAXParserFactory factory = SAXParserFactory.newInstance();
      SAXParser saxParser = factory.newSAXParser();
      PlayersListHandler handler = new PlayersListHandler();
      saxParser.parse(inputFile, handler);

      // Imprimer la liste des joueurs
      List<Player> players = handler.getPlayers();
      for (Player player : players) {
        System.out.println(player);
      }
      return players.size();

    } catch (Exception e) {
      e.printStackTrace();
    }
    return 0L;
  }
}
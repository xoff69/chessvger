package com.xoff.chessvger.repository;


import com.xoff.chessvger.common.DbKeyManager;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PlayerParser {
  @Autowired
  private PlayerRepository playerRepository;
  public  long parse() {
    try {
      File inputFile = new File( "data/reference/players_list_xml_foa.xml");
      //C:\home\developpement\chessvger\server\data\reference\players_list_xml_foa.xml
      //server/data/reference/players_list_xml_foa.xml
      SAXParserFactory factory = SAXParserFactory.newInstance();
      SAXParser saxParser = factory.newSAXParser();
      PlayersListHandler handler = new PlayersListHandler();
      saxParser.parse(inputFile, handler);

      log.info("parse players done");
      List<Player> players = handler.getPlayers();
      List<CommonPlayerEntity> playerEntities= PlayerParseMapper.INSTANCE.map(players);
      long id=1L;
      for (CommonPlayerEntity player : playerEntities) {

        player.setId(id++);
        playerRepository.save(player);
      }
      log.info("db insert players done "+players.size());
      return players.size();

    } catch (Exception e) {
      log.error(e.getMessage(),e);
    }
    return 0L;
  }
}
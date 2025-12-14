package com.xoff.chessvger.backoffice.player;


import com.xoff.chessvger.model.CommonPlayer;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PlayerParser {
    public List<CommonPlayer> parse(String fileName) {
        try {
            File inputFile = new File(fileName);
            //C:\home\developpement\chessvger\server\data\reference\players_list_xml_foa.xml
            //server/data/reference/players_list_xml_foa.xml
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            PlayersListHandler handler = new PlayersListHandler();
            saxParser.parse(inputFile, handler);

            System.out.println("parse players done");
            List<CommonPlayer> players = handler.getPlayers();
            System.out.println("handler parse players done");
            return players;

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return new ArrayList<>();
    }
}
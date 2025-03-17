package com.xoff.chessvger.ui.web.controller.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xoff.chessvger.config.RedisMessagePublisher;
import com.xoff.chessvger.repository.CommonPlayerEntity;
import com.xoff.chessvger.repository.DataSourceContextHolder;
import com.xoff.chessvger.repository.DynamicDataSourceService;
import com.xoff.chessvger.service.IPlayerService;
import com.xoff.chessvger.topic.ActionQueue;
import com.xoff.chessvger.topic.MessageToParser;
import com.xoff.chessvger.ui.web.controller.tools.ResponseList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@Slf4j
public class PlayersController {


    @Autowired
    RedisMessagePublisher redisMessagePublisher;

    @Autowired
    IPlayerService iPlayerService;

    @Autowired
    private DynamicDataSourceService dynamicDataSourceService;

    private void setDatasource(){
        // FIXME  a pousser dans le controller
        dynamicDataSourceService.addNewDataSource("common",
                "jdbc:postgresql://db_chessvger/chessvger",
                "chessvger",
                "chessvger","common");
        DataSourceContextHolder.setDataSource("common");
    }

    @GetMapping("/fetchPlayers")
    public ResponseEntity<ResponseList<CommonPlayerEntity>> fetchPlayers(@RequestParam(required = false) String[] ids) {
        log.info("fetchPlayers");
        if (ids == null || ids.length == 0) {
            log.info("no ID");
            return ResponseEntity.badRequest().body(null);
        }

        List<String> idList = Arrays.asList(ids);
        List<CommonPlayerEntity> players = new ArrayList<>();
        setDatasource();
        for (String id : idList) {
            CommonPlayerEntity commonPlayerEntity = iPlayerService.findById(Long.valueOf(id));
            if (commonPlayerEntity != null) {
                players.add(commonPlayerEntity);
            }
        }
        return new ResponseEntity<>(new ResponseList(players, players.size()),
                HttpStatus.OK);
    }

    @GetMapping("/apiadmin/players/all")
    public ResponseEntity<ResponseList<CommonPlayerEntity>> all() {
        setDatasource();
        return new ResponseEntity<>(new ResponseList(iPlayerService.findAll(), iPlayerService.count()),
                HttpStatus.OK);
    }


    @GetMapping("/apiadmin/players/count")
    public ResponseEntity<Long> count() {
        setDatasource();
        return new ResponseEntity<>(iPlayerService.count(),
                HttpStatus.OK);
    }

    @GetMapping("/apiadmin/players/import")
    // TODO signature, try catch
    public ResponseEntity<String> jobPlayer() throws JsonProcessingException {
        log.info("jobPlayer");

        MessageToParser message = new MessageToParser();
        message.setFolderToParse("./data/players_list_xml_foa.xml");
        message.setSchema("common");
        message.setActionQueue(ActionQueue.PARSEPLAYER);

        ObjectMapper objectMapper = new ObjectMapper();

        redisMessagePublisher.publish(objectMapper.writeValueAsString(message));
        return new ResponseEntity<>("run",
                HttpStatus.OK);
    }
}

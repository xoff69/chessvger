package com.xoff.chessvger.ui.web.controller.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xoff.chessvger.config.RedisMessagePublisher;
import com.xoff.chessvger.database.DataSourceContextHolder;
import com.xoff.chessvger.database.DynamicDataSourceService;
import com.xoff.chessvger.service.IPlayerService;
import com.xoff.chessvger.topic.ActionQueue;
import com.xoff.chessvger.topic.MessageToParser;
import com.xoff.chessvger.ui.web.controller.tools.ResponseList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.xoff.chessvger.model.CommonPlayer;
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

    @GetMapping("/apiadmin/players/fetchPlayers")
    public ResponseEntity<ResponseList<CommonPlayer>> fetchPlayers(@RequestParam(required = false) String[] ids) {
        log.info("fetchPlayers");
        if (ids == null || ids.length == 0) {
            log.info("no ID");
            return ResponseEntity.badRequest().body(null);
        }

        List<String> idList = Arrays.asList(ids);
        List<CommonPlayer> players = new ArrayList<>();
        setDatasource();
        for (String id : idList) {
            CommonPlayer commonPlayerEntity = iPlayerService.findById(Long.valueOf(id));
            if (commonPlayerEntity != null) {
                players.add(commonPlayerEntity);
            }
        }
        return new ResponseEntity<>(new ResponseList(players, players.size()),
                HttpStatus.OK);
    }

    @GetMapping("/apiadmin/players/all")
    public ResponseEntity<ResponseList<CommonPlayer>> all( @RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "10") int size) {
//TODO @RequestHeader("Authorization") String token,
        Pageable pageable = PageRequest.of(page, size);
        setDatasource();
        return new ResponseEntity<>(new ResponseList(iPlayerService.findAll(pageable).stream().toList(), iPlayerService.count()),
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

package com.xoff.chessvger.ui.web.controller;

import com.xoff.chessvger.database.DatabaseHelperService;
import com.xoff.chessvger.model.DatabaseModel;
import com.xoff.chessvger.model.PlayerGameCount;
import com.xoff.chessvger.service.GamePlayerService;
import com.xoff.chessvger.service.IDatabaseService;
import com.xoff.chessvger.ui.web.controller.tools.ResponseList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@Slf4j
public class GamesPlayerController {
    @Autowired
    IDatabaseService iDatabaseService;

    @Autowired
    GamePlayerService gamePlayerService;


    @Autowired
    DatabaseHelperService databaseHelperService;

    @GetMapping("/api/gamesplayer/all")
    public ResponseEntity<ResponseList<PlayerGameCount>> all(@RequestHeader("Authorization") String token,
                                                             @RequestParam long databaseId,
                                                             @RequestParam(defaultValue = "0") int page,
                                                             @RequestParam(defaultValue = "10") int size) throws IOException, InterruptedException {
        log.info("Getting all game player models");
        Pageable pageable = PageRequest.of(page, size);

        // TODO verifier que le user a le droit de lire cette bd
        // FIXME on fait deja le count dans le gameservice
        // pas la peine de repondre un response list ?
        // TODO : la bd des players n est aps au meme endroit, difficile de faire une jointure
        databaseHelperService.setDatasource(token, "common");
        DatabaseModel databaseModel = iDatabaseService.findById(databaseId);
        log.info("databaseid:" + databaseId + "databaseModel:" + databaseModel);
        databaseHelperService.setDatasource(token, databaseModel.getName());

        return new ResponseEntity<>(new ResponseList(gamePlayerService.getPlayersWithGameCount(pageable).stream().toList(), gamePlayerService.count()),
                HttpStatus.OK);
    }


}

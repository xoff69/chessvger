package com.xoff.chessvger.ui.web.controller;

import com.xoff.chessvger.config.RedisMessagePublisher;
import com.xoff.chessvger.database.DatabaseHelperService;
import com.xoff.chessvger.model.DatabaseModel;
import com.xoff.chessvger.service.GameService;
import com.xoff.chessvger.service.IBrowseService;
import com.xoff.chessvger.service.IDatabaseService;
import com.xoff.chessvger.ui.web.controller.tools.ResponseList;
import com.xoff.chessvger.view.StatBrowserView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@Slf4j
public class BrowseController {
    @Autowired
    IDatabaseService iDatabaseService;



    @Autowired
    DatabaseHelperService databaseHelperService;
    @Autowired
    IBrowseService iBrowseService;

    @GetMapping("/api/browse/all")
    public ResponseEntity<List<StatBrowserView>> browse(@RequestHeader("Authorization") String token,
                                                             @RequestParam long databaseId,
                                                        @RequestParam(defaultValue = "") String previousMoves) {

        databaseHelperService.setDatasource(token, "common");
        DatabaseModel databaseModel = iDatabaseService.findById(databaseId);
        log.info("databaseid:" + databaseId + "databaseModel:" + databaseModel);
        databaseHelperService.setDatasource(token, databaseModel.getName());

        return new ResponseEntity<>(iBrowseService.browse(previousMoves),
                HttpStatus.OK);
    }


}

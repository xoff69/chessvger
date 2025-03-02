package com.xoff.chessvger.ui.web.controller;

import com.xoff.chessvger.model.DatabaseModel;
import com.xoff.chessvger.service.DatabaseHelperService;
import com.xoff.chessvger.service.IDatabaseService;
import com.xoff.chessvger.service.UserService;
import com.xoff.chessvger.ui.ResponseList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class DatabaseController {

    @Autowired
    IDatabaseService iDatabaseService;
    @Autowired
    DatabaseHelperService databaseHelperService;
    @Autowired
    private UserService userService;

    @GetMapping("/api/databases/all")
    public ResponseEntity<ResponseList<DatabaseModel>> all(@RequestHeader("Authorization") String token) {

        log.info("token = {}", token);
        databaseHelperService.setDatasource(token, "common");
        return new ResponseEntity<>(new ResponseList(iDatabaseService.findAll(), iDatabaseService.count()),
                HttpStatus.OK);
    }


}

package com.xoff.chessvger.ui.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xoff.chessvger.util.ParamConstants;
import com.xoff.chessvger.config.RedisMessagePublisher;
import com.xoff.chessvger.database.DatabaseHelperService;
import com.xoff.chessvger.model.CommonGame;
import com.xoff.chessvger.model.DatabaseModel;
import com.xoff.chessvger.model.TenantEntity;
import com.xoff.chessvger.service.GameService;
import com.xoff.chessvger.service.IDatabaseService;
import com.xoff.chessvger.topic.ActionQueue;
import com.xoff.chessvger.topic.MessageToParser;
import com.xoff.chessvger.ui.web.controller.tools.ApiRequest;
import com.xoff.chessvger.ui.web.controller.tools.ResponseList;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Optional;

@RestController
@Slf4j
public class GamesController {
    @Autowired
    IDatabaseService iDatabaseService;

    @Autowired
    GameService gameService;

    @Autowired
    RedisMessagePublisher redisMessagePublisher;

    @Autowired
    DatabaseHelperService databaseHelperService;

    @GetMapping("/api/games/all")
    public ResponseEntity<ResponseList<CommonGame>> all(@RequestHeader("Authorization") String token,
                                                        @RequestParam long databaseId, @RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "10") int size) {
        log.info("/api/games/all:" + databaseId);

        Pageable pageable = PageRequest.of(page, size);
        // TODO verifier que le user a le droit de lire cette bd
        // FIXME on fait deja le count dans le gameservice
        // pas la peine de repondre un response list ?
        databaseHelperService.setDatasource(token, "common");
        DatabaseModel databaseModel = iDatabaseService.findById(databaseId);
        log.info("databaseid:" + databaseId + "databaseModel:" + databaseModel);
        databaseHelperService.setDatasource(token, databaseModel.getName());
        return new ResponseEntity<>(new ResponseList(gameService.findAll(pageable).stream().toList(), gameService.count()),
                HttpStatus.OK);
    }

    @GetMapping("/api/games/findById")
    public ResponseEntity<CommonGame>
    findById(@RequestHeader("Authorization") String token, @RequestParam("id") Long id, @RequestParam("databaseId") Long databaseId) {

        log.info("/api/games/findById:" + databaseId + "*" + id);

        databaseHelperService.setDatasource(token, "common");
        DatabaseModel databaseModel = iDatabaseService.findById(databaseId);

        databaseHelperService.setDatasource(token, databaseModel.getName());
        Optional<CommonGame> opt = gameService.findById(id);
        if (opt.isPresent()) {
            return new ResponseEntity<>(opt.get(), HttpStatus.OK);

        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping("/api/games/import")
    public ResponseEntity<String> importPgn(@RequestHeader("Authorization") String token, @RequestBody ApiRequest apiRequest)
            throws JsonProcessingException {
        log.info("importPgn request:" + apiRequest);
        databaseHelperService.setDatasource(token, "common");
        DatabaseModel databaseModel = iDatabaseService.findById(Long.valueOf(apiRequest.getDatabaseId()));
        log.info("databaseid:" + apiRequest + "databaseModel:" + databaseModel);


        Optional<TenantEntity> opt = databaseHelperService.getFromToken(token);
        if (opt.isPresent()) {
            TenantEntity tenantEntity = opt.get();

            MessageToParser messageGame = new MessageToParser();
            messageGame.setTenantId(tenantEntity.getId());
            messageGame.setFolderToParse("./data/twic1997"); // TODO repertoire pgn en dur
            String name = tenantEntity.getName();
            messageGame.setDatabaseName("chessvger_" + name + "_database");
            messageGame.setDatabaseId(Long.valueOf(apiRequest.getDatabaseId()));
            messageGame.setSchema(databaseModel.getName());
            messageGame.setActionQueue(ActionQueue.PARSEGAME);

            ObjectMapper objectMapper = new ObjectMapper();

            redisMessagePublisher.publish(objectMapper.writeValueAsString(messageGame));

            return ResponseEntity.ok("Requête traitée avec succès pour tenantEntity: " + tenantEntity.getId());
        } else {
            log.error("Token importPgn= {}", token);
            return ResponseEntity.badRequest().body("Token importPgn= " + token);
        }
    }



    private static void finishUpload() {
        log.info(" finishUpload ");
        try {
            FileUtils.cleanDirectory(new File(FilenameUtils.getName(ParamConstants.PATH_IMPORT)));
        } catch (IOException e) {
            log.error(e.getMessage());
        }

    }

    private static final File writeMultipartToDisk(MultipartFile multipartFile) {
        File fileo = new File(
                FilenameUtils.getName(ParamConstants.PATH_IMPORT + multipartFile.getOriginalFilename()));

        try (OutputStream os = new FileOutputStream(fileo)) {
            os.write(multipartFile.getBytes());
        } catch (FileNotFoundException e) {
            log.error(e.getMessage());
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return fileo;
    }
}

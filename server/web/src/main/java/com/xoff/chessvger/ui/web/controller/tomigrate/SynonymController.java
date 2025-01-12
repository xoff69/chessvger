package com.xoff.chessvger.ui.web.controller.tomigrate;

import com.xoff.chessvger.common.GlobalManager;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class SynonymController {

  @GetMapping("/synonyms")
  public ResponseEntity<List> getAll() {

    List<String> l =
        GlobalManager.getInstance().getCommonPlayerManager().getSynonymPlayerManager().list();
    return new ResponseEntity<>(l, HttpStatus.OK);

  }
}

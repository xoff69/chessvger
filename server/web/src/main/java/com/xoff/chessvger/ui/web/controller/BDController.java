package com.xoff.chessvger.ui.web.controller;

import com.xoff.chessvger.repository.DatabaseEntity;
import com.xoff.chessvger.ui.PageRequest;
import com.xoff.chessvger.ui.ResponseList;
import com.xoff.chessvger.ui.service.IDatabaseService;
import com.xoff.chessvger.ui.web.form.DBForm;
import com.xoff.chessvger.ui.web.navigation.Navigation;
import com.xoff.chessvger.view.CoupleLongView;
import com.xoff.chessvger.view.DBView;
import com.xoff.chessvger.view.PageView;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Slf4j
public class BDController {

  @Autowired
  IDatabaseService iDatabaseService;

  @Autowired
  Navigation navigation;


  @GetMapping("/api/databases/all")
  public ResponseEntity<ResponseList<DatabaseEntity>> all(@RequestParam long tenantId){

    System.out.println("Reçu  tenantId: " + tenantId);

    return new ResponseEntity<>(new ResponseList(iDatabaseService.findAll(),iDatabaseService.count()),
        HttpStatus.OK);
  }



  @PostMapping(path = "/bdCreate")
  public @ResponseBody ResponseEntity<String> post(@RequestBody DBForm dbForm) {
    iDatabaseService.createBD(dbForm);
    return ResponseEntity.ok("DB created " + dbForm.getDescription());
  }

  @DeleteMapping("/bdDelete")
  public @ResponseBody ResponseEntity<String> delete() {
    return new ResponseEntity<String>("DELETE TODO", HttpStatus.OK);
  }

  @GetMapping("/bdOpen")
  public String bdOpen(@RequestParam long bdId) {
    log.info("bdOpen " + bdId);
    iDatabaseService.dbOpen(bdId);
    return "redirect:/";
  }

  @PatchMapping(value = "/bd/close/{bdId}")
  public ResponseEntity<CoupleLongView> updateEnabled(@PathVariable(name = "bdId") Long bdId) {
    return ResponseEntity.ok(iDatabaseService.dbClose(bdId));
  }
}

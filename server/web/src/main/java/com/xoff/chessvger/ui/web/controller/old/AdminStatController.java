package com.xoff.chessvger.ui.web.controller.old;

import com.xoff.chessvger.ui.service.IAdminStatService;
import com.xoff.chessvger.ui.web.navigation.ApplicationBean;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class AdminStatController {

  @Autowired
  private IAdminStatService adminStatService;

  @Autowired
  private ApplicationBean applicationBean;

  @GetMapping(path = "/adminStat", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<String> getAllStat() {
    // TODO securite
    log.info("feature stat");
    List<String> res = new ArrayList<>();
    res.add(adminStatService.getAllStat());
    return res;

  }
  @DeleteMapping(path = "/adminStatClear")
  public String clear() {
    // TODO securite
    log.info("feature clear ");
    adminStatService.clear();
    return "ok";

  }
}

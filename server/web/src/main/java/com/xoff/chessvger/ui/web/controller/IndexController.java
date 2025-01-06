package com.xoff.chessvger.ui.web.controller;

import com.xoff.chessvger.ui.service.FeatureService;
import com.xoff.chessvger.ui.web.mapper.DatabaseMapper;
import com.xoff.chessvger.ui.web.mapper.GameMapper;
import com.xoff.chessvger.ui.web.navigation.ApplicationBean;
import com.xoff.chessvger.ui.web.navigation.Navigation;
import com.xoff.chessvger.ui.web.navigation.Tab;
import com.xoff.chessvger.view.CoupleView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Slf4j
public class IndexController {

  @Autowired
  FeatureService featureService;

  @Autowired
  ApplicationBean applicationBean;
  @Autowired
  Navigation navigation;
  @Autowired
  GameMapper gameMapper;
  @Autowired
  DatabaseMapper databaseMapper;

  @GetMapping("/closeAll")
  public ResponseEntity<String> closeAll() {
      log.info("closeAll");
    return new ResponseEntity<>("ok", HttpStatus.OK);
  }

  @GetMapping("/chooseTab")
  public ResponseEntity<CoupleView> chooseTab(@RequestParam String tabId) {
    CoupleView coupleView=new CoupleView();
    String id=tabId.substring("tab".length());
    Tab current=navigation.findById(Integer.parseInt(id));
    if (current.getFils().isEmpty()){
      log.info("chooseTab cas 0 : "+current);
      coupleView.setParent(current.getId());
      coupleView.setChild(0);
    }
    else{
      log.info("chooseTab cas 1 : "+current);
      // TODO mettre a jour la navigation et eventuellement update ici
      coupleView.setParent(current.getId());
      coupleView.setChild(current.getFils().getFirst().getId());
    }
    //log.info("chooseTab : "+coupleView);
    return new ResponseEntity<>(coupleView, HttpStatus.OK);
  }


  @GetMapping("/")
  public String index(Model model) {
    model.addAttribute("navigation", navigation);

    log.info("fin index controller");
    return "index";
  }
}

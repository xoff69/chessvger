package com.xoff.chessvger.ui.web.controller.tomigrate;

import com.xoff.chessvger.ui.web.navigation.Navigation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;


//@EnableScheduling
@Controller
@Slf4j
public class ChessEngineController {
  @Autowired
  Navigation navigation;
  //@Autowired
  //private SimpMessagingTemplate template;

  /*
      Random r=new Random();
      @Scheduled(fixedRate = 5000)
      public void greetings()  throws Exception {
          Thread.sleep(1000); // simulated delay
          System.out.println("scheduled");
          // la queue va utiliser le convert and send
          this.template.convertAndSend("/topic/greetings",
          new Greeting("scheduled "+r.nextDouble()));
      }
  */
    /*
    @MessageMapping("/fleet/{fleetId}/driver/{driverId}")
    @SendTo("/topic/fleet/{fleetId}")
    public Simple simple(@DestinationVariable String fleetId,
    @DestinationVariable String driverId) {
        return new Simple(fleetId, driverId);
    }
    */
  /*
  @MessageMapping("/runAnalyse")
  @SendTo("/topic/greetings")
  // public ChessLineView runAnalyse(@PathVariable Long bdId,
  // @PathVariable Long gameId, @PathVariable String fen) throws Exception {
  public ChessLineView runAnalyse() throws Exception {

    // ici on initialise  l engine et le queuemanager
    //EngineAnalyseLigne ici, na pas d existanece ailleurs
    //     GameView gameView = navigation.getCacheGameView().getDatabaseManager(3 + "_" + 1914);
    Engine engine = EngineFactory.createDefaultEngineForSystem();

    log.info("run analuse");
    engine.setFen("1k6/3K1B2/8/2N5/8/8/8/8 b - - 0 9");
    QueueManagerForGame queueManagerForGame = new QueueManagerForGame("/topic/greetings");
    EngineAnalyseLigne engineAnalyseLigne = new EngineAnalyseLigne(engine, queueManagerForGame);
    queueManagerForGame.addConsumer(template);
    engineAnalyseLigne.runAnalyse();
    //Thread.sleep(1000); // simulated delay
    return new ChessLineView("Hello, " + HtmlUtils.htmlEscape("start") + "!");
  }
*/
}
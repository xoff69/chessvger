package com.xoff.chessvger.util;


import java.util.Collection;
import java.util.HashMap;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TimeMeter {


  private static TimeMeter _instance;
  private final HashMap<String, Mesure> hashMethod;
  private long startTime;

  private TimeMeter() {
    hashMethod = new HashMap<String, Mesure>();
    startTime = System.currentTimeMillis();
  }

  public static TimeMeter getInstance() {
    if (_instance == null) {
      _instance = new TimeMeter();
    }
    return _instance;
  }

  public void print() {

    log.info(">>>Result:");
    long endTime = startTime = System.currentTimeMillis() - startTime;
    Collection<Mesure> collec = hashMethod.values();
    for (Mesure m : collec) {
      m.setRatio(endTime);
      log.info(m.toString());
    }
    log.info("<<<Result");
  }


  public void startMesure(String methodeName) {
    Mesure m = hashMethod.get(methodeName);
    if (m == null) {
      m = new Mesure();
      m.setMethodeName(methodeName);
      hashMethod.put(methodeName, m);
    }
    m.setDebut(System.currentTimeMillis());

  }


  public void stopMesure(String methodeName) {
    long l2 = System.currentTimeMillis();
    Mesure m = hashMethod.get(methodeName);
    if (m != null) {
      log.error("No start call for " + methodeName);

      m.setTotalExecTime(m.getTotalExecTime() + l2 - m.getDebut());
      m.setNbCall(m.getNbCall() + 1);
    }
  }

  @Getter
  @Setter
  public class Mesure {


    private long ratio;

    private String methodeName;

    private long totalExecTime;

    private int nbCall;

    private long debut;


    public Mesure() {
      totalExecTime = 0;
      nbCall = 0;
      ratio = 1;
    }

    @Override
    public String toString() {
      String result = "Methode=" + methodeName;
      result += ",temps total=" + totalExecTime + " soit " + totalExecTime / 1000 + " s";
      result += ",nb appel=" + nbCall;
      if (ratio != 0) {
        result += ",ratio=" + (totalExecTime * 100 / ratio) + " %";
      }
      return result;
    }


  }

}

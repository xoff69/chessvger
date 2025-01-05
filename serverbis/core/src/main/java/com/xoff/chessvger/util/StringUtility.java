package com.xoff.chessvger.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

@Slf4j
public class StringUtility {


  public static boolean egaliteChaine(String critereValue, String st2) {
    if (!StringUtils.isEmpty(critereValue)) {
      return st2.toLowerCase().contains(critereValue.toLowerCase());
    }
    return true;
  }

  /**
   * //TODO @description egalite de chainte + egalite approximative ,a definir
   * karpov = karpof , guelfand = gelfand, c est les synonymes tout betement
   */
  public static boolean egaliteChainePlayer(String critereValue, String st2, boolean approx) {
    if (!StringUtils.isEmpty(critereValue)) {
      return st2.toLowerCase().contains(critereValue.toLowerCase());
    }
    return true;
  }


  public static boolean isNumberOrEmpty(String s) {
    return StringUtils.isEmpty(s) || StringUtils.isNumeric(s);
  }

  /**
   * @FIXME
   */
  public static boolean isDateOrEmpty(String s) {
    StringUtils.isEmpty(s);
    return true;
  }


  public static int letterToNumberEco(char letter) {
    switch (letter) {
      case 'A':
        return 1000;
      case 'B':
        return 2000;
      case 'C':
        return 3000;
      case 'D':
        return 4000;
      case 'E':
        return 5000;
      default:
        log.error("letterToNumberEco error " + letter);

    }
    return 0;
  }

  /**
   * TODO a mettre dans PHNutil
   */
  public static boolean egaliteNombre(int critereValue, int st2) {
    if (critereValue != 0) {
      return critereValue == st2;
    }
    return true;
  }

  public static boolean egaliteNombre(long critereValue, long st2) {
    if (critereValue != 0) {
      return critereValue == st2;
    }
    return true;
  }

  /**
   * on considere les codes au bon format, attention des fois on a 00/05 genre
   * A00/A05 assimile a 00
   * r , l eco du game
   */
  public static boolean between(String eco1, String eco2, String valeur) {
    if (StringUtils.isEmpty(valeur)) {
      return false;
    }
    int mi = 0;
    try {
      mi = Integer.parseInt(eco1.substring(1));
    } catch (Exception e) {
      log.error("between A " + eco1);
    }
    int ma = 99;
    try {
      ma = Integer.parseInt(eco2.substring(1));
    } catch (Exception e) {
      log.error("between A " + eco2);
    }
    int vg = 0;
    try {
      vg = Integer.parseInt(valeur.substring(1));
    } catch (Exception e) {
      log.error("between c  " + valeur);
    }
    int min = letterToNumberEco(eco1.charAt(0)) + mi;
    int max = letterToNumberEco(eco2.charAt(0)) + ma;
    int val = letterToNumberEco(valeur.charAt(0)) + vg;
    return (val >= min && val <= max);
  }

  public static String notZeronotEmpty(int value) {
    if (value > 0) {
      return String.valueOf(value);
    } else {
      return StringUtils.EMPTY;
    }
  }

  /**
   * convertit une liste en chaine separee par MAP_SEP
   */
  public static String listToString(List<String> l) {
    if (l == null) {
      return StringUtils.EMPTY;
    }
    StringBuilder sb = new StringBuilder();
    l.stream().filter((s) -> (!StringUtils.isEmpty(s))).forEachOrdered((s) -> {
      sb.append(s).append(Constants.MAP_SEP);
    });
    return sb.toString();
  }

  /**
   * convertit un set en chaine separee par MAP_SEP
   */
  public static String setToString(Set<String> l) {
    StringBuilder sb = new StringBuilder();
    l.stream().filter((s) -> (!StringUtils.isEmpty(s))).forEachOrdered((s) -> {
      sb.append(s).append(Constants.MAP_SEP);
    });
    return sb.toString();
  }

  public static List<String> stringToList(String source) {
    List<String> l = new ArrayList();
    if (source == null || source.isEmpty()) {
      return l;
    }
    String[] as = source.split(Constants.MAP_SEP);

    for (String s : as) {
      if (!StringUtils.isEmpty(s)) {
        l.add(s);
        //   log.info(source + " apres le split:" + s);
      }
    }

    return l;
  }

  /**
   * convertit une liste en Set
   */
  public static Set<String> stringToSet(String source) {
    Set<String> l = new HashSet<>();
    if (source == null || source.isEmpty()) {
      return l;
    }
    String[] as = source.split(Constants.MAP_SEP);

    for (String s : as) {
      if (!StringUtils.isEmpty(s)) {
        l.add(s);
        //   log.info(source + " apres le split:" + s);
      }
    }

    return l;
  }
}

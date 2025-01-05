package com.xoff.chessvger.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;


@Slf4j
public class DateUtils {

  private static final SimpleDateFormat SDFSTANDARD =
      new SimpleDateFormat(Constants.FORMAT_DATE_PGN);
  private static final SimpleDateFormat SDFVARIANT2 = new SimpleDateFormat("YYYY_MM_dd");


  public static int getYear(String dateEntree) {

    if (StringUtils.isEmpty(dateEntree) || dateEntree.length() < 4) {
      return 0;
    }
    int y = 0;
    try {
      y = Integer.parseInt(dateEntree.substring(0, 4));
    } catch (NumberFormatException e) {
      log.error("Error date " + dateEntree);
    }
    return y;
  }


  public static String toDateFile(long ts) {
    Date date = new Date(ts);
    return SDFVARIANT2.format(date);
  }

  public static String toDate(long ts) {
    Date date = new Date(ts);
    return SDFSTANDARD.format(date);
  }

}

package com.xoff.chessvger.util;


public class FaitsDeJeuUtil {

  public static final long WHITE_CASTLE_SHORT =
      0b0000000000000000000000000000000000000000000000000000000000000001L;
  public static final long WHITE_CASTLE_LONG =
      0b0000000000000000000000000000000000000000000000000000000000000010L;

  public static final long BLACK_CASTLE_SHORT =
      0b0000000000000000000000000000000000000000000000000000000000000100L;
  public static final long BLACK_CASTLE_LONG =
      0b0000000000000000000000000000000000000000000000000000000000001000L;

  public static final long BXF7 =
      0b0000000000000000000000000000000000000000000000000000000000010000L;
  public static final long BXH6 =
      0b0000000000000000000000000000000000000000000000000000000000100000L;

  public static final long BXF2 =
      0b0000000000000000000000000000000000000000000000000000000001000000L;
  public static final long BXH3 =
      0b0000000000000000000000000000000000000000000000000000000010000000L;

  public static final long DOUBLECHECK =
      0b0000000000000000000000000000000000000000000000000000000100000000L;
  public static final long EP = 0b0000000000000000000000000000000000000000000000000000001000000000L;

  public static final long OPPOSITEBISHOP =
      0b0000000000000000000000000000000000000000000000000000010000000000L;

  public static final long PAT =
      0b0000000000000000000000000000000000000000000000000000100000000000L;

  public static final long DOUPLEPAWNS =
      0b0000000000000000000000000000000000000000000000000001000000000000L;
  public static final long TRIPLEPAWNS =
      0b0000000000000000000000000000000000000000000000000010000000000000L;


  public static boolean isMatching(long value, long mask) {
    return (value & mask) == mask;

  }

}

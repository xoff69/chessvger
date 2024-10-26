package com.xoff.chessvger.util;


public class MaterialUtil {

  //PION 8
  //DAME,FOU,CAVALIER,TOUR 5
  //public static final long MASK = 0b0000000000000000000000000000000000000000000000000000000000000000L;
  //public static final long MASK_PION_BLANC = 0b1111111111111111111111111111111111111111111111111111111100000000L;
  //public static final long MASK_PION_NOIR=0b0000000011111111111111111111111111111111111111111111111111111111L;
  //public static final long MASK_CAVALIER_BLANC=0b1111111111111111111111111111111111111111111111111100000011111111L;
  //public static final long MASK_CAVALIER_NOIR=0b1111111100000011111111111111111111111111111111111111111111111111L;
  //public static final long MASK_FOU_BLANC = 0b1111111111111111111111111111111111111111111100000011111111111111L;
  //public static final long MASK_FOU_NOIR = 0b1111111111111100000011111111111111111111111111111111111111111111L;
  //public static final long MASK_TOUR_BLANC = 0b1111111111111111111111111111111111111100000011111111111111111111L;
  //public static final long MASK_TOUR_NOIR = 0b1111111111111111111100000011111111111111111111111111111111111111L;
  //public static final long MASK_DAME_BLANC = 0b1111111111111111111111111111111100000011111111111111111111111111L;
  //public static final long MASK_DAME_NOIR = 0b1111111111111111111111111100000011111111111111111111111111111111L;
  public static final int DEBUT_PION_BLANC = 0;
  public static final int DEBUT_CAVALIER_BLANC = 8;
  public static final int DEBUT_FOU_BLANC = 13;
  public static final int DEBUT_DAME_BLANC = 18;
  public static final int DEBUT_TOUR_BLANC = 23;

  public static final int DEBUT_PION_NOIR = 25;
  public static final int DEBUT_FOU_NOIR = 34;
  public static final int DEBUT_DAME_NOIR = 39;
  public static final int DEBUT_TOUR_NOIR = 45;
  public static final int DEBUT_CAVALIER_NOIR = 50;


  public static long encode(long origine, int nbElements, int offsetMask) {
    // on code les pions sur les 8 premiers bits a droite
    long localmask = 1;
    localmask = localmask << offsetMask;
    //  origine = origine | 0L;
    for (int i = 0; i < nbElements; i++) {
      origine = origine | localmask;
      localmask = localmask << 1;
    }
    return origine;
  }

}

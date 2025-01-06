package com.xoff.chessvger.backoffice.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FileUtils {
  public static String read(String where){
    StringBuilder sqlQuery = new StringBuilder();
    try (InputStream inputStream = FileUtils.class.getClassLoader().getResourceAsStream(where)) {


      try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
        String ligne;
        while ((ligne = reader.readLine()) != null) {
          sqlQuery.append(ligne).append("\n");
        }
      }

      return  sqlQuery.toString();
    } catch (IOException e) {
      System.err.println("Erreur lors de la lecture du fichier : " + e.getMessage());
      return "";
    }
  }
}

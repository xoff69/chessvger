package com.xoff.chessvger.repository;

public class DataSourceContextHolder {

  // Stocker le contexte des sources de données dans un ThreadLocal
  private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();

  /**
   * Définir le contexte de la source de données.
   * @param dataSourceType Le type de source de données (par exemple, "primary" ou "secondary").
   */
  public static void setDataSource(String dataSourceType) {
    contextHolder.set(dataSourceType);
  }

  /**
   * Obtenir le contexte courant de la source de données.
   * @return Le type de source de données actuel.
   */
  public static String getDataSource() {
    return contextHolder.get();
  }

  /**
   * Effacer le contexte courant.
   */
  public static void clearDataSourceType() {
    contextHolder.remove();
  }
}

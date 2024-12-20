package com.xoff.chessvger.backoffice.util;

import com.xoff.chessvger.common.Contract;
import com.xoff.chessvger.common.UserTenant;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.Instant;
// pgadmin a lancer aussi

public class DatabaseManager {
  public static final String COMMON_SCHEMA="common";
  public static final String ADMIN_SCHEMA="admin";
  public static final String SCHEMA_TENANT_PATTERN="tenant_%s";

  public static final String TABLE_PLAYER="create table "+COMMON_SCHEMA+".common_player (id bigint not null, birthday varchar(255),\n" +
      "                                   blitz_games varchar(255), blitz_rating varchar(255), blitzk varchar(255),\n" +
      "                                   country varchar(255), fide_id varchar(255), flag varchar(255),\n" +
      "                                   foa_title varchar(255), games varchar(255), k varchar(255), name varchar(255),\n" +
      "                                   o_title varchar(255), rapid_games varchar(255), rapid_rating varchar(255),\n" +
      "                                   rapidk varchar(255), rating varchar(255), sex varchar(255), title varchar(255),\n" +
      "                                   w_title varchar(255), primary key (id))\n" + ";";
  public static final String TABLE_DATABASE=   "CREATE TABLE %s.database (\n" +
     " id BIGINT PRIMARY KEY AUTO_INCREMENT,\n" +
      "   name VARCHAR(255) NOT NULL,\n" +
      "description TEXT,\n" +
      "date_created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,\n" +
      "date_update TIMESTAMP,\n" +
      ");";

  /**
   * tenant schema + db id
   */
  public static final String TABLE_GAME=" create table %s.db_%s_common_game (black_elo integer, date date, event_date date, favori boolean not null, interet integer not null,\n" +
  "is_deleted boolean, last_position integer, nb_coups integer, partie_analysee boolean, theorique boolean not null,\n" +
  "white_elo integer, black_fide_id bigint, id bigint not null, informations_fait_de_jeu bigint, last_update bigint,\n" +
  "white_fide_id bigint, moves varchar(3000), black_player varchar(255), black_title varchar(255), eco varchar(255),\n" +
  "event varchar(255), first_move varchar(255), opening varchar(255), result varchar(255), round varchar(255),\n" +
 " site varchar(255), white_player varchar(255), white_title varchar(255), primary key (id))\n" +
  ";";
  /**
   * tenant schema + db id
   */
  public static final String TABLE_POSITION_GAMES=
  "create table %s.position_games (id bigint not null, position bigint not null, primary key (id));";
  /**
   * tenant schema + db id
   */
  public static final String TABLE_POSITION_ENTITY_GAMES=
      "  create table %s.position_entity_games (games bigint, position_entity_id bigint not null);";
  public static final String TABLE_CONTRACT = ;
  public static final String FEATURE_FLAG = ;


  private static final String CHECK_SCHEMA_EXISTS_SQL =
      "SELECT EXISTS (SELECT 1 FROM information_schema.schemata WHERE schema_name = ?)";
  private static final String CREATE_SCHEMA_SQL =
      "CREATE SCHEMA IF NOT EXISTS %s";
  public static final String TABLE_USER =
      "CREATE TABLE "+ADMIN_SCHEMA+".users (\n" + "\n" +
          "                             id SERIAL PRIMARY KEY,\n" +
          "                             login VARCHAR(50) NOT NULL UNIQUE,\n" +
          "                             description TEXT,\n" +
          "                             password VARCHAR(255) NOT NULL,\n" + "\n" +
          "                             date_created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,\n" +
          "                             date_update TIMESTAMP,\n" + "\n" +
          "                             profil BOOLEAN DEFAULT FALSE -- Profil: admin (TRUE) ou utilisateur normal (FALSE)\n" +
          "\n" + ");";
  private static final String INSERT_USER="INSERT INTO \"+ADMIN_SCHEMA+\".users (login, description, password, profil)\n" +
  "VALUES     ('admin', 'Administrator account', 'admin', TRUE);";

public static Contract getDefaultContract(){
  // TODO
  return new Contract();
}
public static void createDatabase(UserTenant userTenant) {

  // TODO
}
public static void linkUserToContract(UserTenant userTenant,Contract contract){}
  public static void createUser(Connection connection,
                                String login, String description, String password, boolean profil) throws Exception {
    String sql = String.format(INSERT_USER, schemaName, tableName);
    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
      stmt.setString(1, login);
      stmt.setString(2, description);
      stmt.setString(3, password);
      stmt.setTimestamp(4, Instant.now()); // Peut être null
      stmt.setBoolean(5, profil);
      stmt.executeUpdate();
      System.out.println("Row inserted: " + login);
    }
  }

  public static boolean createSchemaIfNotExists(Connection connection, String schemaName)
      throws Exception {
    if (!schemaExists(connection, schemaName)) {
      createSchema(connection, schemaName);
    }
  }
  private static boolean schemaExists(Connection connection, String schemaName) throws Exception {
    try (PreparedStatement stmt = connection.prepareStatement(CHECK_SCHEMA_EXISTS_SQL)) {
      stmt.setString(1, schemaName);
      try (ResultSet rs = stmt.executeQuery()) {
        if (rs.next()) {
          return rs.getBoolean(1);
        }
      }
    }
    return false;
  }

  private static void createSchema(Connection connection, String schemaName) throws Exception {
    String sql = String.format(CREATE_SCHEMA_SQL, schemaName);
    try (Statement stmt = connection.createStatement()) {
      stmt.execute(sql);
      System.out.println("Schema created: " + schemaName);
    }
  }

  // Méthode pour créer une table
  public static void createTable(Connection connection, String query) throws Exception {

    try (Statement stmt = connection.createStatement()) {
      stmt.execute(query);
      System.out.println("Table created: " + query);
    }
  }


  public static void insertDefautContract(Connection connection) {// TODO
  }
}
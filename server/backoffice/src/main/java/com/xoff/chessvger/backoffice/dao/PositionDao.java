package com.xoff.chessvger.backoffice.dao;

import com.xoff.chessvger.backoffice.position.PositionEntity;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class PositionDao {

  private static String SCHEMA = "tenant_admin";
  private static final String INSERT_SQL = "INSERT INTO "+SCHEMA+".position_games (id,position) VALUES (?,?)";
  private static final String INSERT_LIST_SQL =
      "INSERT INTO "+SCHEMA+".position_entity_games (position_entity_id, games) VALUES (?, ?)";

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

  public void insertEntity(PositionEntity entity) throws SQLException {
    Connection connection = null;
    PreparedStatement insertEntityStmt = null;
    PreparedStatement insertListStmt = null;

    try {
      // 1. Créer la connexion à la base de données
      connection = CommonDao.getConnection();
      connection.setAutoCommit(false);

      insertEntityStmt =
          connection.prepareStatement(INSERT_SQL, PreparedStatement.RETURN_GENERATED_KEYS);
      insertEntityStmt.setLong(1, entity.getId());
      insertEntityStmt.setLong(2, entity.getPosition());
      insertEntityStmt.executeUpdate();

      try (var generatedKeys = insertEntityStmt.getGeneratedKeys()) {
        if (generatedKeys.next()) {
          long generatedId = generatedKeys.getLong(1);
          entity.setId(generatedId);

          // 4. Insérer les éléments de la liste dans la table associée
          insertListStmt = connection.prepareStatement(INSERT_LIST_SQL);
          List<Long> games = entity.getGames();
          for (Long value : games) {
            insertListStmt.setLong(1, generatedId);
            insertListStmt.setLong(2, value);
            insertListStmt.addBatch(); // Préparer un batch d'inserts
          }
          insertListStmt.executeBatch();
        }
      }

      connection.commit(); // Valider la transaction
    } catch (SQLException e) {
      if (connection != null) {
        connection.rollback(); // Annuler la transaction en cas d'erreur
      }
      throw e;
    } finally {
      if (insertListStmt != null) {
        insertListStmt.close();
      }
      if (insertEntityStmt != null) {
        insertEntityStmt.close();
      }
      if (connection != null) {
        connection.close();
      }
    }
  }
}

package com.xoff.chessvger.backoffice.dao;

import com.xoff.chessvger.backoffice.material.MaterialEntity;
import com.xoff.chessvger.chess.board.CoupleZobristMaterial;
import com.xoff.chessvger.chess.board.IPositionManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import org.postgresql.shaded.com.ongres.scram.common.exception.ScramServerErrorException;

public class MaterialDao {


  private static final String INSERT_SQL = "INSERT INTO  %s.material_games (id,position) VALUES (?,?)";
  private static final String INSERT_LIST_SQL =
      "INSERT INTO  %s.material_entity_games (material_entity_id, games) VALUES (?, ?)";

  public static void insert(Connection connection,String schemaName,Long gameId, List<CoupleZobristMaterial> list) {

    on ajoute juste des mateial-id, game-id

    upsert la position avec le game
    PreparedStatement insertEntityStmt = null;
    PreparedStatement insertListStmt = null;

    try {
      String sql = String.format(INSERT_SQL, schemaName);
      PreparedStatement preparedStatement = connection.prepareStatement(sql);
      insertEntityStmt =
          connection.prepareStatement(INSERT_SQL, PreparedStatement.RETURN_GENERATED_KEYS);
      insertEntityStmt.setLong(1, gameId);
      insertEntityStmt.executeUpdate();

      // 3. Récupérer l'ID généré
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

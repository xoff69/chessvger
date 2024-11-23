package com.xoff.chessvger.queues.material;

import com.xoff.chessvger.queues.position.PositionEntity;
import com.xoff.chessvger.queues.util.CommonDao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class MaterialDao {
 // position_games
  //position_entity_games



    private static final String INSERT_SQL = "INSERT INTO ExampleEntity (someLongValue) VALUES (?)";
    private static final String INSERT_LIST_SQL = "INSERT INTO ExampleEntity_IntegerList (exampleEntity_id, integer_value) VALUES (?, ?)";

    public void insertEntity(PositionEntity entity) throws SQLException {
      Connection connection = null;
      PreparedStatement insertEntityStmt = null;
      PreparedStatement insertListStmt = null;

      try {
        // 1. Créer la connexion à la base de données
        connection = CommonDao.getInstance().getConnection();
        connection.setAutoCommit(false); // Permet de contrôler la transaction manuellement

        // 2. Préparer la requête SQL pour insérer dans ExampleEntity
        insertEntityStmt = connection.prepareStatement(INSERT_SQL, PreparedStatement.RETURN_GENERATED_KEYS);
        insertEntityStmt.setLong(1, entity.getId());
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

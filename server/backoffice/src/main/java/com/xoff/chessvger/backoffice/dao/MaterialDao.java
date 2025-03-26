package com.xoff.chessvger.backoffice.dao;

import com.xoff.chessvger.chess.board.CoupleZobristMaterial;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Slf4j
public class MaterialDao {


  private static final String INSERT_SQL = "INSERT INTO  %s.material_games (value,game_id) VALUES (?,?)";


  public static void insert(Connection connection,String schemaName,Long gameId, List<CoupleZobristMaterial> list)
      throws SQLException {

    PreparedStatement insertEntityStmt = null;
    String sql = String.format(INSERT_SQL, schemaName);
    try {

      insertEntityStmt =
          connection.prepareStatement(sql);
      for (CoupleZobristMaterial coupleZobristMaterial : list) {
        insertEntityStmt.setLong(1, gameId);
        insertEntityStmt.setLong(2, coupleZobristMaterial.getMaterial());
        insertEntityStmt.executeUpdate();
      }

      connection.commit();   insertEntityStmt.close();
    } catch (SQLException e) {
      log.error(sql, e);
    }

    }
}

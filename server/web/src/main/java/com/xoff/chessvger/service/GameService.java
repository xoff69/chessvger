package com.xoff.chessvger.service;
import com.xoff.chessvger.chess.game.CommonGame;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class GameService {

  private final JdbcTemplate jdbcTemplate;

  private final RowMapper<CommonGame> rowMapper = (rs, rowNum) -> {
    CommonGame game = new CommonGame();
    game.setId(rs.getLong("id"));
    game.setEvent(rs.getString("event"));
    game.setSite(rs.getString("site"));
    game.setPartieAnalysee(rs.getBoolean("partie_analysee"));
    game.setDate(rs.getDate("date"));
    game.setEventDate(rs.getDate("event_date"));
    game.setRound(rs.getString("round"));
    game.setResult(rs.getString("result"));
    game.setWhitePlayer(rs.getString("white_player"));
    game.setBlackPlayer(rs.getString("black_player"));
    game.setWhiteTitle(rs.getString("white_title"));
    game.setBlackTitle(rs.getString("black_title"));
    game.setWhiteElo(rs.getInt("white_elo"));
    game.setBlackElo(rs.getInt("black_elo"));
    game.setEco(rs.getString("eco"));
    game.setOpening(rs.getString("opening"));
    game.setWhiteFideId(rs.getLong("white_fide_id"));
    game.setBlackFideId(rs.getLong("black_fide_id"));
    game.setNbcoups(rs.getInt("nb_coups"));
    game.setLastPosition(rs.getInt("last_position"));
    game.setInformationsFaitDeJeu(rs.getLong("informations_fait_de_jeu"));
    game.setLastUpdate(rs.getLong("last_update"));
    game.setDeleted(rs.getBoolean("is_deleted"));
    game.setFirstMove(rs.getString("first_move"));
    game.setMoves(rs.getString("moves"));
    game.setInteret(rs.getInt("interet"));
    game.setTheorique(rs.getBoolean("theorique"));
    game.setFavori(rs.getBoolean("favori"));
    return game;
  };

  public long count() {
    return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM common_game", Long.class);
  }

  public Optional<CommonGame> findById(Long id) {
    List<CommonGame> games = jdbcTemplate.query(
            "SELECT * FROM main.common_game WHERE id = ?",
            rowMapper,
            id
    );
    return games.stream().findFirst();
  }

  public Page<CommonGame> findAll(Pageable pageable) {
    log.info("findAll");
    long total = count();
    List<CommonGame> games = jdbcTemplate.query(
            "SELECT * FROM common_game LIMIT ? OFFSET ?",
            rowMapper,
            pageable.getPageSize(),
            pageable.getOffset()
    );
    return new PageImpl<>(games, pageable, total);
  }
}

package com.xoff.chessvger.service;

import com.xoff.chessvger.dao.UtilDao;
import com.xoff.chessvger.model.CommonPlayer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PlayerServiceImpl implements IPlayerService {

    private final JdbcTemplate jdbcTemplate;

    /**
     * TODO
     * @param name
     * @return
     */
    private List<String> variantes(String name) {
        List<String> resultat = new ArrayList();
        //    if (name.contains("rj")) {
        resultat.add(name.replace("rj", "ri"));
        resultat.add(name.replace("ov", "of"));
        resultat.add(name.replace("ge", "gue"));
        resultat.add(name.replace("ck", "ch"));
        resultat.add(name.replace("ikt", "ict"));
        resultat.add(name.replace("ik", "ick"));
        resultat.add(name.replace("gu", "gou"));
        // }

        return resultat;
    }
    private final RowMapper<CommonPlayer> playerRowMapper = (rs, rowNum) -> {
        CommonPlayer player = new CommonPlayer();
        player.setId(rs.getLong("id"));
        player.setFideId(rs.getString("fide_id"));      // Adapte le nom de la colonne si nécessaire
        player.setName(rs.getString("name"));
        player.setCountry(rs.getString("country"));
        player.setSex(rs.getString("sex"));
        player.setTitle(rs.getString("title"));
        player.setWTitle(rs.getString("w_title"));       // Attention aux conventions de nommage dans ta table
        player.setOTitle(rs.getString("o_title"));       // Vérifie si c'est "o_title" ou "otitle"
        player.setFoaTitle(rs.getString("foa_title"));
        player.setRating(rs.getString("rating"));
        player.setGames(rs.getString("games"));
        player.setK(rs.getString("k"));
        player.setRapidRating(rs.getString("rapid_rating"));
        player.setRapidGames(rs.getString("rapid_games"));
        player.setRapidK(rs.getString("rapidk"));
        player.setBlitzRating(rs.getString("blitz_rating"));
        player.setBlitzGames(rs.getString("blitz_games"));
        player.setBlitzK(rs.getString("blitzk"));
        player.setBirthday(rs.getString("birthday"));
        player.setFlag(rs.getString("flag"));
        return player;
    };

    public Page<CommonPlayer> findAll(Pageable pageable) {
        // Adaptation de la requête selon ta structure et ton schéma si besoin (ici table "common_player" dans le schéma par défaut)
        String sql = UtilDao.getAllPaginatedQuery("common_player");
        List<CommonPlayer> players = jdbcTemplate.query(
                sql,
                playerRowMapper,
                pageable.getPageSize(),
                pageable.getOffset()
        );
        Long total = count();
        return new PageImpl<>(players, pageable, total);
    }

    @Override
    public Long count() {
        return jdbcTemplate.queryForObject(UtilDao.getCountQuery("common_player"), Long.class);
    }

    @Override
    public CommonPlayer findById(Long id) {

        String sql = UtilDao.findById("common_player");
        List<CommonPlayer> players = jdbcTemplate.query(sql, playerRowMapper, id);
        return players.stream().findFirst()
                .orElseThrow(() -> new RuntimeException("Player not found with id: " + id));
    }
}

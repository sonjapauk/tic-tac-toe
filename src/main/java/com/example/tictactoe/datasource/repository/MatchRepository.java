package com.example.tictactoe.datasource.repository;

import com.example.tictactoe.datasource.model.DataMatch;
import com.example.tictactoe.datasource.model.DataUserRating;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MatchRepository extends CrudRepository<DataMatch, String> {
    @Query("SELECT m.id FROM DataMatch m WHERE m.state = :state")
    List<String> findIdsByState(@Param("state") String state);

    @Query("SELECT m.game.currentID FROM DataMatch m WHERE m.id = :matchId")
    String findGameIdById(@Param("matchId") String matchId);

    @Query("""
                SELECT m.id
                FROM DataMatch m
                WHERE (m.x = :playerId OR m.o = :playerId)
                    AND m.state IN ('DRAW', 'WIN_PLAYER_O', 'WIN_PLAYER_X')
            """)
    List<String> findFinishedIdsByPlayerId(@Param("playerId") String playerId);

    @Query(value = """
            WITH rate AS (
            SELECT u.id,
                COUNT(m.id) FILTER (
                WHERE
                    (u.id = m.x_player AND m.state = 'WIN_PLAYER_X') OR
                    (u.id = m.o_player AND m.state = 'WIN_PLAYER_O')
                ) AS win,
                COUNT(m.id) FILTER (
                WHERE
                    m.state IN ('WIN_PLAYER_X', 'WIN_PLAYER_O', 'DRAW')
                ) AS total
            FROM users u
            LEFT JOIN matches m
            ON u.id = m.x_player OR u.id = m.o_player
            GROUP BY u.id
            )
                        
            SELECT id,
                CASE
                    WHEN total = 0 OR win = 0 THEN 0
                    WHEN total = win THEN 1
                    ELSE ROUND(rate.win * 1.0 / (rate.total - rate.win), 2)
                END AS ratio
            FROM rate
            ORDER BY ratio DESC, id
            LIMIT :N
            """, nativeQuery = true)
    List<DataUserRating> findTopByWinRate(@Param("N") int N);
}

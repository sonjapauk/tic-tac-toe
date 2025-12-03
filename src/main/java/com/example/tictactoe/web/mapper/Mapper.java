package com.example.tictactoe.web.mapper;

import com.example.tictactoe.domain.model.*;
import com.example.tictactoe.domain.model.field.Field;
import com.example.tictactoe.domain.model.game.Game;
import com.example.tictactoe.domain.model.match.Match;
import com.example.tictactoe.domain.model.match.MatchState;
import com.example.tictactoe.web.model.*;

import java.util.UUID;

public class Mapper {
    public static Game toDomain(WebGame game, String id) {
        return new Game(new Field(game.getField().deepCopy()), UUID.fromString(id));
    }

    public static WebGame toWeb(Game game) {
        return new WebGame(new WebField(game.getField().deepCopy()), game.getState().toString());
    }

    public static Match toDomain(WebMatch match, String id) {
        return new Match(UUID.fromString(match.getId()),
                toDomain(match.getGame(), id),
                match.getIdX() == null ? null : UUID.fromString(match.getIdX()),
                match.getIdO() == null ? null : UUID.fromString(match.getIdO()),
                MatchState.fromString(match.getState()),
                match.getCreatedAt());
    }

    public static WebMatch toWeb(Match match) {
        return new WebMatch(match.getId().toString(),
                toWeb(match.getGame()),
                match.hasX() ? match.getX().toString() : null,
                match.hasO() ? match.getO().toString() : null,
                match.getState().name(),
                match.getCreatedAt());
    }

    public static WebUser toWeb(User user) {
        return new WebUser(user.getId().toString(), user.getLogin(), user.getRoles().stream().map(Role::name).toList());
    }

    public static WebUserRating toWeb(UserRating rating) {
        return new WebUserRating(rating.getUserId().toString(), rating.getRate());
    }
}

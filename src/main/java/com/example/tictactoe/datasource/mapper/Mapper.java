package com.example.tictactoe.datasource.mapper;

import com.example.tictactoe.datasource.model.*;
import com.example.tictactoe.domain.model.*;
import com.example.tictactoe.domain.model.field.Field;
import com.example.tictactoe.domain.model.game.Game;
import com.example.tictactoe.domain.model.match.Match;
import com.example.tictactoe.domain.model.match.MatchState;

import java.util.UUID;

public class Mapper {
    public static DataGame toDataSource(Game game) {
        return new DataGame(new DataField(game.getField().deepCopy()), game.getCurrentID().toString());
    }

    public static Game toDomain(DataGame game) {
        return new Game(new Field(game.getField().deepCopy()), UUID.fromString(game.getCurrentID()));
    }

    public static DataUser toDataSource(User user) {
        return new DataUser(user.getId().toString(), user.getLogin(), user.getPassword(), user.getRoles().stream().map(Role::name).toList());
    }

    public static User toDomain(DataUser user) {
        return new User(UUID.fromString(user.getId()), user.getLogin(), user.getPassword(), user.getRoles().stream().map(Role::fromString).toList());
    }

    public static DataMatch toDataSource(Match match) {
        return new DataMatch(
                match.getId().toString(),
                toDataSource(match.getGame()),
                match.hasX() ? match.getX().toString() : null,
                match.hasO() ? match.getO().toString() : null,
                match.getState().name(),
                match.getCreatedAt()
        );
    }

    public static Match toDomain(DataMatch match) {
        return new Match(
                UUID.fromString(match.getId()),
                toDomain(match.getGame()),
                match.getX() == null ? null : UUID.fromString(match.getX()),
                match.getO() == null ? null : UUID.fromString(match.getO()),
                MatchState.fromString(match.getState()),
                match.getCreatedAt()
        );
    }

    public static UserRating toDomain(DataUserRating rating) {
        return new UserRating(UUID.fromString(rating.getId()), rating.getRatio());
    }

    public static void updateDataMatch(DataMatch dataMatch, Match match) {
        dataMatch.setId(match.getId().toString());
        dataMatch.setGame(toDataSource(match.getGame()));
        dataMatch.setX(match.hasX() ? match.getX().toString() : null);
        dataMatch.setO(match.hasO() ? match.getO().toString() : null);
        dataMatch.setState(match.getState().name());
    }
}

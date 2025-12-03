package com.example.tictactoe.web.controller;

import com.example.tictactoe.domain.model.game.Game;
import com.example.tictactoe.domain.model.match.Match;
import com.example.tictactoe.domain.model.Player;
import com.example.tictactoe.domain.service.MatchService;
import com.example.tictactoe.web.mapper.Mapper;
import com.example.tictactoe.security.CurrentUser;
import com.example.tictactoe.web.model.WebUserRating;
import com.example.tictactoe.web.model.request.StartRequest;
import com.example.tictactoe.web.model.WebGame;
import com.example.tictactoe.web.model.WebMatch;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
public class MatchController {
    private final MatchService service;

    public MatchController(MatchService service) {
        this.service = service;
    }

    @PostMapping("/game")
    public ResponseEntity<WebMatch> start(@RequestBody StartRequest gameParams) {
        Match newMatch = service.createMatch(CurrentUser.getId(), gameParams.vsComputer(), Player.fromString(gameParams.chosenMark()));
        URI location = URI.create("/game/" + newMatch.getId().toString());
        return ResponseEntity.created(location).body(Mapper.toWeb(newMatch));
    }

    @GetMapping("/join")
    public ResponseEntity<List<UUID>> getAvailableGames() {
        return ResponseEntity.ok(service.getAvailableGames());
    }

    @PostMapping("/join/{id}")
    public ResponseEntity<WebMatch> joinGame(@PathVariable String id) {
        service.join(UUID.fromString(id), CurrentUser.getId());
        return ResponseEntity.ok(Mapper.toWeb(service.getMatchById(UUID.fromString(id))));
    }

    @PostMapping("/game/{id}")
    public ResponseEntity<WebMatch> play(@PathVariable String id, @RequestBody WebGame userMove) {
        UUID matchId = UUID.fromString(id);
        UUID gameId = service.getGameId(matchId);
        Game domainUserMove = Mapper.toDomain(userMove, gameId.toString());

        service.validateMove(domainUserMove, CurrentUser.getId(), matchId);
        Match updated = service.applyMove(matchId, domainUserMove);

        if (service.vsBot(updated) && !service.isOver(updated)) {
            updated = service.makeMove(matchId);
        }

        return ResponseEntity.ok(Mapper.toWeb(updated));
    }

    @GetMapping("/game/{id}")
    public ResponseEntity<WebMatch> getGame(@PathVariable String id) {
        return ResponseEntity.ok(Mapper.toWeb(service.getMatchById(UUID.fromString(id))));
    }

    @GetMapping("/game/finished")
    public ResponseEntity<List<UUID>> getFinishedGames() {
        return ResponseEntity.ok(service.getFinishedGames(CurrentUser.getId()));
    }

    @GetMapping("/game/best-players")
    public ResponseEntity<List<WebUserRating>> getBestPlayers(@RequestParam int N) {
        return ResponseEntity.ok(service.getBestPlayers(N).stream().map(Mapper::toWeb).toList());
    }
}

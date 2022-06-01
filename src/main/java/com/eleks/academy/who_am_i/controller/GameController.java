package com.eleks.academy.who_am_i.controller;

import com.eleks.academy.who_am_i.model.request.CharacterSuggestion;
import com.eleks.academy.who_am_i.model.request.NewGameRequest;
import com.eleks.academy.who_am_i.model.response.GameDetails;
import com.eleks.academy.who_am_i.model.response.GameLight;
import com.eleks.academy.who_am_i.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.eleks.academy.who_am_i.utils.StringUtils.Headers.PLAYER;

@RestController
@RequestMapping("/games")
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public GameDetails createGame(@RequestHeader(PLAYER) String player, @Valid @RequestBody NewGameRequest gameRequest) {
        return this.gameService.createGame(player, gameRequest);
    }

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public List<GameLight> findAvailableGames (@RequestHeader(PLAYER) String player) {
        return this.gameService.findAvailableGames(player);
    }

    @GetMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<GameDetails> findById(@PathVariable("id") String id, @RequestHeader(PLAYER) String player) {
        return this.gameService.findByIdAndPlayer(id, player)
                        .map(ResponseEntity::ok)
                        .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}/characters")
    @ResponseStatus(code = HttpStatus.OK)
    public void suggestCharacter(@PathVariable("id") String id, @RequestHeader(PLAYER) String player, @Valid @RequestBody CharacterSuggestion suggestion) {
        this.gameService.suggestCharacter(id, player, suggestion);
    }

    @PostMapping("/{id}/players")
    @ResponseStatus(code = HttpStatus.OK)
    public void enrollToGame(@PathVariable("id") String id, @RequestHeader(PLAYER) String player) {
        this.gameService.startGame(id, player).map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}

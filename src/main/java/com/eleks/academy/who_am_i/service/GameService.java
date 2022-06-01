package com.eleks.academy.who_am_i.service;

import com.eleks.academy.who_am_i.model.request.CharacterSuggestion;
import com.eleks.academy.who_am_i.model.request.NewGameRequest;
import com.eleks.academy.who_am_i.model.response.GameDetails;
import com.eleks.academy.who_am_i.model.response.GameLight;

import java.util.List;
import java.util.Optional;

public interface GameService {
    List<GameLight> findAvaialbleGames(String player);

    GameDetails createGame(String player, NewGameRequest gameRequest);

    void enrollToGame(String id, String player);

    Optional<GameDetails> findByIdAndPlayer(String id, String player);

    void suggestCharacter(String id, String player, CharacterSuggestion suggestion);

    Optional<GameDetails> startGame(String id, String player);

}

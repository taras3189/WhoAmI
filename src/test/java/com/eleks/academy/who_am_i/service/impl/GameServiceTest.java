package com.eleks.academy.who_am_i.service.impl;

import com.eleks.academy.who_am_i.core.SynchronousGame;
import com.eleks.academy.who_am_i.core.exception.GameException;
import com.eleks.academy.who_am_i.core.impl.PersistentGame;
import com.eleks.academy.who_am_i.model.request.CharacterSuggestion;
import com.eleks.academy.who_am_i.model.request.NewGameRequest;
import com.eleks.academy.who_am_i.model.response.GameDetails;
import com.eleks.academy.who_am_i.model.response.GameLight;
import com.eleks.academy.who_am_i.repository.impl.GameInMemoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GameServiceTest {

    private final GameInMemoryRepository mockGameRepository = mock(GameInMemoryRepository.class);
    private final GameServiceImpl gameService = new GameServiceImpl(mockGameRepository);
    private final NewGameRequest gameRequest = new NewGameRequest();

    @BeforeEach
    void set() {
        gameRequest.setMaxPlayers(2);
    }

    @Test
    void findAvailableGames() throws Exception {
        final String player = "player";
        Stream<SynchronousGame> games = Stream.empty();

        when(mockGameRepository.findAllAvailable(player)).thenReturn(games);

        List<GameLight> listOfGames = gameService.findAvailableGames(player);

        assertThat(listOfGames).isNotNull();
        assertThat(listOfGames).isEmpty();

        verify(mockGameRepository, times(1)).findAllAvailable(eq(player));
    }

    @Test
    void createGame() {
        final String player = "player";
        final SynchronousGame game = new PersistentGame(player, gameRequest.getMaxPlayers());

        when(mockGameRepository.save(any(SynchronousGame.class))).thenReturn(game);

        GameDetails createdGame = gameService.createGame(player, gameRequest);

        assertThat(createdGame).isNotNull();
        assertThat(createdGame.getId()).isEqualTo(game.getId());

        verify(mockGameRepository, times(1)).save(any(SynchronousGame.class));
    }

    @Test
    void findByIdAndPlayer() {
        final String player = "player";
        final String id = "12345";
        Optional<SynchronousGame> myOptional = Optional.of(new PersistentGame(player, gameRequest.getMaxPlayers()));

        when(mockGameRepository.findById(id)).thenReturn(myOptional);

        Optional<GameDetails> game = gameService.findByIdAndPlayer(id, player);

        assertThat(game).isNotNull();
        assertThat(game).isNotEmpty();
        assertThat(game).isEqualTo(myOptional.map(GameDetails::of));

        verify(mockGameRepository, times(1)).findById(id);
    }

    @Test
    void enrollToGame_FailedWith_ResponseStatusException() {
        final String player = "player";
        final String player2 = "player2";
        final String id = "12345";
        Optional<SynchronousGame> game = Optional.of(new PersistentGame(player, gameRequest.getMaxPlayers()));

        when(mockGameRepository.findById(id))
                .thenReturn(game)
                .thenThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot enroll to a game"));

        gameService.enrollToGame(id, player2);

        Assertions.assertThrows(ResponseStatusException.class,
                () -> { gameService.enrollToGame(id, player2); },
                "Cannot enroll to a game");

        verify(mockGameRepository, times(2)).findById(eq(id));
    }

    @Test
    void enrollToGame_FailedWith_GameException() {
        final String player = "player";
        final String id = "12345";
        Optional<SynchronousGame> game = Optional.of(new PersistentGame(player, gameRequest.getMaxPlayers()));

        when(mockGameRepository.findById(id)).thenReturn(game);

        Assertions.assertThrows(GameException.class,
                () -> { gameService.enrollToGame(id, player); });

        verify(mockGameRepository, times(1)).findById(eq(id));
    }

    @Test
    void enrollToGame() {
        final String player = "player";
        final String player2 = "player2";
        final String id = "12345";
        Optional<SynchronousGame> myOptional = Optional.of(new PersistentGame(player, gameRequest.getMaxPlayers()));

        when(mockGameRepository.findById(id)).thenReturn(myOptional);

        gameService.enrollToGame(id, player2);

        verify(mockGameRepository, times(1)).findById(eq(id));
    }

    @Test
    void suggestCharacter() {
        final String player = "player";
        final String id = "12345";
        CharacterSuggestion character = new CharacterSuggestion("char");
        Optional<SynchronousGame> game = Optional.of(new PersistentGame(player, gameRequest.getMaxPlayers()));

        when(mockGameRepository.findById(id)).thenReturn(game);

        gameService.suggestCharacter(id, player, character);

        verify(mockGameRepository, times(1)).findById(id);
    }

}
package com.eleks.academy.who_am_i.core.state;

import com.eleks.academy.who_am_i.core.SynchronousPlayer;
import com.eleks.academy.who_am_i.core.exception.GameException;
import com.eleks.academy.who_am_i.core.impl.Answer;
import com.eleks.academy.who_am_i.core.impl.PersistentPlayer;
import org.mockito.stubbing.Answer;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


public class WaitingForPlayers extends AbstractGameState {

    private final Map<String, SynchronousPlayer> players;

    public WaitingForPlayers(int maxPlayers) {
        super(0, maxPlayers);
        this.players = new HashMap<>(maxPlayers);
    }

    private WaitingForPlayers(int maxPlayers, Map<String, SynchronousPlayer> players) {
        super(players.size(), maxPlayers);
        this.players = players;
    }

    @Override
    public GameState next() {
        return new SuggestingCharacters(players);
    }

    @Override
    public GameState makeTurn(Answer answer) {
        Map<String, SynchronousPlayer> nextPlayers = new HashMap<>(players);

        if (nextPlayers.containsKey(answer.getPlayer())) {
            throw new GameException("Cannot enroll to the game");
        } else {
            nextPlayers.put(answer.getPlayer(), new PersistentPlayer(answer.getPlayer()));
        }
        if (players.size() == getMaxPlayers()) {
            return new SuggestingCharacters(players);
        } else {
            return new WaitingForPlayers(getMaxPlayers(), nextPlayers);
        }
    }

    @Override
    public Optional<SynchronousPlayer> findPlayer (String player) {
        return Optional.ofNullable(players.get(player));
    }

}

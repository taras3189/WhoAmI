package com.eleks.academy.who_am_i.core.impl;

import com.eleks.academy.who_am_i.core.Game;
import com.eleks.academy.who_am_i.core.Player;
import com.eleks.academy.who_am_i.core.SynchronousGame;
import com.eleks.academy.who_am_i.core.SynchronousPlayer;

import java.time.Instant;
import java.util.Optional;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Function;

public class PersistentGame implements Game, SynchronousGame {

    private final Lock turnLock = new ReentrantLock();

    private final String id;

    private final Queue<GaneState> turns = new LinkedBlockingQueue<>();

    /**
     * Creates a new game (game room) and makes a first enrolment turn by a current player
     * so that he won't have to enroll to the game he created
     *
     * @param hostPlayer player to initiate a new game
     */
    public PersistentGame(String hostPlayer, Integer maxPlayers) {
        this.id = String.format("%d-%d",
                Instant.now().toEpochMilli(),
                Double.valueOf(Math.random() * 999).intValue());
        this.turns.add(GameState.start(hostPlayer, maxPlayers));
    }

    @Override
    public Optional<SynchronousPlayer> findPlayer(String player) {
        return this.applyIfPresent(this.turns.peek(), gameState -> gameState.findPlayer(player));
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public boolean isAvailable() {
        return this.turns.peek() instanceof WaitingForPlayers;
    }

    @Override
    public String getTurn() {
        return this.applyIfPresent(this.turns.peek(), GameState::getCurrentTurn);
    }

    @Override
    public String getStatus() {
        return this.applyIfPresent(this.turns.peek(), GameState::getStatus);
    }

    @Override
    public String getPlayersInGame() {
        Function<GameState, String> playersCountExtractor = gameState ->
                "%d/%d".formatted(gameState.getPlayersInGame(), gameState.getMaxPlayers());
        return this.applyIfPresent(this.turns.peek(), playersCountExtractor);
    }

    @Override
    public boolean isFinished() {
        return this.turns.isEmpty();
    }

    @Override
    public void changeTurn() {

    }

    @Override
    public void initGame() {

    }

    @Override
    public void play() {

    }

    @Override
    private <T, R> R applyIfPresent(T source, Function<T, R> mapper) {
        return this.applyIfPresent(source, mapper, null);
    }

    @Override
    private <T, R> R applyIfPresent(T source, Function<T, R> mapper, R fallback) {
        return Optional.ofNullable(source)
                .map(mapper)
                .orElse(fallback);
    }

}

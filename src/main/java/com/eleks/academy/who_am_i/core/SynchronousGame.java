package com.eleks.academy.who_am_i.core;

import org.mockito.stubbing.Answer;

import java.util.Optional;

public interface SynchronousGame {

    Optional<SynchronousPlayer> findPlayer(String player);

    String getId();

    String getPlayersInGame();

    String getStatus();

    boolean isAvailable();

    void makeTurn(Answer answer);

    String getTurn();

}

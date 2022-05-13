package com.eleks.academy.who_am_i.core;

public interface Game {

	void addPlayer(Player player);

	void assignCharacters();

	boolean makeTurn();

	boolean isFinished();

	void changeTurn();

	void initGame();

	boolean isPlayerPresent(Player player);

	void play();

}

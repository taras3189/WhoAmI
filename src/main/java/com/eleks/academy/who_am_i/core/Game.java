package com.eleks.academy.who_am_i.core;

public interface Game {
	
	void addPlayer(Player player);
	
	void begin();
	
	boolean makeTurn();
	
	void changeTurn();
	
	void endTurn();
	
	void assignCharacters();
	
	boolean isFinished();	

	void initGame();
	
	void init();
	
	void showPlayers();
	
	int countPlayers();
	
	void showResults();
	
	
	
	

}

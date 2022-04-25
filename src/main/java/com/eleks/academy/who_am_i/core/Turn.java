package com.eleks.academy.who_am_i.core;

import java.util.List;

public interface Turn {

	Player getGuesser();

	List<Player> getOtherPlayers();

	void changeTurn();

}

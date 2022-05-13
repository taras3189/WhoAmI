package com.eleks.academy.who_am_i.networking.server;

import com.eleks.academy.who_am_i.core.Game;

import java.io.IOException;

public interface Server {

	Game startGame() throws IOException;

	void waitForPlayer() throws IOException;

	void stop();

}

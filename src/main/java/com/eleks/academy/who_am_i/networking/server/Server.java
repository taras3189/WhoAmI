package com.eleks.academy.who_am_i.networking.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;

import com.eleks.academy.who_am_i.core.Game;
import com.eleks.academy.who_am_i.core.Player;

public interface Server {

	Game startGame() throws IOException;

	Socket waitForPlayer(Game game) throws Exception;

	void addPlayer(Player player);

	void stopServer(Socket clientSocket, BufferedReader reader) throws IOException;

}

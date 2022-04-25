package com.eleks.academy.who_am_i.networking.server;

import com.eleks.academy.who_am_i.core.Game;
import com.eleks.academy.who_am_i.core.Player;
import com.eleks.academy.who_am_i.core.impl.RandomGame;
import com.eleks.academy.who_am_i.networking.client.ClientPlayer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

public class ServerImpl implements Server {

	private final ServerSocket serverSocket;
	private final List<Player> clientPlayers;
	private final int players;

	private List<String> characters = List.of("Spiderman", "Batman", "Superman", "Jackie Chan");
	private List<String> questions = List.of("Am I a human?", "Am I a character from a movie?", "Am I a hero?", "Am I a looser?");
	private List<String> guessess = List.of("Spiderman", "Batman", "Superman", "Jackie Chan");


	public ServerImpl(int port, int players) throws IOException {
		this.serverSocket = new ServerSocket(port);
		this.players = players;
		this.clientPlayers = new ArrayList<>(players);
	}

	@Override
	public Game startGame() throws IOException {

		RandomGame game = new RandomGame(clientPlayers, characters);
		game.initGame();
		return game;
	}

	@Override
	@PostConstruct
	public void waitForPlayer() throws IOException {
		System.out.println("Server starts");
		System.out.println("Waiting for a client connect...");
		for (int i = 0; i < players; i++) {
			ClientPlayer clientPlayer = new ClientPlayer(serverSocket.accept());
			clientPlayers.add(clientPlayer);
		}
		System.out.println(String.format("Got %d players. Starting a game.", players));
	}

	@Override
	@PreDestroy
	public void stop() {
		for (Player player : clientPlayers) {
			try {
				player.close();
			} catch (Exception e) {
				System.err.println(String.format("Could not close a socket (%s)", e.getMessage()));
			}
		}

	}
}

package com.eleks.academy.who_am_i.networking.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import com.eleks.academy.who_am_i.core.Game;
import com.eleks.academy.who_am_i.core.Player;
import com.eleks.academy.who_am_i.core.impl.RandomGame;
import com.eleks.academy.who_am_i.core.impl.RandomPlayer;

public class ServerImpl implements Server {	

	private RandomGame game = new RandomGame(characters);	
	
	private final ServerSocket serverSocket;
	
	private Game game;
	private List<String> characters = List.of("Spiderman", "Batman", "Superman", "Jackie Chan");
	private List<String> guessess = List.of("Spiderman", "Batman", "Superman", "Jackie Chan");
	private List<String> questions = List.of("Am I a human?", "Am I a character from a movie?", "Am I a hero?", "Am I a looser?");
	
	private List<Player> players = List.of(new RandomPlayer("Player №1", guesses, questions),
			new RandomPlayer("Player №2", guesses, questions),
			new RandomPlayer("AI Player Bot №3", guesses, questions),
			new RandomPlayer("AI Player Bot №4", guesses, questions),
			
	

	public ServerImpl(int port) throws IOException {
		this.serverSocket = new ServerSocket(port);
	}

	@Override
	public Game startGame() throws IOException {
		game.addPlayer(new RandomPlayer("AI Player", guesses, questions));		
		game = new RandomGame(characters, players);
		System.out.println("Server starts\nWaiting for a players...");
		
		while(game.countPlayers() < 4) {
			Socket socket = waitForPlayers(game);
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String playerName = bufferedReader.readLine();
			addPlayer(new ClientPlayer(playerName, socket));
		}
		return game;
	}

	@Override
	public Socket waitForPlayer(Game game) throws IOException {
		return serverSocket.accept();
	}	

}

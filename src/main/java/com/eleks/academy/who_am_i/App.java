package com.eleks.academy.who_am_i;

<<<<<<< HEAD
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.eleks.academy.who_am_i.core.Game;
import com.eleks.academy.who_am_i.networking.client.ClientPlayer;
import com.eleks.academy.who_am_i.networking.server.ServerImpl;
=======
import com.eleks.academy.who_am_i.configuration.ContextConfig;
import com.eleks.academy.who_am_i.networking.server.Server;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
>>>>>>> 2cdf8eb (Add files via upload)

public class App {

	public static void main(String[] args) throws IOException {

<<<<<<< HEAD
		ServerImpl server = new ServerImpl(888);

		Game game = server.startGame();

		var socket = server.waitForPlayer(game);

		BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

		boolean gameStatus = true;

		var playerName = reader.readLine();

		server.addPlayer(new ClientPlayer(playerName, socket));

		game.assignCharacters();

		game.initGame();
		
		game.init();

		while (gameStatus) {
			boolean turnResult = game.makeTurn();

			while (turnResult) {
				turnResult = game.makeTurn();
			}
			game.changeTurn();
			gameStatus = !game.isFinished();
		}

		server.stopServer(socket, reader);
=======
		ApplicationContext applicationContext = new AnnotationConfigApplicationContext(ContextConfig.class);

		Server server = applicationContext.getBean(Server.class);


		server.startGame().play();


>>>>>>> 2cdf8eb (Add files via upload)
	}

}

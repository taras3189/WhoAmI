package com.eleks.academy.who_am_i.core.impl;

import com.eleks.academy.who_am_i.core.Game;
import com.eleks.academy.who_am_i.core.Player;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

import static org.junit.jupiter.api.Assertions.*;

public class RandomGameTest {

	@Test
	void askAllPlayersForCharacterSuggestions() {
		var game = new RandomGame(List.of(), List.of("c"));
		TestPlayer p1 = new TestPlayer("P1");
		TestPlayer p2 = new TestPlayer("P2");
		p1.character = "Batman";
		p2.character = "Superman";

		game.addPlayer(p1);
		game.addPlayer(p2);

		game.assignCharacters();
		assertAll(() -> assertTrue(p1.suggested),
				() -> assertTrue(p2.suggested));
	}

	@Test
	void notAddedPlayerWhenFailedSuggestion() {
		Game game = new RandomGame(List.of(), List.of("c"));
		TestPlayer p1 = new TestPlayer("P1");
		p1.character = "";
		game.addPlayer(p1);
		assertFalse(game.isPlayerPresent(p1));
	}

	private static final class TestPlayer implements Player {
		private final String name;
		private boolean suggested;
		private String character;

		public TestPlayer(String name) {
			super();
			this.name = name;
		}

		@Override
		public Future<String> getName() {
			return CompletableFuture.completedFuture(name);
		}

		@Override
		public Future<String> suggestCharacter() {
			suggested = true;
			return CompletableFuture.completedFuture(character);
		}

		@Override
		public String getQuestion() {
			throw new UnsupportedOperationException();
		}

		@Override
		public Future<String> answerQuestion(String question, String character) {
			throw new UnsupportedOperationException();
		}

		@Override
		public String getGuess() {
			throw new UnsupportedOperationException();
		}

		@Override
		public Future<Boolean> isReadyForGuess() {
			throw new UnsupportedOperationException();
		}

		@Override
		public Future<String> answerGuess(String guess, String character) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void close() {
			// TODO Auto-generated method stub

		}

	}

}

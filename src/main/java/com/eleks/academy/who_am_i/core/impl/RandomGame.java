package com.eleks.academy.who_am_i.core.impl;

import com.eleks.academy.who_am_i.core.Game;
import com.eleks.academy.who_am_i.core.Player;
import com.eleks.academy.who_am_i.core.Turn;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.*;

public class RandomGame implements Game {

	private static final int DURATION = 2;
	private static final TimeUnit UNIT = TimeUnit.MINUTES;
	private static final String YES = "Yes";
	private static final String NO = "No";
	private static final String NO_ANSWER = "Player did not asnwer within ";
	private Map<String, String> playersCharacter = new ConcurrentHashMap<>();
	private List<Player> players = new ArrayList<>();
	private List<String> availableCharacters;
	private Turn currentTurn;

	public RandomGame(List<Player> players, List<String> availableCharacters) {
		this.availableCharacters = new ArrayList<>(availableCharacters);
		this.players = new ArrayList<>(players);
	}

	@Override
	public boolean isPlayerPresent(Player player) {
		return this.players.contains(player);
	}

	@Override
	public void addPlayer(Player player) {
		// TODO: Add test to ensure that player has not been added to the lists when failed to obtain suggestion
		Future<String> maybeCharacter = player.suggestCharacter();
		try {
			String character = maybeCharacter.get(DURATION, UNIT);
			if (!character.isBlank()) {
				this.players.add(player);
				this.availableCharacters.add(character);
			}
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Thread.currentThread().interrupt();
		} catch (TimeoutException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean makeTurn() {
		Player currentGuesser = currentTurn.getGuesser();
		String guessersName = null;
		boolean isReadyGuess = false;

		try {
			guessersName = currentGuesser.getName().get();
			isReadyGuess = currentGuesser.isReadyForGuess().get(DURATION, UNIT);
		} catch (InterruptedException | ExecutionException e) {
			// TODO: Add custom runtime exception implementation
			Thread.currentThread().interrupt();
		} catch (TimeoutException e) {
			throw new PlayerAnswerException(NO_ANSWER + DURATION + " " + UNIT, e);
		}
		List<String> answers;
		String guess = null;
		if (isReadyGuess) {
			try {
				guess = currentGuesser.getGuess().get(DURATION, UNIT);
			} catch (InterruptedException | TimeoutException | ExecutionException e) {
				e.printStackTrace();
				Thread.currentThread().interrupt();
			}
			String finalGuess = guess;
			String finalGuessersName = guessersName;
			answers = currentTurn.getOtherPlayers().stream().parallel()
					.map(player -> {
						try {
							return player.answerGuess(finalGuess, this.playersCharacter.get(finalGuessersName)).get(DURATION, UNIT);
						} catch (InterruptedException | ExecutionException e) {
							e.printStackTrace();
							Thread.currentThread().interrupt();
						} catch (TimeoutException ex) {
							throw new PlayerAnswerException(NO_ANSWER + DURATION + " " + UNIT, ex);
						}
						return null;
					}).toList();

			long positiveCount = answers.stream().filter(YES::equals).count();
			long negativeCount = answers.stream().filter(NO::equals).count();

			boolean win = positiveCount > negativeCount;

			if (win) {
				players.remove(currentGuesser);
			}
			return false;

		} else {
			String question = null;
			try {
				question = currentGuesser.getQuestion().get(DURATION, UNIT);
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
				Thread.currentThread().interrupt();
			} catch (TimeoutException ex) {
				throw new PlayerAnswerException(NO_ANSWER + DURATION + " " + UNIT, ex);
			}
			String finalGuessersName1 = guessersName;
			String finalQuestion = question;
			answers = currentTurn.getOtherPlayers().stream()
					.<String>map(player -> {
						try {
							return player.answerQuestion(finalQuestion, this.playersCharacter.get(finalGuessersName1)).get(DURATION, UNIT);
						} catch (InterruptedException | ExecutionException e) {
							e.printStackTrace();
							Thread.currentThread().interrupt();
						} catch (TimeoutException ex) {
							throw new PlayerAnswerException(NO_ANSWER + DURATION + " " + UNIT, ex);
						}
						return null;

					}).toList();

			long positiveCount = answers.stream().filter(YES::equals).count();
			long negativeCount = answers.stream().filter(NO::equals).count();
			return positiveCount > negativeCount;
		}

	}

	@Override
	public void assignCharacters() {
		players.stream().map(Player::getName).parallel().map(f -> {
			// TODO: extract into a configuration parameters
			try {
				return f.get();
			} catch (InterruptedException | ExecutionException e) {
				Thread.currentThread().interrupt();
				return null;
			}
		}).forEach(name -> this.playersCharacter.put(name, this.getRandomCharacter(name)));
	}

	@Override
	public void initGame() {
		this.currentTurn = new TurnImpl(this.players);
	}

	@Override
	public boolean isFinished() {
		return players.size() == 1;
	}

	private String getRandomCharacter(String name) {
		int randomPos;
		Optional<Player> currentPlayer = players.stream().filter(p -> {
			try {
				return p.getName().get().equals(name);
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
				Thread.currentThread().interrupt();
				return false;
			}
		}).findFirst();
		String character = "";
		if (currentPlayer.isPresent()) {
			try {
				character = currentPlayer.get().suggestCharacter().get();
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
				Thread.currentThread().interrupt();
			}
		}
		int indexOf = availableCharacters.indexOf(character);
		do {
			randomPos = (int) (Math.random() * this.availableCharacters.size());
			// TODO: Ensure player never receives own suggested character
		} while (randomPos != indexOf);
		return this.availableCharacters.remove(randomPos);
	}

	@Override
	public void changeTurn() {
		this.currentTurn.changeTurn();
	}

	@Override
	public void play() {
		// TODO Auto-generated method stub

	}

}
package com.eleks.academy.who_am_i.core.impl;

import com.eleks.academy.who_am_i.core.Game;
import com.eleks.academy.who_am_i.core.Player;
import com.eleks.academy.who_am_i.core.Turn;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class RandomGame implements Game {

    private static final int DURATION = 2;
    private static final TimeUnit UNIT = TimeUnit.MINUTES;
    private static final String YES = "Yes";
    private static final String NO = "No";
    private static final String NO_ANSWER = "Player did not asnwer within ";
    private Map<String, String> playersCharacter = new ConcurrentHashMap<>();
    private List<Player> players;
    private List<String> availableCharacters;
    private Turn currentTurn;

    public RandomGame(List<Player> players, List<String> availableCharacters) {
        this.availableCharacters = new ArrayList<>(availableCharacters);
        this.players = new ArrayList<>(players.size());
        players.forEach(this::addPlayer);
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
            System.err.println("Player did not suggest a character within %d %s".formatted(DURATION, UNIT));
        }
    }

    @Override
    public boolean makeTurn() {
        Player currentGuesser = currentTurn.getGuesser();
        List<String> answers;
        String guessersName;
        try {
            guessersName = currentGuesser.getName().get(DURATION, UNIT);
        } catch (TimeoutException | InterruptedException | ExecutionException e) {
            // TODO: Add custom runtime exception implementation
            throw new RuntimeException("Failed to obtain a player's name", e);
        }

        if (currentGuesser.isReadyGuess()) {
            String guess = currentGuesser.getGuess();
            answers = currentTurn.getOtherPlayers().stream()
                    .map(player -> player.answerGuess(guess, this.playersCharacter.get(guessersName)))
                    .collect(Collectors.toList());

            long positiveCount = answers.stream().filter(YES::equals).count();
            long negativeCount = answers.stream().filter(NO::equals).count();

            boolean win = positiveCount > negativeCount;

            if (win) {
                players.remove(currentGuesser);
            }
            return win;
        } else {
            String question = currentGuesser.getQuestion();
            answers = currentTurn.getOtherPlayers().stream()
                    .map(player -> player.answerQuestion(question, this.playersCharacter.get(guessersName)))
                    .collect(Collectors.toList());

            long positiveCount = answers.stream().filter(YES::equals).count();
            long negativeCount = answers.stream().filter(NO::equals).count();
            return positiveCount > negativeCount;
        }

    }

    void assignCharacters() {
        players.stream().map(Player::getName).parallel().map(f -> {
			// TODO: extract into a configuration parameters
			try {
				return f.get(DURATION, UNIT);
			} catch (InterruptedException | ExecutionException e) {
				Thread.currentThread().interrupt();
				// TODO: Add custom runtime exception implementation
				throw new RuntimeException("Failed to obtain a player's name", e);
			} catch (TimeoutException e) {
				// TODO: Choose a name from a pool of names, i.e. Anonymous Badger etc.
				throw new RuntimeException("Player did not provide a name within %d %s".formatted(DURATION, UNIT));
			}
		}).forEach(name -> this.playersCharacter.put(name, this.getRandomCharacter()));

    }

    @Override
    public void initGame() {
        this.assignCharacters();
        this.currentTurn = new TurnImpl(this.players);
    }

    @Override
    public boolean isFinished() {
        return players.size() == 1;
    }

    private String getRandomCharacter() {
        int randomPos = (int) (Math.random() * this.availableCharacters.size());
        // TODO: Ensure player never receives own suggested character
        return this.availableCharacters.remove(randomPos);
    }

    @Override
    public void changeTurn() {
        this.currentTurn.changeTurn();
    }

    @Override
    public void play() {
        boolean gameStatus = true;

        while(gameStatus) {
            boolean turnResult = this.makeTurn();

            while(turnResult) {
                turnResult = this.makeTurn();
            }
            this.changeTurn();
            gameStatus = !this.isFinished();

        }
    }

}

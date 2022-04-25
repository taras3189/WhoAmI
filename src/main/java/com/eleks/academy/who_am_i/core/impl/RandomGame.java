package com.eleks.academy.who_am_i.core.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.eleks.academy.who_am_i.core.Game;
import com.eleks.academy.who_am_i.core.Player;
import com.eleks.academy.who_am_i.core.Turn;

public class RandomGame implements Game {

	private Map<String, String> playersCharacter = new HashMap<>();
	private List<Player> players = new ArrayList<>();
	private List<String> availableCharacters;
	private Turn currentTurn;

	private final static String YES = "Yes";
	private final static String NO = "No";

	public RandomGame(List<String> availableCharacters) {
		this.availableCharacters = new ArrayList<String>(availableCharacters);
	}

	static {
		System.out.println("\tGame Started\n");
	}
	private Turn currentGameTurn;
	private List<String> characters;
	private List<Player> players;
	private Map<String, String> playersCharacters = new HashMap<>();
	private List<String> gameResult = new ArrayList<>();
	private int turnCount = 0;

	@Override
	public void addPlayer(Player player) {
		this.players.add(player);аа

	public RandomGame(List<String> characters, List<Player> players) {
		this.characters = new ArrayList<>(characters);
		this.players = new ArrayList<>(players);

	}

	@Override
	public boolean makeTurn() {
		Player currentPlayer = currentGameTurn.getGuesser();
		
		if(currentPlayer.isReadyForGuess()) {
			return giveGuess(currentPlayer.getGuess(),currentPlayer);
		} else {
			boolean streak;
			
			do {
				streak = giveQuestion(currentPlayer.getQuestion(), currentPlayer);
			} while (streak != false);			
			return true;
		}		
	}

	public void init() {
			showPlayers();
			assignCharacters();
			begin();				
					
			while(isFinished() != true) {
				turnCount++;
				System.out.println("\tTurn №" + turnCount + " Started!\n");
				boolean isTurnEnd = makeTurn();
			
				while(isTurnEnd != true) {
					isTurnEnd = makeTurn();
				}
				return win;				
		
			} else {	
				String question = currentGuesser.getQuestion();
				answers = currentTurn.getOtherPlayers().stream()
					.map(player -> player.answerQuestion(question, this.playersCharacter.get(currentGuesser.getName())))
					.collect(Collectors.toSet());
				long positiveCount = answers.stream().filter(a -> YES.equals(a)).count();
				long negativeCount = answers.stream().filter(a -> NO.equals(a)).count();
				return positiveCount > negativeCount;
				endTurn();			
			}
			
			showResults();	
	}

	@Override
	public void assignCharacters() {
		players.stream().forEach(player -> this.playersCharacter.put(player.getName(), this.getRandomCharacter()));

	}

	@Override
	public void showPlayers() {
		System.out.println("Players: ");
		players.stream().forEach(player -> System.out.println("-> " + player.getName()));
		System.out.println();
	}

	@Override
	public void initGame() {
		this.currentTurn = new TurnImpl(this.players);

	}

	@Override
	public boolean isFinished() {
		if (players.size() == 1) {
			gameResult.add(players.get(0).getName());
		}
		return players.size() == 1;
	}

	private String getRandomCharacter() {
		int randomPos = (int)(Math.random() * this.availableCharacters.size()); 
		return this.availableCharacters.remove(randomPos);
	}

	@Override
	public void changeTurn() {
		this.currentTurn.changeTurn();
	}

	@Override
	public void begin() {
		currentGameTurn = new TurnImpl(players);
	}

	@Override
	public void endTurn() {
		System.out.println("\n\tTurn Is Ended!");
		currentGameTurn.changeTurn();
	}
	

	@Override
	public int countPlayers() {
		return players.size();
	}

	@Override
	public void showResults() {
		System.out.println("\tGame Finished!\n\tGame Results:\n");
		for (int i = 0; i < gameResult.size(); i++) {
			if(i == 0) {
				System.out.println("Winner! -> " + gameResult.get(i));
			}			
		}
	}	
	
	private boolean giveQuestion(String playerQuestion, Player currentPlayer) {
		if (playerQuestion.isBlank()) {
			return false;
		}
		List<String> playersAnswers;
		
		playersAnswers = currentGameTurn.getOtherPlayers().stream()
				.map(player -> player.answerQuestion(playerQuestion, playersCharacters.get(currentPlayer.getName())))
				.collect(Collectors.toList());
		
		long yes = playersAnswers.stream().filter(answer -> answer.equals(true)).count();
		long no = playersAnswers.stream().filter(answer -> answer.equals(false)).count();
		
		return yes > no;
	}
	
	private boolean giveGuess(String playerGuess, Player currentPlayer) {
		List<String> playersAnswers;
		
		playersAnswers = currentGameTurn.getOtherPlayers().stream()
				.map(player -> player.answerGuess(playerGuess, playersCharacters.get(currentPlayer.getName())))
				.collect(Collectors.toList());
		
		long yes = playersAnswers.stream().filter(answer -> answer.equals(true)).count();
		long no = playersAnswers.stream().filter(answer -> answer.equals(false)).count();
		
		if(yes > no) {
			gameResult.add(currentPlayer.getName());
			players.remove(currentPlayer);
		}
		return true;
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
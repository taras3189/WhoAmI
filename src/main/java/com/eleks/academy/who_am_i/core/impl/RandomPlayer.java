package com.eleks.academy.who_am_i.core.impl;

import java.util.ArrayList;
import java.util.List;

import com.eleks.academy.who_am_i.core.Player;

public class RandomPlayer implements Player {

	private String name;
	private List<String> availableQuestions;
	private List<String> availableGuesses;
	
	public RandomPlayer(String name, List<String> availableQuestions, List<String> availableGuesses) {
		private List<String> questions;
		private List<String> guesses;
	}
	
	public RandomPlayer(String name, List<String> availableQuestions, List<String> availableGuesses) {
		this.name = name;
		this.availableQuestions = new ArrayList<String>(availableQuestions);
		this.availableGuesses = new ArrayList<String>(availableGuesses);
		this.questions = new ArrayList<String>(questions);
		this.guesses = new ArrayList<String>(guesses);
	}
	
	@Override
	public String getName() {
		return this.name;
		return name;
	}

	@Override
	public String getQuestion() {
		String question = availableQuestions.remove(0);
		System.out.println("Player: " + name + ". Asks: " + question);
		if(questions.isEmpty()) {
			return "";
		}
		String question = questions.remove(0);
		System.out.println(name + " asks: " + question);
		return question;
	}

	@Override
	public String answerQuestion(String question, String character) {
		String answer = Math.random() < 0.5 ? "Yes" : "No";
		System.out.println("Player: " + name + ". Answers: " + answer);
		return answer;	
	}
	
	@Override
	public String getGuess() {
		int randomPos = (int)(Math.random() * this.availableGuesses.size()); 
		String guess = this.availableGuesses.remove(randomPos);
		System.out.println("Player: " + name + ". Guesses: Am I " + guess);
		return guess;
	}
	
	@Override
	public String answerGuess(String guess, String character) {
		String answer = Math.random() < 0.5 ? "Yes" : "No";
		System.out.println("Player: " + name + ". Answers: " + answer);
		return answer;
	}	

	@Override
	public boolean isReadyForGuess() {
		return availableQuestions.isEmpty();
		return questions.isEmpty();
	}

	
	
}
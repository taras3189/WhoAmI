package com.eleks.academy.who_am_i.core;

public interface Player {

	String getName();
	
	String getQuestion();
	
	String answerQuestion(String question, String character);	
	
	String getGuess();
	
	boolean isReadyForGuess();	

	String answerGuess(String guess, String character);	
	
}


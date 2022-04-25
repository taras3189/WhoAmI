package com.eleks.academy.who_am_i.core;

import java.util.concurrent.Future;

public interface Player {

	Future<String> getName();

	Future<String> suggestCharacter();

	// TODO: return Future<String>
	Future<String> getQuestion();

	// TODO: return Future<String>
	Future<String> answerQuestion(String question, String character);

	// TODO: return Future<String>
	Future<String> getGuess();

	// TODO: return Future<String>
	Future<Boolean> isReadyForGuess();

	Future<String> answerGuess(String guess, String character);

	void close();

}

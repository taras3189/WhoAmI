package com.eleks.academy.who_am_i.networking.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

import com.eleks.academy.who_am_i.core.Player;

public class ClientPlayer implements Player {

	private String name;
	private Socket socket;
	private BufferedReader reader;
	private PrintStream writer;

	public ClientPlayer(String name, Socket socket) throws IOException {
		this.name = name;
		this.socket = socket;
		this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		this.writer = new PrintStream(socket.getOutputStream());
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public String getQuestion() {		
		
		try {
			writer.println("Ask your question: ");
			question = reader.readLine();
			System.out.println(name + " asks: " + question);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return question;
	}

	@Override
	public String answerQuestion(String question, String character) {
		String answer = "";
		
		try {
			writer.println("Answer second player question: " + question + "Character is: " + character);
			writer.println("Answer other player question: " + question + "Character is: " + character);
			answer = reader.readLine();
			System.out.println(name + " answers:\n -> " + question);
		} catch (IOException e) {
			e.printStackTrace();
		}		
		return answer;

	}

	@Override
	public String getGuess() {
		String guess = "";		

		try {
			writer.println("Write your guess: ");
			guess = reader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return guess;
	}

	@Override
	public boolean isReadyForGuess() {
		String answer = "";
		writer.println("Are you ready to guess? ");
		
		try {
			answer = reader.readLine();
		} catch (IOException e) {			
			e.printStackTrace();
		}		
		return answer.equals("Yes") ? true : false;
	}

	@Override
	public String answerGuess(String guess, String character) {
		String answer = "";		

		try {
			writer.println("Write your answer: ");
			answer = reader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return answer;		
	}

}

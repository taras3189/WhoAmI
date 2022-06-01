package com.eleks.academy.who_am_i.networking.client;

import com.eleks.academy.who_am_i.core.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Objects;
import java.util.concurrent.*;

public class ClientPlayer implements Player, AutoCloseable {

	private String name = "";
	private String suggestedCharacter = "";

	private final BufferedReader reader;
	private final PrintStream writer;
	private final Socket socket;

	private final ExecutorService executor = Executors.newSingleThreadExecutor();

	public ClientPlayer(Socket socket) throws IOException {
		this.socket = socket;
		this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		this.writer = new PrintStream(socket.getOutputStream());
	}

	@Override
	public Future<String> getName() {
		return executor.submit(this::askName);
	}

	private String askName() {
		if (name.isEmpty()) {
			try {
				writer.println("Your name please!");
				name = reader.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return name;
	}

	@Override
	public String getQuestion() {
		Callable<String> askQuestion = () -> {
			String question = "";

			try {
				writer.println("Ask your question: ");
				question = reader.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return question;
		};
		return executor.submit(askQuestion);
	}

	@Override
	public Future<String> answerQuestion(String question, String character) {
		Callable<String> answerQuestion = () -> {
			String answer = "";
			try {
				writer.println("Answer second player question: " + question + "Character is: " + character);
				answer = reader.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return answer;
		};
		return executor.submit(answerQuestion);
	}

	@Override
	public String getGuess() {
		Callable<String> getGuess = () -> {
			String answer = "";

			try {
				writer.println("Write your guess: ");
				answer = reader.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return answer;
		};
		return executor.submit(getGuess);
	}

	@Override
	public Future<Boolean> isReadyForGuess() {
		Callable<Boolean> isReadyForGuess = () -> {
			String answer = "";

			try {
				writer.println("Are you ready to guess? ");
				answer = reader.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return answer.equals("Yes");
		};
		return executor.submit(isReadyForGuess);
	}

	@Override
	public Future<String> answerGuess(String guess, String character) {
		Callable<String> answerGuess = () -> {
			String answer = "";

			try {
				writer.println("Write your answer: ");
				answer = reader.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return answer;
		};
		return executor.submit(answerGuess);
	}

	@Override
	public Future<String> suggestCharacter() {
		return executor.submit(this::doSuggestCharacter);
	}

	private String doSuggestCharacter() {
		if (suggestedCharacter.isEmpty()) {
			try {
				return reader.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return suggestedCharacter;
	}

	@Override
	public void close() {
		executor.shutdown();
		try {
			executor.awaitTermination(5, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
		close(writer);
		close(reader);
		close(socket);
	}

	private void close(AutoCloseable closeable) {
		try {
			closeable.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof ClientPlayer that)) return false;
		return name.equals(that.name) && suggestedCharacter.equals(that.suggestedCharacter);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, suggestedCharacter);
	}

}

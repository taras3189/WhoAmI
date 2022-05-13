package com.eleks.academy.who_am_i.core.impl;

import com.eleks.academy.who_am_i.core.Player;
import com.eleks.academy.who_am_i.core.Turn;

import java.util.List;
import java.util.stream.Collectors;

public class TurnImpl implements Turn {

	private List<Player> players;
	private int currentPlayerIndex = 0;

	public TurnImpl(List<Player> players) {
		this.players = players;
	}

	@Override
	public Player getGuesser() {
		return this.players.get(currentPlayerIndex);
	}

	@Override
	public List<Player> getOtherPlayers() {
		return this.players.stream()
				.filter(player -> !player.getName().equals(this.getGuesser().getName()))
				.collect(Collectors.toList());
	}

	@Override
	public void changeTurn() {
		this.currentPlayerIndex = this.currentPlayerIndex + 1 >= this.players.size() ? 0 : this.currentPlayerIndex + 1;
	}

}

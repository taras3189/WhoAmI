package com.eleks.academy.who_am_i.core.impl;

public sealed class StartGameAnswer extends Answer {

    public StartGameAnswer(String player) {
        super(player);
    }

    public static StartGameAnswer of(String player) {
        return new StartGameAnswer(player);
    }

}

package com.eleks.academy.who_am_i.core.impl;


/**
 * Extend this class in case more input is needed
 * for a specific {@link com.eleks.academy.whoami.core.state.GameState}
 */

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public sealed class Answer permits StartGameAnswer {
    private final String player;
    private String message;
}

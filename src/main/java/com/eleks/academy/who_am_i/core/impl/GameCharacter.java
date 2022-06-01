package com.eleks.academy.who_am_i.core.impl;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(staticName = "of")
public class GameCharacter {

    private final String character;

    private final String author;

    private boolean taken;

    public void markTaken() {
        this.taken = Boolean.TRUE;
    }

}

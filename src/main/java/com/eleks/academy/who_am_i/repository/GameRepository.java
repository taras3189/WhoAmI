package com.eleks.academy.who_am_i.repository;

import com.eleks.academy.who_am_i.core.SynchronousGame;

import java.util.Optional;
import java.util.stream.Stream;

public class GameRepository {

    Stream<SynchronousGame> findAllAvailable(String player);

    SynchronousGame save(SynchronousGame game);

    Optional<SynchronousGame> findById(String id);

}

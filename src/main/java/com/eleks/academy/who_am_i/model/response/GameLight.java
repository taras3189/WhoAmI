package com.eleks.academy.who_am_i.model.response;


import com.eleks.academy.who_am_i.core.SynchronousGame;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GameLight {

    private String id;

    private String status;

    private String playersInGame;

    public static GameLight of(SynchronousGame game) {
        return GameLight.builder()
                .id(game.getId())
                .status(game.getStatus())
                .playersInGame(game.getPlayersInGame())
                .build();
    }

}

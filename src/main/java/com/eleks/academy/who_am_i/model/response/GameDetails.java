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
public class GameDetails {

    private String id;

    private String status;

    private String currentTurn;

    public static GameDetails of(SynchronousGame game) {
        return GameDetails.builder()
                .id(game.getId())
                .status(game.getStatus())
                .currentTurn(game.getTurn())
                .build();
    }

}

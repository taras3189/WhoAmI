package com.eleks.academy.who_am_i.model.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CharacterSuggestion {

    @NotBlank(message = "character must be not blank")
    private String character;

}

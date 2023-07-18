package com.hoainamtd.pokemonreview.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PokemonDTO {
    private int id;
    private String name;
    private String type;

    private ArrayList<ReviewDTO> reviewDTOS;
}

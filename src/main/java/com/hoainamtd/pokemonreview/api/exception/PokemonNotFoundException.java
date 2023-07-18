package com.hoainamtd.pokemonreview.api.exception;

public class PokemonNotFoundException extends RuntimeException{
    private static final long serialVerisionUID = 2;

    public PokemonNotFoundException(String message) {
        super(message);
    }
}

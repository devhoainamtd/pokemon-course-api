package com.hoainamtd.pokemonreview.api.service;

import com.hoainamtd.pokemonreview.api.dto.PokemonDTO;
import com.hoainamtd.pokemonreview.api.dto.PokemonResponse;
import com.hoainamtd.pokemonreview.api.dto.ReviewDTO;
import com.hoainamtd.pokemonreview.api.models.Pokemon;
import com.hoainamtd.pokemonreview.api.models.Review;

import java.util.List;

public interface PokemonService {
    public List<PokemonDTO> listPokemon();

    public PokemonResponse listPokemonPagination(int pageNo, int pageSize);
    public PokemonDTO findPokemonById(int id);
    public PokemonDTO createPokemon(PokemonDTO pokemonDTO);
    public PokemonDTO updatePokemon(PokemonDTO pokemonDTO, int id);
    public void deletePokemon(int id);
    public List<Review> shoListReview(int pokemonId);

}

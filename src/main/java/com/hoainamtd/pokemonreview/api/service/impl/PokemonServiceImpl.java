package com.hoainamtd.pokemonreview.api.service.impl;

import com.hoainamtd.pokemonreview.api.dto.PokemonDTO;
import com.hoainamtd.pokemonreview.api.dto.PokemonResponse;
import com.hoainamtd.pokemonreview.api.dto.ReviewDTO;
import com.hoainamtd.pokemonreview.api.exception.PokemonNotFoundException;
import com.hoainamtd.pokemonreview.api.models.Pokemon;
import com.hoainamtd.pokemonreview.api.models.Review;
import com.hoainamtd.pokemonreview.api.repository.PokemonRepository;
import com.hoainamtd.pokemonreview.api.service.PokemonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PokemonServiceImpl implements PokemonService {

    private PokemonRepository repo;

    @Autowired
    public PokemonServiceImpl(PokemonRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<PokemonDTO> listPokemon() {
        List<Pokemon> listOfPokemon = repo.findAll();
        return listOfPokemon.stream().map(pokemon -> mapToDTO(pokemon)).collect(Collectors.toList());
    }

    @Override
    public PokemonResponse listPokemonPagination(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Pokemon> pokemons = repo.findAll(pageable);
        List<Pokemon> listOfPokemon = pokemons.getContent();
        List<PokemonDTO> content = listOfPokemon.stream().map(p -> mapToDTO(p)).collect(Collectors.toList());

        PokemonResponse pokemonResponse = new PokemonResponse();
        pokemonResponse.setContent(content);
        pokemonResponse.setPageNo(pokemons.getNumber());
        pokemonResponse.setPageSize(pokemons.getSize());
        pokemonResponse.setTotalElements(pokemons.getTotalElements());
        pokemonResponse.setTotalPages(pokemons.getTotalPages());
        pokemonResponse.setLast(pokemons.isLast());
        return pokemonResponse;
    }

//    @Override
//    public PokemonResponse listPokemonPagination(int pageNo, int pageSize) {
//        Pageable pageable = PageRequest.of(pageNo, pageSize);
//        Page<Pokemon> pokemons = repo.findAll(pageable);
//        List<Pokemon> listOfPokemon = pokemons.getContent();
//
//        List<Review> arrayList = listOfPokemon.get(0).getReviews();
//        for (Review r : arrayList
//             ) {
//            System.out.println(r.getTitle());
//        }
//
////        List<PokemonDTO> content = listOfPokemon.stream().map(p -> mapToDTO(p)).collect(Collectors.toList());
////
////        PokemonResponse pokemonResponse = new PokemonResponse();
////        pokemonResponse.setContent(content);
////        pokemonResponse.setPageNo(pokemons.getNumber());
////        pokemonResponse.setPageSize(pokemons.getSize());
////        pokemonResponse.setTotalElements(pokemons.getTotalElements());
////        pokemonResponse.setTotalPages(pokemons.getTotalPages());
////        pokemonResponse.setLast(pokemons.isLast());
////        return pokemonResponse;
//        return null;
//    }

    @Override
    public PokemonDTO findPokemonById(int id) {
        Pokemon pokemon = repo.findById(id).orElseThrow(() -> new PokemonNotFoundException("Pokemon could not be found"));
        return mapToDTO(pokemon);
    }

    @Override
    public PokemonDTO createPokemon(PokemonDTO pokemonDTO) {
        Pokemon pokemon = new Pokemon();

        List<Pokemon> listPokemon = repo.findAll();
        for (Pokemon p : listPokemon) {
            if (pokemonDTO.getName().equalsIgnoreCase(p.getName())) throw new PokemonNotFoundException("The pokemon's name is the same");
        }
        pokemon.setName(pokemonDTO.getName());
        pokemon.setType(pokemonDTO.getType());

        repo.save(pokemon);
        return mapToDTO(pokemon);
    }

    @Override
    public PokemonDTO updatePokemon(PokemonDTO pokemonDTO, int id) {
        Pokemon pokemon = repo.findById(id).orElseThrow(() -> new PokemonNotFoundException("Pokemon could not be updated"));
        pokemon.setName(pokemonDTO.getName());
        pokemon.setType(pokemonDTO.getType());

        repo.save(pokemon);

        PokemonDTO pokemonDTO1 = new PokemonDTO();

        return mapToDTO(pokemon);
    }

    @Override
    public void deletePokemon(int id) {
        repo.findById(id).orElseThrow(() -> new PokemonNotFoundException("Pokemon could not be delete"));
        repo.deleteById(id);
    }

    @Override
    public List<Review> shoListReview(int pokemonId) {
        Pokemon pokemon = repo.findById(pokemonId).get();
        ArrayList<Review> reviewArrayList = (ArrayList<Review>) pokemon.getReviews();
        return reviewArrayList;
    }

    private PokemonDTO mapToDTO(Pokemon pokemon) {
        PokemonDTO pokemonDTO = new PokemonDTO();

        pokemonDTO.setId(pokemon.getId());
        pokemonDTO.setName(pokemon.getName());
        pokemonDTO.setType(pokemon.getType());
        List<ReviewDTO> reviewDTOs = new ArrayList<>();
        for (Review review : pokemon.getReviews()) {
            ReviewDTO reviewDTO = new ReviewDTO();

            reviewDTO.setContent(review.getContent());
            reviewDTO.setTitle(review.getTitle());
            reviewDTO.setStars(review.getStars());

            reviewDTOs.add(reviewDTO);
        }
        pokemonDTO.setReviewDTOS((ArrayList<ReviewDTO>) reviewDTOs);
        return pokemonDTO;
    }

    private Pokemon mapToEntity(PokemonDTO pokemonDTO) {
        Pokemon pokemon = new Pokemon();

        pokemon.setId(pokemonDTO.getId());
        pokemon.setName(pokemonDTO.getName());
        pokemon.setType(pokemonDTO.getType());

        return pokemon;
    }
}

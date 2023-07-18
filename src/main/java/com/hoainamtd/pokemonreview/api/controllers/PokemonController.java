package com.hoainamtd.pokemonreview.api.controllers;

import com.hoainamtd.pokemonreview.api.dto.PokemonDTO;
import com.hoainamtd.pokemonreview.api.dto.PokemonResponse;
import com.hoainamtd.pokemonreview.api.models.Review;
import com.hoainamtd.pokemonreview.api.service.PokemonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PokemonController {
    private PokemonService pokemonService;

    @Autowired
    public PokemonController(PokemonService pokemonService) {
        this.pokemonService = pokemonService;
    }

    @PostMapping("/pokemon/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<PokemonDTO> createPokemon(@RequestBody PokemonDTO pokemonDTO) {
        return new ResponseEntity<>(pokemonService.createPokemon(pokemonDTO), HttpStatus.CREATED);
    }

    @GetMapping("/pokemon/{id}")
    public ResponseEntity<PokemonDTO> getById(@PathVariable int id) {
        return new ResponseEntity<>(pokemonService.findPokemonById(id), HttpStatus.OK);
    }

    @GetMapping("/pokemon/pagination")
    public ResponseEntity<PokemonResponse> getPokemonPagination(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize
    ) {
        return new ResponseEntity<>(pokemonService.listPokemonPagination(pageNo, pageSize), HttpStatus.OK);
    }

    @PutMapping("/pokemon/{id}")
    public PokemonDTO updatePokemon(@PathVariable int id, @RequestBody PokemonDTO pokemonDTO) {
        return pokemonService.updatePokemon(pokemonDTO, id);
    }

    @DeleteMapping("/pokemon/{id}")
    public ResponseEntity<String> deletePokemon(@PathVariable int id) {
        return new ResponseEntity<>( "Delete Successfully!", HttpStatus.OK);
    }

    @GetMapping("/pokemon/listReview/{id}")
    public List<Review> listReview(@PathVariable int id) {
        return pokemonService.shoListReview(id);
    }
}

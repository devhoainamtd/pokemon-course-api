package com.hoainamtd.pokemonreview.api.repository;

import com.hoainamtd.pokemonreview.api.models.Pokemon;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class PokemonRepositoryTest {

    @Autowired
    private PokemonRepository pokemonRepository;

    @Test
    public void PokemonRepository_saveAll_ReturnsSavedPokemon() {

        // Arrange
        Pokemon pokemon = Pokemon.builder()
                .name("Pikachu")
                .type("Electricity")
                .build();

        //Act
        Pokemon savePokemon = pokemonRepository.save(pokemon);

        //Assert
        Assertions.assertThat(savePokemon).isNotNull();
        Assertions.assertThat(savePokemon.getId()).isNotZero();
    }

    @Test
    public void PokemonRepository_getAll_ReturnMoreThanOnePokemon() {

        Pokemon pokemon = Pokemon.builder()
                .name("Pikachu")
                .type("Electricity")
                .build();

        Pokemon pokemon1 = Pokemon.builder()
                .name("Sakura")
                .type("Fire")
                .build();

        pokemonRepository.save(pokemon);
        pokemonRepository.save(pokemon1);
        List<Pokemon> pokemonList = pokemonRepository.findAll();

        Assertions.assertThat(pokemonList).isNotNull();
        Assertions.assertThat(pokemonList.size()).isEqualTo(2);
    }

    @Test
    public void PokemonRepository_findById_ReturnPokemon() {

        Pokemon pokemon = Pokemon.builder()
                .name("Pikachu")
                .type("Electricity")
                .build();

        pokemonRepository.save(pokemon);
        Pokemon pokemon1 = pokemonRepository.findById(pokemon.getId()).get();

        Assertions.assertThat(pokemon1).isNotNull();
    }

    @Test
    public void PokemonRepository_findByType_ReturnPokemonNotNull() {

        Pokemon pokemon = Pokemon.builder()
                .name("Pikachu")
                .type("Electricity")
                .build();

        pokemonRepository.save(pokemon);
        Pokemon pokemon1 = pokemonRepository.findByType(pokemon.getType()).get();

        Assertions.assertThat(pokemon1).isNotNull();
    }

    @Test
    public void PokemonRepository_updatePokemon_ReturnPokemonNotNull() {

        Pokemon pokemon = Pokemon.builder()
                .name("Pikachu")
                .type("Electricity")
                .build();

        pokemonRepository.save(pokemon);
        Pokemon pokemon1 = pokemonRepository.findById(pokemon.getId()).get();
        pokemon1.setName("Sakura");
        pokemon1.setType("Fire");
        Pokemon pokemonUpdate = pokemonRepository.save(pokemon1);

        Assertions.assertThat(pokemonUpdate).isNotNull();
    }

    @Test
    public void PokemonRepository_deletePokemon_ReturnPokemonIsEmpty() {
        Pokemon pokemon = Pokemon.builder()
                .name("Pikachu")
                .type("Electricity")
                .build();

        pokemonRepository.save(pokemon);

        pokemonRepository.deleteById(pokemon.getId());
        Optional<Pokemon> pokemonDelete = pokemonRepository.findById(pokemon.getId());

        Assertions.assertThat(pokemonDelete).isEmpty();
    }
}

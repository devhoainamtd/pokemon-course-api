package com.hoainamtd.pokemonreview.api.service;

import com.hoainamtd.pokemonreview.api.dto.PokemonDTO;
import com.hoainamtd.pokemonreview.api.dto.PokemonResponse;
import com.hoainamtd.pokemonreview.api.models.Pokemon;
import com.hoainamtd.pokemonreview.api.repository.PokemonRepository;
import com.hoainamtd.pokemonreview.api.service.impl.PokemonServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PokemonServiceTest {

    @Mock
    private PokemonRepository pokemonRepository;

    @InjectMocks
    private PokemonServiceImpl pokemonService;

    @Test
    public void PokemonService_createPokemon_ReturnsPokemon() {
        Pokemon pokemon = Pokemon.builder()
                .name("pikachu")
                .type("electric")
                .build();
        PokemonDTO pokemonDTO = PokemonDTO.builder()
                .name("pikachu")
                .type("electric")
                .build();

        // khi save một đối tượng bấy kì của Pokemon.class thì sẽ luôn trả v pokemon
        when(pokemonRepository.save(Mockito.any(Pokemon.class))).thenReturn(pokemon);

        PokemonDTO savedPokemon = pokemonService.createPokemon(pokemonDTO);

        Assertions.assertThat(savedPokemon).isNotNull();
    }

    @Test
    public void PokemonService_listPokemonPagination_ReturnsListPokemon() {
        Page<Pokemon> pokemons = Mockito.mock(Page.class);

        when(pokemonRepository.findAll(Mockito.any(Pageable.class))).thenReturn(pokemons);

        PokemonResponse pokemonResponse = pokemonService.listPokemonPagination(1, 10);

        Assertions.assertThat(pokemonResponse).isNotNull();
    }

    @Test
    public void PokemonService_findPokemonById_ReturnsPokemon() {
        Pokemon pokemon = Mockito.mock(Pokemon.class);
        PokemonDTO pokemonDTO = Mockito.mock(PokemonDTO.class);

        when(pokemonRepository.findById(Mockito.any())).thenReturn(Optional.ofNullable(pokemon));

        PokemonDTO pokemonGet = pokemonService.findPokemonById(pokemonDTO.getId());

        Assertions.assertThat(pokemonGet).isNotNull();
    }

    @Test
    public void PokemonService_updatePokemon_ReturnsNewPokemon() {
        Pokemon pokemon = Mockito.mock(Pokemon.class);
        PokemonDTO pokemonDTO = Mockito.mock(PokemonDTO.class);

        when(pokemonRepository.findById(Mockito.any())).thenReturn(Optional.ofNullable(pokemon));
        when(pokemonRepository.save(Mockito.any(Pokemon.class))).thenReturn(pokemon);

        PokemonDTO pokemonUpdate = pokemonService.updatePokemon(pokemonDTO, pokemon.getId());

        Assertions.assertThat(pokemonUpdate).isNotNull();
    }

    @Test
    public void PokemonService_deletePokemon_ReturnIsEmpty() {
        Pokemon pokemon = Mockito.mock(Pokemon.class);

        when(pokemonRepository.findById(Mockito.any())).thenReturn(Optional.ofNullable(pokemon));

        //kiem tra có nem ra ngoại lệ hay không
        assertAll(() -> pokemonService.deletePokemon(pokemon.getId()));

    }
}

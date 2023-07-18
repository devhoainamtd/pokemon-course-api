package com.hoainamtd.pokemonreview.api.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hoainamtd.pokemonreview.api.dto.PokemonDTO;
import com.hoainamtd.pokemonreview.api.dto.PokemonResponse;
import com.hoainamtd.pokemonreview.api.dto.ReviewDTO;
import com.hoainamtd.pokemonreview.api.models.Pokemon;
import com.hoainamtd.pokemonreview.api.models.Review;
import com.hoainamtd.pokemonreview.api.service.PokemonService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import java.util.Arrays;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(controllers = PokemonController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class PokemonControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PokemonService pokemonService;
    @Autowired
    private ObjectMapper objectMapper;

    private Pokemon pokemon;
    private Review review;
    private ReviewDTO reviewDTO;
    private PokemonDTO pokemonDTO;

    @BeforeEach
    public void init() {
        pokemon = Pokemon.builder().name("pikachu").type("electric").build();
        pokemonDTO = PokemonDTO.builder().name("pikachu").type("electric").build();
        review = Review.builder().title("title").content("content").stars(5).build();
        reviewDTO = ReviewDTO.builder().title("review title").content("test content").stars(5).build();
    }

    @Test
    public void PokemonController_CreatePokemon_ReturnCreated() throws Exception {
        given(pokemonService.createPokemon(ArgumentMatchers.any())).willAnswer((invocation -> invocation.getArgument(0)));

        ResultActions response = mockMvc.perform(post("/api/pokemon/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pokemonDTO)));

        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(pokemonDTO.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.type", CoreMatchers.is(pokemonDTO.getType())));
    }

    @Test
    public void PokemonController_GetPokemonPagination_ReturnPokemonResponsive() throws Exception {

        PokemonResponse responseDTO = PokemonResponse.builder()
                .content(Arrays.asList(pokemonDTO))
                .pageSize(10)
                .last(true)
                .pageNo(1)
                .build();
        when(pokemonService.listPokemonPagination(1, 10)).thenReturn(responseDTO);

        ResultActions response = mockMvc.perform(get("/api/pokemon/pagination")
                .contentType(MediaType.APPLICATION_JSON)
                .param("pageNo", "1")
                .param("pageSize", "10"));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.size()", CoreMatchers.is(responseDTO.getContent().size())));
    }

    @Test
    public void PokemonController_GetById_ReturnPokemonDTO() throws Exception {
        when(pokemonService.findPokemonById(1)).thenReturn(pokemonDTO);

        ResultActions response = mockMvc.perform(get("/api/pokemon/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pokemonDTO)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(pokemonDTO.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.type", CoreMatchers.is(pokemonDTO.getType())));
    }

    @Test
    public void PokemonController_UpdatePokemon_ReturnPokemonDTO() throws Exception {
        when(pokemonService.updatePokemon(pokemonDTO,1)).thenReturn(pokemonDTO);

        ResultActions response = mockMvc.perform(put("/api/pokemon/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pokemonDTO)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(pokemonDTO.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.type", CoreMatchers.is(pokemonDTO.getType())));
    }

    @Test
    public void PokemonController_DeletePokemon_ReturnPokemonDTO() throws Exception {

        doNothing().doThrow(new RuntimeException()).when(pokemonService).deletePokemon(1);

        ResultActions response = mockMvc.perform(delete("/api/pokemon/1")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk());

    }
}

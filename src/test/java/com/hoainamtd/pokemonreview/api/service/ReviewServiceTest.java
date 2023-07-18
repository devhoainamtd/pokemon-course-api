package com.hoainamtd.pokemonreview.api.service;

import com.hoainamtd.pokemonreview.api.dto.PokemonDTO;
import com.hoainamtd.pokemonreview.api.dto.ReviewDTO;
import com.hoainamtd.pokemonreview.api.models.Pokemon;
import com.hoainamtd.pokemonreview.api.models.Review;
import com.hoainamtd.pokemonreview.api.repository.PokemonRepository;
import com.hoainamtd.pokemonreview.api.repository.ReviewRepository;
import com.hoainamtd.pokemonreview.api.service.impl.ReviewServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceTest {
    @Mock
    private ReviewRepository reviewRepository;
    @Mock
    private PokemonRepository pokemonRepository;
    @InjectMocks
    private ReviewServiceImpl reviewService;

    private Pokemon pokemon;
    private PokemonDTO pokemonDTO;
    private Review review;
    private ReviewDTO reviewDTO;

    @BeforeEach
    public void init() {
        pokemon = Pokemon.builder().name("pikachu").type("electric").build();
        pokemonDTO = PokemonDTO.builder().name("pikachu").type("electric").build();
        review = Review.builder().title("title").content("content").stars(5).build();
        reviewDTO = ReviewDTO.builder().title("title").content("content").stars(5).build();
    }

    @Test
    public void ReviewService_createPokemon_ReturnsReviewDTO() {
        when(pokemonRepository.findById(pokemon.getId())).thenReturn(Optional.ofNullable(pokemon));
        when(reviewRepository.save(Mockito.any(Review.class))).thenReturn(review);

        ReviewDTO newReview = reviewService.createReview(pokemon.getId(), reviewDTO);

        Assertions.assertThat(newReview).isNotNull();
    }

    @Test
    public void ReviewService_GetReviewByPokemonId_ReturnListReviewDTO() {
        when(reviewRepository.findByPokemonId(pokemon.getId())).thenReturn(Arrays.asList(review));

        List<ReviewDTO> pokemonReturn = reviewService.getReviewsByPokemonId(pokemon.getId());

        Assertions.assertThat(pokemonReturn).isNotNull();
    }

    @Test
    public void ReviewService_GetReviewById_ReturnReviewDTO() {

        review.setPokemon(pokemon);

        when(pokemonRepository.findById(pokemon.getId())).thenReturn(Optional.ofNullable(pokemon));
        when(reviewRepository.findById(review.getId())).thenReturn(Optional.ofNullable(review));

        ReviewDTO reviewReturn = reviewService.getReviewById(pokemon.getId(), review.getId());

        Assertions.assertThat(reviewReturn).isNotNull();

    }

    @Test
    public void ReviewService_UpdateReview_ReturnNewReviewDTO() {

        pokemon.setReviews(Arrays.asList(review));
        review.setPokemon(pokemon);
        when(pokemonRepository.findById(pokemon.getId())).thenReturn(Optional.ofNullable(pokemon));
        when(reviewRepository.findById(review.getId())).thenReturn(Optional.ofNullable(review));
        when(reviewRepository.save(Mockito.any())).thenReturn(review);

        ReviewDTO reviewDTOReturn = reviewService.updateReview(pokemon.getId(), review.getId(), reviewDTO);

        Assertions.assertThat(reviewDTOReturn).isNotNull();
    }

    @Test
    public void ReviewService_DeleteReview_ReturnIsEmpty() {

        pokemon.setReviews(Arrays.asList(review));
        review.setPokemon(pokemon);
        when(pokemonRepository.findById(pokemon.getId())).thenReturn(Optional.ofNullable(pokemon));
        when(reviewRepository.findById(review.getId())).thenReturn(Optional.ofNullable(review));

        assertAll(() -> reviewService.deleteReview(pokemon.getId(), review.getId()));


    }
}

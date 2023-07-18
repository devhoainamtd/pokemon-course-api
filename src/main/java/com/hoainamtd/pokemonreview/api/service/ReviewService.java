package com.hoainamtd.pokemonreview.api.service;

import com.hoainamtd.pokemonreview.api.dto.ReviewDTO;

import java.util.List;

public interface ReviewService {
    ReviewDTO createReview(int pokemonId, ReviewDTO reviewDTO);
    List<ReviewDTO> getReviewsByPokemonId(int pokemonId);
    ReviewDTO getReviewById(int pokemonId, int reviewId);
    ReviewDTO updateReview(int pokemonId, int reviewId, ReviewDTO reviewDTO);
    void deleteReview(int pokemonId, int reviewId);
}

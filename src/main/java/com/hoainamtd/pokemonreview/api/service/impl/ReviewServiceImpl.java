package com.hoainamtd.pokemonreview.api.service.impl;

import com.hoainamtd.pokemonreview.api.dto.ReviewDTO;
import com.hoainamtd.pokemonreview.api.exception.PokemonNotFoundException;
import com.hoainamtd.pokemonreview.api.exception.ReviewNotFoundException;
import com.hoainamtd.pokemonreview.api.models.Pokemon;
import com.hoainamtd.pokemonreview.api.models.Review;
import com.hoainamtd.pokemonreview.api.repository.PokemonRepository;
import com.hoainamtd.pokemonreview.api.repository.ReviewRepository;
import com.hoainamtd.pokemonreview.api.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService {

    private ReviewRepository reviewRepository;
    private PokemonRepository pokemonRepository;

    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository, PokemonRepository pokemonRepository) {
        this.reviewRepository = reviewRepository;
        this.pokemonRepository = pokemonRepository;
    }

    @Override
    public ReviewDTO createReview(int pokemonId, ReviewDTO reviewDTO) {
        Review review = mapToEntity(reviewDTO);
        Pokemon pokemon = pokemonRepository.findById(pokemonId)
                .orElseThrow(() -> new PokemonNotFoundException("Pokemon could not be found"));
        review.setPokemon(pokemon);

        Review newReview = reviewRepository.save(review);
        return mapToDTO(newReview);
    }

    @Override
    public List<ReviewDTO> getReviewsByPokemonId(int pokemonId) {
        List<Review> reviewList = reviewRepository.findByPokemonId(pokemonId);

        return reviewList.stream().map(review -> mapToDTO(review)).collect(Collectors.toList());
    }

    @Override
    public ReviewDTO getReviewById(int pokemonId, int reviewId) {
        Pokemon pokemon = pokemonRepository.findById(pokemonId)
                .orElseThrow(() -> new PokemonNotFoundException("Pokemon could not be found"));
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewNotFoundException("Review could not be found"));

        if (review.getPokemon().getId() != pokemon.getId())
            throw new ReviewNotFoundException("This review does not belond to a pokemon");

        return mapToDTO(review);
    }

    @Override
    public ReviewDTO updateReview(int pokemonId, int reviewId, ReviewDTO reviewDTO) {
        Pokemon pokemon = pokemonRepository.findById(pokemonId)
                .orElseThrow(() -> new PokemonNotFoundException("Pokemon could not be found"));
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewNotFoundException("Review could not be found"));

        if (review.getPokemon().getId() != pokemon.getId())
            throw new ReviewNotFoundException("This review does not belond to a pokemon");

        review.setContent(reviewDTO.getContent());
        review.setStars(reviewDTO.getStars());
        review.setTitle(reviewDTO.getTitle());

        Review updateReview = reviewRepository.save(review);
        return mapToDTO(updateReview);
    }

    @Override
    public void deleteReview(int pokemonId, int reviewId) {
        Pokemon pokemon = pokemonRepository.findById(pokemonId)
                .orElseThrow(() -> new PokemonNotFoundException("Pokemon could not be found"));
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewNotFoundException("Review could not be found"));
        if (review.getPokemon().getId() != pokemon.getId())
            throw new ReviewNotFoundException("This review does not belond to a pokemon");

        reviewRepository.deleteById(reviewId);
    }

    private ReviewDTO mapToDTO(Review review) {
        ReviewDTO reviewDTO = new ReviewDTO();

        reviewDTO.setId(review.getId());
        reviewDTO.setContent(review.getContent());
        reviewDTO.setStars(review.getStars());
        reviewDTO.setTitle(review.getTitle());

        return reviewDTO;
    }

    private Review mapToEntity(ReviewDTO reviewDTO) {
        Review review = new Review();

        review.setId(reviewDTO.getId());
        review.setContent(reviewDTO.getContent());
        review.setStars(reviewDTO.getStars());
        review.setTitle(reviewDTO.getTitle());

        return review;
    }
}

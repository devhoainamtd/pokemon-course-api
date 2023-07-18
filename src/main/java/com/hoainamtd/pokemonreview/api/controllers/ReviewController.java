package com.hoainamtd.pokemonreview.api.controllers;

import com.hoainamtd.pokemonreview.api.dto.ReviewDTO;
import com.hoainamtd.pokemonreview.api.models.Review;
import com.hoainamtd.pokemonreview.api.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ReviewController {

    private ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }
    @PostMapping("/review/{pokemonId}")
    public ReviewDTO createReview(@PathVariable("pokemonId") int pokemonId, @RequestBody ReviewDTO reviewDTO) {
        return reviewService.createReview(pokemonId, reviewDTO);
    }

    @GetMapping("/pokemon/{id}/review/list")
    public List<ReviewDTO> getAllReviewByPokemonId(@PathVariable("id") int pokemonId) {
        return reviewService.getReviewsByPokemonId(pokemonId);
    }

    @GetMapping("/pokemon/{pokemonId}/review/{reviewId}")
    public ReviewDTO getReviewById(@PathVariable("pokemonId") int pokemonId,
                                   @PathVariable("reviewId") int reviewId) {
        return reviewService.getReviewById(pokemonId, reviewId);
    }

    @PutMapping("/pokemon/{pokemonId}/review/{reviewId}")
    public ReviewDTO updateReview(@PathVariable("pokemonId") int pokemonId,
                                  @PathVariable("reviewId") int reviewId,
                                  @RequestBody ReviewDTO reviewDTO) {
        return reviewService.updateReview(pokemonId, reviewId, reviewDTO);
    }

    @DeleteMapping("/pokemon/{pokemonId}/review/{reviewId}")
    public String deleteReview(@PathVariable("pokemonId") int pokemonId,
                                                  @PathVariable("reviewId") int reviewId) {
        reviewService.deleteReview(pokemonId, reviewId);
        return "Delete Review Successfully!";
    }
}

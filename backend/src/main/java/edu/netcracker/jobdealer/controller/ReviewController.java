package edu.netcracker.jobdealer.controller;

import edu.netcracker.jobdealer.dto.ReviewDto;
import edu.netcracker.jobdealer.entity.Review;
import edu.netcracker.jobdealer.service.ReviewService;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
public class ReviewController {

    private final Mapper mapper;
    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService, Mapper mapper) {
        this.reviewService = reviewService;
        this.mapper = mapper;
    }

    @GetMapping(value = "/accounts/{id}/reviews")
    public ResponseEntity<?> getReviews(@PathVariable("id") UUID id) {
        List<Review> reviews = reviewService.getUserReviews(id);
        List<ReviewDto> dtos = reviews.stream()
                .map(e -> mapper.map(e, ReviewDto.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }


    @GetMapping(value = "/accounts/{id}/reviews/my")
    public ResponseEntity<?> getOwnedReview(@PathVariable("id") UUID id, @AuthenticationPrincipal User user) {
        Optional<Review> ownedReview = reviewService.getOwnedReview(user.getUsername(), id);
        return ownedReview.isPresent() ? ResponseEntity.noContent().build() : ResponseEntity.ok(mapper.map(ownedReview, ReviewDto.class));
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping(value = "/accounts/{id}/reviews")
    public ResponseEntity<?> sendReview(@PathVariable("id") UUID receiver,
                                        @RequestParam String text,
                                        @AuthenticationPrincipal User user) {
        Review review = reviewService.sendReview(text, user.getUsername(), receiver);
        return ResponseEntity.ok(mapper.map(review, ReviewDto.class));
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping(value = "/reviews/{reviewId}/rating/increase")
    public ResponseEntity<?> deleteReview(@PathVariable("reviewId") UUID reviewId,
                                          @AuthenticationPrincipal User user) {
        reviewService.deleteReview(reviewId, user.getUsername());
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("isAuthenticated()")
    @PatchMapping(value = "/reviews/{reviewId}/rating/increase")
    public ResponseEntity<?> increaseRating(@PathVariable("reviewId") UUID reviewId,
                                            @AuthenticationPrincipal User user) {
        Review review = reviewService.increaseRating(reviewId, user.getUsername());
        return ResponseEntity.ok(mapper.map(review, ReviewDto.class));
    }

    @PreAuthorize("isAuthenticated()")
    @PatchMapping(value = "/reviews/{reviewId}/rating/decrease")
    public ResponseEntity<?> decreaseRating(@PathVariable("reviewId") UUID reviewId,
                                            @AuthenticationPrincipal User user) {
        Review review = reviewService.decreaseRating(reviewId, user.getUsername());
        return ResponseEntity.ok(mapper.map(review, ReviewDto.class));
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/reviews/{reviewId}/")
    public ResponseEntity<?> canVote(@AuthenticationPrincipal User user, @PathVariable UUID reviewId) {
        return ResponseEntity.ok(reviewService.canVote(reviewId, user.getUsername()));
    }


}

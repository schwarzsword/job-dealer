package edu.netcracker.jobdealer.controller;

import edu.netcracker.jobdealer.dto.ReviewDto;
import edu.netcracker.jobdealer.entity.Review;
import edu.netcracker.jobdealer.exceptions.DoubleVotingException;
import edu.netcracker.jobdealer.exceptions.NoPermissionException;
import edu.netcracker.jobdealer.exceptions.NotFoundException;
import edu.netcracker.jobdealer.exceptions.ReviewNotFountException;
import edu.netcracker.jobdealer.service.ReviewService;
import org.dozer.Mapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
public class ReviewController {

    private final Mapper mapper;

    private final ReviewService reviewService;

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


    @PreAuthorize("isAuthenticated()")
    @PostMapping(value = "/accounts/{id}/reviews")
    public ResponseEntity<?> sendReview(@PathVariable("id") UUID receiver,
                                        @RequestParam String text,
                                        @AuthenticationPrincipal User user) {
        try {
            reviewService.sendReview(text, user.getUsername(), receiver);
        } catch (NotFoundException ex) {
            return ResponseEntity.status(404).body(ex.getMessage());
        }
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping(value = "/reviews/{reviewId}")
    public ResponseEntity<?> deleteReview(@PathVariable("reviewId") UUID reviewId,
                                          @AuthenticationPrincipal User user) {
        try {
            reviewService.deleteReview(reviewId, user.getUsername());
        } catch (NoPermissionException ex) {
            return ResponseEntity.status(401).body(ex.getMessage());
        } catch (ReviewNotFountException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("isAuthenticated()")
    @PatchMapping(value = "/reviews/{reviewId}")
    public ResponseEntity<?> increaseRating(@PathVariable("reviewId") UUID reviewId,
                                            @AuthenticationPrincipal User user) {
        try {
            reviewService.increaseRating(reviewId, user.getUsername());
        } catch (DoubleVotingException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (ReviewNotFountException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("isAuthenticated()")
    @PatchMapping(value = "/reviews/{reviewId}")
    public ResponseEntity<?> decreaseRating(@PathVariable("reviewId") UUID reviewId,
                                            @AuthenticationPrincipal User user) {
        try {
            reviewService.decreaseRating(reviewId, user.getUsername());
        } catch (DoubleVotingException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (ReviewNotFountException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
        return ResponseEntity.noContent().build();
    }


//    @PreAuthorize("isAuthenticated()")
//    @GetMapping(value = "{email}/reviews/{reviewId}")
//    public ResponseEntity<?> getReview(@PathVariable("reviewId") UUID reviewId) {
//        Review review = reviewService.getReviewById(reviewId);
//        ReviewDto dto = mapper.map(review, ReviewDto.class);
//        return ResponseEntity.ok(dto);
//    }

}

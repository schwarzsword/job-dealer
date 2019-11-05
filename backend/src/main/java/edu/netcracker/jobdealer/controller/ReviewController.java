package edu.netcracker.jobdealer.controller;

import edu.netcracker.jobdealer.entity.Review;
import edu.netcracker.jobdealer.exceptions.DoubleVotingException;
import edu.netcracker.jobdealer.exceptions.MessageNotFoundException;
import edu.netcracker.jobdealer.exceptions.NoRightsException;
import edu.netcracker.jobdealer.exceptions.ReviewNotFountException;
import edu.netcracker.jobdealer.service.ReviewService;
import org.dozer.DozerBeanMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class ReviewController {

    private final DozerBeanMapper mapper;

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService, DozerBeanMapper mapper) {
        this.reviewService = reviewService;
        this.mapper = mapper;
    }

    @Secured({"ROLE_USER", "ROLE_COMPANY", "ROLE_ADMIN"})
    @GetMapping(value = "{email}/reviews")
    public ResponseEntity getUserReviews(@PathVariable("email") String email) {
        List<Review> reviews = reviewService.getUserReviews(email);
        //TODO make mappings
//        List<MessageDTO> dtos = userMessages.stream().map(e -> mapper.map(e, MessageDTO.class)).collect(Collectors.toList());
        return ResponseEntity.ok(reviews);
    }

    @Secured({"ROLE_USER", "ROLE_COMPANY", "ROLE_ADMIN"})
    @GetMapping(value = "{email}/reviews/{reviewId}")
    public ResponseEntity getReviews(@PathVariable("reviewId") UUID reviewId) {
        Review review = reviewService.getReviewById(reviewId);
        return ResponseEntity.ok(review);
    }

    @Secured({"ROLE_USER", "ROLE_COMPANY", "ROLE_ADMIN"})
    @PostMapping(value = "{email}/reviews")
    public ResponseEntity sendReviews(@RequestParam String who, @RequestParam String text, @AuthenticationPrincipal User user) {
        reviewService.sendReview(text, user.getUsername(), who);
        return ResponseEntity.ok().build();
    }

    @Secured({"ROLE_USER", "ROLE_COMPANY", "ROLE_ADMIN"})
    @DeleteMapping(value = "{email}/reviews/{reviewId}")
    public ResponseEntity deleteReview(@PathVariable("reviewId") UUID reviewId, @AuthenticationPrincipal User user) {
        try {
            reviewService.deleteReview(reviewId, user.getUsername());
        } catch (NoRightsException ex) {
            return ResponseEntity.badRequest().body("You have no permission to delete this message");
        } catch (ReviewNotFountException e) {
            return ResponseEntity.badRequest().body("No message found");
        }
        return ResponseEntity.ok().build();
    }

    @Secured({"ROLE_USER", "ROLE_COMPANY", "ROLE_ADMIN"})
    @PutMapping(value = "{email}/reviews/{reviewId}")
    public ResponseEntity increaseRating(@PathVariable("reviewId") UUID reviewId, @AuthenticationPrincipal User user, @RequestParam boolean raise) {
        try {
            reviewService.changeRating(reviewId, raise, user.getUsername());
        } catch (DoubleVotingException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (ReviewNotFountException e) {
            return ResponseEntity.badRequest().body("No review found");
        }
        return ResponseEntity.ok().build();
    }

}

package edu.netcracker.jobdealer.service;

import edu.netcracker.jobdealer.entity.Review;
import edu.netcracker.jobdealer.exceptions.AccountByIdNotFoundException;
import edu.netcracker.jobdealer.exceptions.DoubleVotingException;
import edu.netcracker.jobdealer.exceptions.NoPermissionException;
import edu.netcracker.jobdealer.exceptions.ReviewNotFountException;

import java.util.List;
import java.util.UUID;

public interface ReviewService {
    Review sendReview(String text, String srcEmail, UUID receiver);

    List<Review> getUserReviews(UUID userId);

    List<Review> getUserReviews(String email);

    Review getReviewById(UUID reviewId);

    Review increaseRating(UUID reviewId, String email) ;

    Review decreaseRating(UUID reviewId, String email);

    void deleteReview(UUID id, String ownerEmail);

    boolean canVote(UUID id, String ownerEmail);
}

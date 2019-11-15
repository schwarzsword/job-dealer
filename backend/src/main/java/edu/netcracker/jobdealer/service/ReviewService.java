package edu.netcracker.jobdealer.service;

import edu.netcracker.jobdealer.entity.Review;
import edu.netcracker.jobdealer.exceptions.AccountByIdNotFoundException;
import edu.netcracker.jobdealer.exceptions.DoubleVotingException;
import edu.netcracker.jobdealer.exceptions.NoPermissionException;
import edu.netcracker.jobdealer.exceptions.ReviewNotFountException;

import java.util.List;
import java.util.UUID;

public interface ReviewService {
    Review sendReview(String text, String srcEmail, UUID receiver) throws AccountByIdNotFoundException;

    List<Review> getUserReviews(UUID userId);

    List<Review> getUserReviews(String email);

    Review getReviewById(UUID reviewId) throws ReviewNotFountException;

    Review increaseRating(UUID reviewId, String email) throws ReviewNotFountException, DoubleVotingException;

    Review decreaseRating(UUID reviewId, String email) throws ReviewNotFountException, DoubleVotingException;

    void deleteReview(UUID mesId, String ownerEmail) throws ReviewNotFountException, NoPermissionException;
}

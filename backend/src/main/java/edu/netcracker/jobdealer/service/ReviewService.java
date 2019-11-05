package edu.netcracker.jobdealer.service;

import edu.netcracker.jobdealer.entity.Account;
import edu.netcracker.jobdealer.entity.Review;
import edu.netcracker.jobdealer.exceptions.DoubleVotingException;
import edu.netcracker.jobdealer.exceptions.NoRightsException;
import edu.netcracker.jobdealer.exceptions.ReviewNotFountException;
import edu.netcracker.jobdealer.exceptions.UserNotFoundException;

import java.util.List;
import java.util.UUID;

public interface ReviewService {
    public Review sendReview(String text, String srcEmail, String destEmail) throws UserNotFoundException;

    public List<Review> getUserReviews(UUID userId);

    public List<Review> getUserReviews(String email);

    Review getReviewById(UUID reviewId) throws ReviewNotFountException;

    Review changeRating(UUID reviewId, boolean raise, String email) throws ReviewNotFountException, DoubleVotingException;

    void deleteReview(UUID mesId, String ownerEmail) throws ReviewNotFountException, NoRightsException;
}

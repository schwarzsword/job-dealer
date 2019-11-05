package edu.netcracker.jobdealer.service;

import edu.netcracker.jobdealer.entity.Account;
import edu.netcracker.jobdealer.entity.Review;
import edu.netcracker.jobdealer.exceptions.ReviewNotFountException;

import java.util.List;
import java.util.UUID;

public interface ReviewService {
    Review sendReview(String text, Account src, Account dest);

    public List<Review> getUserReviews(UUID userId);

    public List<Review> getUserReviews(String email);

    Review getReviewById(UUID reviewId) throws ReviewNotFountException;

    Review changeRating(Review review, boolean raise);
}

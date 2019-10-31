package edu.netcracker.jobdealer.service;

import edu.netcracker.jobdealer.entity.Account;
import edu.netcracker.jobdealer.entity.Review;
import edu.netcracker.jobdealer.exceptions.ReviewNotFountException;

import java.util.List;
import java.util.UUID;

public interface ReviewService {
    Review sendReview(String text, Account src, Account dest);

    List<Review> getReviews(Account user);

    Review getReviewById(UUID reviewId) throws ReviewNotFountException;

    Review changeRating(Review review, boolean raise);
}

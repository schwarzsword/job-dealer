package edu.netcracker.jobdealer.service.impl;

import edu.netcracker.jobdealer.entity.Account;
import edu.netcracker.jobdealer.entity.Message;
import edu.netcracker.jobdealer.entity.Review;
import edu.netcracker.jobdealer.exceptions.ReviewNotFountException;
import edu.netcracker.jobdealer.repository.AccountRepository;
import edu.netcracker.jobdealer.repository.ReviewRepository;
import edu.netcracker.jobdealer.service.ReviewService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Transactional
@Service("reviewService")
public class ReviewServiceImpl implements ReviewService {

    private final AccountRepository accountRepository;

    private final ReviewRepository reviewRepository;

    public ReviewServiceImpl(AccountRepository accountRepository, ReviewRepository reviewRepository) {
        this.accountRepository = accountRepository;
        this.reviewRepository = reviewRepository;
    }

    @Override
    public Review sendReview(String text, Account src, Account dest) {
        Review review = new Review(text, src, dest);
        reviewRepository.save(review);
        return review;
    }

    @Override
    public List<Review> getUserReviews(UUID userId) {
        return reviewRepository.findAllByReviewDest_Id(userId);
    }

    @Override
    public List<Review> getUserReviews(String email) {
        return reviewRepository.findAllByReviewDest_Email(email);
    }

    @Override
    public Review getReviewById(UUID reviewId) throws ReviewNotFountException {
        Optional<Review> byId = reviewRepository.findById(reviewId);
        if (byId.isPresent()) {
            return byId.get();
        } else throw new ReviewNotFountException("Review not found");
    }

    @Override
    public Review changeRating(Review review, boolean raise) {
        int rating = review.getRating();
        if (raise) {
            review.setRating(--rating);
        } else {
            review.setRating(++rating);
        }
        reviewRepository.save(review);
        return review;
    }
}

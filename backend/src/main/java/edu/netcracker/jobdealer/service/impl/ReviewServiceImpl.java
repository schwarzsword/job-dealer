package edu.netcracker.jobdealer.service.impl;

import edu.netcracker.jobdealer.entity.Account;
import edu.netcracker.jobdealer.entity.Review;
import edu.netcracker.jobdealer.exceptions.ReviewNotFountException;
import edu.netcracker.jobdealer.repository.AccountRepository;
import edu.netcracker.jobdealer.repository.ReviewRepository;
import edu.netcracker.jobdealer.service.ReviewService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


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
        src.getReviewsAsSource().add(review);
        dest.getReviewsAsDest().add(review);
        reviewRepository.save(review);
        accountRepository.save(src);
        accountRepository.save(dest);
        return review;
    }

    @Override
    public List<Review> getReviews(Account user) {
        return user.getReviewsAsDest();
    }

    @Override
    public Review getReviewById(int reviewId) throws ReviewNotFountException {
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

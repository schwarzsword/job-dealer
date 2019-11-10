package edu.netcracker.jobdealer.service.impl;

import edu.netcracker.jobdealer.entity.Account;
import edu.netcracker.jobdealer.entity.Review;
import edu.netcracker.jobdealer.exceptions.DoubleVotingException;
import edu.netcracker.jobdealer.exceptions.NoRightsException;
import edu.netcracker.jobdealer.exceptions.ReviewNotFountException;
import edu.netcracker.jobdealer.exceptions.AccountNotFoundException;
import edu.netcracker.jobdealer.repository.ReviewRepository;
import edu.netcracker.jobdealer.service.AccountService;
import edu.netcracker.jobdealer.service.ReviewService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Transactional
@Service
public class ReviewServiceImpl implements ReviewService {

    private final AccountService accountService;

    private final ReviewRepository reviewRepository;

    public ReviewServiceImpl(AccountService accountService, ReviewRepository reviewRepository) {
        this.accountService = accountService;
        this.reviewRepository = reviewRepository;
    }

    @Override
    public Review sendReview(String text, String srcEmail, String destEmail) throws AccountNotFoundException {
        Account src = accountService.getByEmail(srcEmail);
        Account dest = accountService.getByEmail(destEmail);
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
    public Review changeRating(UUID reviewId, boolean raise, String email) throws ReviewNotFountException, DoubleVotingException {
        Account account = accountService.getByEmail(email);
        Review review = getReviewById(reviewId);
        List<Account> increased = review.getIncreased();
        if (!increased.contains(account)) {
            int rating = review.getRating();
            if (raise) {
                review.setRating(++rating);
            } else {
                review.setRating(--rating);
            }
            increased.add(account);
            review.setIncreased(increased);
            reviewRepository.save(review);
            return review;
        } else throw new DoubleVotingException("You can't vote twice");
    }

    @Override
    public void deleteReview(UUID reviewId, String ownerEmail) throws ReviewNotFountException, NoRightsException {
        Review review = getReviewById(reviewId);
        if (review.getReviewSource().getEmail().equals(ownerEmail)) {
            reviewRepository.delete(review);
        } else throw new NoRightsException("You can delete only owned messages");
    }

}


package edu.netcracker.jobdealer.service.impl;

import edu.netcracker.jobdealer.entity.Account;
import edu.netcracker.jobdealer.entity.Review;
import edu.netcracker.jobdealer.exceptions.AccountByIdNotFoundException;
import edu.netcracker.jobdealer.exceptions.DoubleVotingException;
import edu.netcracker.jobdealer.exceptions.NoPermissionException;
import edu.netcracker.jobdealer.exceptions.ReviewNotFountException;
import edu.netcracker.jobdealer.repository.ReviewRepository;
import edu.netcracker.jobdealer.service.AccountService;
import edu.netcracker.jobdealer.service.ReviewService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


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
    public Review sendReview(String text, String srcEmail, UUID receiver) throws AccountByIdNotFoundException {
        Account src = accountService.getByEmail(srcEmail);
        Account dest = accountService.getById(receiver);
        Review review = new Review(text, src, dest);
        reviewRepository.save(review);
        return review;
    }

    @Override
    public List<Review> getUserReviews(UUID userId) {
        return reviewRepository.findAllByReviewDestId(userId);
    }

    @Override
    public List<Review> getUserReviews(String email) {
        return reviewRepository.findAllByReviewDestEmail(email);
    }

    @Override
    public Review getReviewById(UUID reviewId) throws ReviewNotFountException {
        return reviewRepository.findById(reviewId).orElseThrow(ReviewNotFountException::new);
    }

    @Override
    public Review increaseRating(UUID reviewId, String email) throws ReviewNotFountException, DoubleVotingException {
        Account account = accountService.getByEmail(email);
        Review review = getReviewById(reviewId);
        List<Account> increased = review.getVoted();
        if (!increased.contains(account)) {
            int rating = review.getRating();
            review.setRating(++rating);
            increased.add(account);
            review.setVoted(increased);
            reviewRepository.save(review);
            return review;
        } else throw new DoubleVotingException();
    }

    @Override
    public Review decreaseRating(UUID reviewId, String email) throws ReviewNotFountException, DoubleVotingException {
        Account account = accountService.getByEmail(email);
        Review review = getReviewById(reviewId);
        List<Account> increased = review.getVoted();
        if (!increased.contains(account)) {
            int rating = review.getRating();
            review.setRating(--rating);
            increased.add(account);
            review.setVoted(increased);
            reviewRepository.save(review);
            return review;
        } else throw new DoubleVotingException();
    }

    @Override
    public void deleteReview(UUID reviewId, String ownerEmail) throws ReviewNotFountException, NoPermissionException {
        Review review = getReviewById(reviewId);
        if (review.getReviewSource().getEmail().equals(ownerEmail)) {
            reviewRepository.delete(review);
        } else throw new NoPermissionException("You can delete only owned reviews");
    }

    @Override
    public boolean canVote(UUID id, String ownerEmail) {
        Review review = reviewRepository.findById(id).orElseThrow(ReviewNotFountException::new);
        return review.getVoted().stream()
                .map(Account::getEmail)
                .collect(Collectors.toList())
                .contains(ownerEmail);
    }

}


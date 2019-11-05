package edu.netcracker.jobdealer.repository;


import edu.netcracker.jobdealer.entity.Account;
import edu.netcracker.jobdealer.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ReviewRepository extends JpaRepository<Review, UUID> {
    List<Review> findAllByReviewDest_Email(String email);

    List<Review> findAllByReviewSource_Email(String email);

    List<Review> findAllByReviewDest_Id(UUID id);

    List<Review> findAllByReviewSource_Id(UUID id);

    List<Review> findAllByReviewDest(Account account);

    List<Review> findAllByReviewSource(Account account);

    void deleteAllByReviewDest(Account account);

    void deleteAllByReviewSource(Account account);

    void deleteAllByReviewDest_Email(String email);

    void deleteAllByReviewSource_Email(String email);

    void deleteAllByReviewDest_Id(UUID id);

    void deleteAllByReviewSource_Id(UUID id);
}
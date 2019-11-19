package edu.netcracker.jobdealer.repository;


import edu.netcracker.jobdealer.entity.Account;
import edu.netcracker.jobdealer.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReviewRepository extends JpaRepository<Review, UUID> {
    List<Review> findAllByReviewDestEmail(String email);

    List<Review> findAllByReviewSourceEmail(String email);

    List<Review> findAllByReviewDestId(UUID id);

    List<Review> findAllByReviewSourceId(UUID id);

    List<Review> findAllByReviewDest(Account account);

    List<Review> findAllByReviewSource(Account account);

    void deleteAllByReviewDest(Account account);

    void deleteAllByReviewSource(Account account);

    void deleteAllByReviewDestEmail(String email);

    void deleteAllByReviewSourceEmail(String email);

    void deleteAllByReviewDestId(UUID id);

    void deleteAllByReviewSourceId(UUID id);

    Optional<Review> findByReviewDestEmailAndReviewSourceEmail(String dest, String source);

    Optional<Review> findByReviewDestIdAndReviewSourceId(UUID dest, UUID source);
}
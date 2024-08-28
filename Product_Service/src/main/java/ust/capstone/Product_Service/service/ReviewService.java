package ust.capstone.Product_Service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ust.capstone.Product_Service.model.Review;
import ust.capstone.Product_Service.repository.ReviewRepository;

import java.util.List;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    public Review createReview(Review review) {
        return reviewRepository.save(review);
    }

    public Review getReviewById(String id) {
        return reviewRepository.findById(id).orElseThrow(() -> new RuntimeException("Review not found"));
    }

    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    public Review updateReview(String id, Review reviewDetails) {
        Review review = getReviewById(id);
        review.setComment(reviewDetails.getComment());  // Changed from setContent to setComment
        review.setRating(reviewDetails.getRating());
        return reviewRepository.save(review);
    }


    public void deleteReview(String id) {
        reviewRepository.deleteById(id);
    }
}

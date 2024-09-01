package ust.capstone.Product_Service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ust.capstone.Product_Service.exception.ReviewNotFoundException;
import ust.capstone.Product_Service.model.Product;
import ust.capstone.Product_Service.model.Review;
import ust.capstone.Product_Service.repository.ProductRepository;
import ust.capstone.Product_Service.repository.ReviewRepository;

import java.util.List;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ProductRepository productRepository;

    public Review createReview(Review review) {
        // Save the review
        Review savedReview = reviewRepository.save(review);

        // Find the associated product
        Product product = productRepository.findById(review.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Update the product's review IDs
        product.getReviewIds().add(savedReview.getId());
        productRepository.save(product);

        return savedReview;
    }

    public Review getReviewById(String id) {
        return reviewRepository.findById(id)
                .orElseThrow(() -> new ReviewNotFoundException("Review not found with id: " + id));
    }

    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    public Review updateReview(String id, Review reviewDetails) {
        Review review = getReviewById(id);
        review.setComment(reviewDetails.getComment());
        review.setRating(reviewDetails.getRating());
        review.setReviewDate(reviewDetails.getReviewDate());
        return reviewRepository.save(review);
    }

    public void deleteReview(String id) {
        Review review = getReviewById(id);
        reviewRepository.delete(review);
    }
    public List<Review> getReviewsByUserId(String userId) {
        return reviewRepository.findByUserId(userId);
    }

    public List<Review> getReviewsByProductId(String productId) {
        return reviewRepository.findByProductId(productId);
    }
}

package com.dinhchieu.demo.service.Impl;

import com.dinhchieu.demo.dao.ProductRepository;
import com.dinhchieu.demo.dao.ReviewRepository;
import com.dinhchieu.demo.dao.UserRepository;
import com.dinhchieu.demo.dto.request.ReviewRequestDTO;
import com.dinhchieu.demo.dto.request.ReviewUpdateRequestDTO;
import com.dinhchieu.demo.dto.response.ReviewResponseDTO;
import com.dinhchieu.demo.entity.Product;
import com.dinhchieu.demo.entity.Review;
import com.dinhchieu.demo.entity.User;
import com.dinhchieu.demo.handle.ProductNotFoundException;
import com.dinhchieu.demo.handle.ReviewNotFoundException;
import com.dinhchieu.demo.handle.UserNotFoundException;
import com.dinhchieu.demo.service.ReviewService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;


    private ReviewResponseDTO mapToReviewResponseDTO(Review review){
        ReviewResponseDTO dto = new ReviewResponseDTO();
        dto.setId(review.getId());
        dto.setProductId(review.getProduct().getId());
        dto.setBuyerId(review.getBuyerId());
        dto.setRating(review.getRating());
        dto.setComment(review.getComment());
        dto.setCreateAt(review.getCreateAt());
        return dto;
    }

    @Override
    @Transactional
    public ReviewResponseDTO addReview(ReviewRequestDTO reviewRequestDTO) {
        Optional<Product> product = productRepository.findById(reviewRequestDTO.getProductId());
        Optional<User> user = userRepository.findById(reviewRequestDTO.getBuyerId());

        if (product.isEmpty()) {
            throw new ProductNotFoundException("Product with id " + reviewRequestDTO.getProductId() +  " is not found!");
        }

        if(reviewRequestDTO.getRating() < 1 || reviewRequestDTO.getRating() > 5){
            throw new IllegalArgumentException("Rating must be between 1 and 5");
        }

        if(reviewRequestDTO.getComment().length() < 5){
            throw new IllegalArgumentException("Comment must be at least 5 characters");
        }

        if(user.isEmpty()){
            throw new UserNotFoundException("User with id " + reviewRequestDTO.getBuyerId() + " is not found!");
        }


       Product existingProduct = product.get();
        Review review = new Review();
        review.setProduct(existingProduct);
        review.setBuyerId(user.get().getId());
        review.setRating(reviewRequestDTO.getRating());
        review.setComment(reviewRequestDTO.getComment());
        review.setCreateAt(System.currentTimeMillis());
        reviewRepository.save(review);

        return mapToReviewResponseDTO(review);
    }

    @Override
    @Transactional
    public void removeReview(int reviewId) {
        Optional<Review> review = reviewRepository.findById(reviewId);
        if(review.isEmpty()){
            throw new ReviewNotFoundException("Review with id " + reviewId + " is not found!");
        }

        reviewRepository.deleteById(reviewId);
    }

    @Override
    @Transactional
    public ReviewResponseDTO updateReview(int reviewId, ReviewUpdateRequestDTO reviewUpdateRequestDTO) {

       Optional<Review> review = reviewRepository.findById(reviewId);
        if(review.isEmpty()){
            throw new ReviewNotFoundException("Review with id " + reviewId + " is not found!");
        }

        Review existingReview = review.get();
        existingReview.setRating(reviewUpdateRequestDTO.getRating());
        existingReview.setComment(reviewUpdateRequestDTO.getComment());
        existingReview.setCreateAt(System.currentTimeMillis());
        reviewRepository.save(existingReview);

        return mapToReviewResponseDTO(existingReview);
    }

    @Override
    public ReviewResponseDTO getReviewById(int id) {
        Optional<Review> review = reviewRepository.findById(id);

        if(review.isEmpty()){
            throw new ReviewNotFoundException("Review with id " + id + " is not found!");
        }

        Review existingReview = review.get();
        return mapToReviewResponseDTO(existingReview);
    }

    @Override
    public ReviewResponseDTO getReviewByProductId(int productId) {

        Optional<Product> product = productRepository.findById(productId);
        if(product.isEmpty()){
            throw new ProductNotFoundException("Product with id " + productId + " is not found!");
        }

        Product existingProduct = product.get();
        Review review = reviewRepository.findReviewByProduct(existingProduct);
        return mapToReviewResponseDTO(review);

    }
}

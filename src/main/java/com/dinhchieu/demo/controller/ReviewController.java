package com.dinhchieu.demo.controller;

import com.dinhchieu.demo.dto.request.ReviewRequestDTO;
import com.dinhchieu.demo.dto.request.ReviewUpdateRequestDTO;
import com.dinhchieu.demo.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @GetMapping("/api/v1/reviews/product/{productId}")
    public ResponseEntity<?> getReviewByProductId(@PathVariable int productId) {
        return ResponseEntity.status(HttpStatus.OK.value()).body(reviewService.getReviewByProductId(productId));
    }

    @GetMapping("/api/v1/reviews/{id}")
    public ResponseEntity<?> getReviewById(@PathVariable int id) {
        return ResponseEntity.status(HttpStatus.OK.value()).body(reviewService.getReviewById(id));
    }

    @PostMapping("/api/v1/reviews")
    public ResponseEntity<?> addReview(@RequestBody ReviewRequestDTO reviewRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED.value()).body(reviewService.addReview(reviewRequestDTO));
    }

    @DeleteMapping("/api/v1/reviews/{id}")
    public ResponseEntity<?> removeReview(@PathVariable int id) {
        reviewService.removeReview(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Review with id " + id + " is deleted!");
    }

    @PatchMapping("/api/v1/reviews/{id}")
    public ResponseEntity<?> updateReview(@PathVariable int id, @RequestBody ReviewUpdateRequestDTO reviewUpdateRequestDTO) {
        return ResponseEntity.status(HttpStatus.OK.value()).body(reviewService.updateReview(id, reviewUpdateRequestDTO ));
    }
}

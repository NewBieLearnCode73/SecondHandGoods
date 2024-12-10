package com.dinhchieu.demo.service;

import com.dinhchieu.demo.dto.request.ReviewRequestDTO;
import com.dinhchieu.demo.dto.request.ReviewUpdateRequestDTO;
import com.dinhchieu.demo.dto.response.ReviewResponseDTO;

public interface ReviewService {
    public ReviewResponseDTO addReview(ReviewRequestDTO reviewRequestDTO);
    public void removeReview(int reviewId);
    public ReviewResponseDTO updateReview(int reviewId, ReviewUpdateRequestDTO reviewUpdateRequestDTO);
    public ReviewResponseDTO getReviewById(int id);
    public ReviewResponseDTO getReviewByProductId(int productId);
}

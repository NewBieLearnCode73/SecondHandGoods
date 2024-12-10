package com.dinhchieu.demo.dao;

import com.dinhchieu.demo.entity.Product;
import com.dinhchieu.demo.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {
    Review findReviewByProduct(Product product);
}

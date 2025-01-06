package com.dinhchieu.demo.dao;

import com.dinhchieu.demo.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image, Integer> {
    void deleteImagesByProduct_Id(int productId);
}
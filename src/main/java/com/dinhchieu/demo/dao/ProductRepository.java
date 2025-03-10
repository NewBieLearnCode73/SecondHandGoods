package com.dinhchieu.demo.dao;

import com.dinhchieu.demo.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    Page<Product> findAllByOwnerId(int userId, Pageable pageable);
    Page<Product> findAllByTypeId(int typeId, Pageable pageable);
}

package com.dinhchieu.demo.dao;

import com.dinhchieu.demo.dto.response.ProductDetailResponseDTO;
import com.dinhchieu.demo.entity.Product;
import com.dinhchieu.demo.entity.Type;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TypeRepository extends JpaRepository<Type, Integer> {
    @Query("SELECT t.typeName FROM Type t")
    List<String> getAllTypeName();
}

package com.dinhchieu.demo.dao;

import com.dinhchieu.demo.entity.Type;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TypeRepository extends JpaRepository<Type, Integer> {
}

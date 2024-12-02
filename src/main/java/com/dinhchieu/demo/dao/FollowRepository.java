package com.dinhchieu.demo.dao;

import com.dinhchieu.demo.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<Follow, Integer> {
}

package com.dinhchieu.demo.dao;

import com.dinhchieu.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}

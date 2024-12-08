package com.dinhchieu.demo.dao;

import com.dinhchieu.demo.entity.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StateRepository extends JpaRepository<State, Integer> {
    State findByName(String name);
}

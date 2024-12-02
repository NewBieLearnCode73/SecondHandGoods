package com.dinhchieu.demo.dao;

import com.dinhchieu.demo.entity.State;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StateRepository extends JpaRepository<State, Integer> {
}

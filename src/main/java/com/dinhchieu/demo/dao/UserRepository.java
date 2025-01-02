package com.dinhchieu.demo.dao;

import com.dinhchieu.demo.entity.Role;
import com.dinhchieu.demo.entity.User;
import com.dinhchieu.demo.utils.AccountState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {
    User findUserByUsername(String username);
    User findUserByEmail(String email);
    User findUserByPhone(String phone);
    User findUserById(int id);
    List<User> findAllByAccountState(AccountState accountState);
}

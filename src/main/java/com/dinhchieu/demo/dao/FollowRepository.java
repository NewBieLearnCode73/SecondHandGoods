package com.dinhchieu.demo.dao;

import com.dinhchieu.demo.entity.Follow;
import com.dinhchieu.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Integer> {
    boolean existsByFollowerAndFollowee(User follower, User followee);
    void deleteByFollowerAndFollowee(User follower, User followee);
    int countByFollowee(User followee);
    int countByFollower(User follower);
}

package com.dinhchieu.demo.service.Impl;

import com.dinhchieu.demo.dao.FollowRepository;
import com.dinhchieu.demo.dao.UserRepository;
import com.dinhchieu.demo.entity.Follow;
import com.dinhchieu.demo.entity.User;
import com.dinhchieu.demo.handle.UserNotFoundException;
import com.dinhchieu.demo.service.FollowService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FollowServiceImpl implements FollowService {

    @Autowired
    private FollowRepository followRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public void follow(int followerId, int followeeId) throws Exception {
        Optional<User> follower = userRepository.findById(followerId);
        if (follower.isEmpty()) {
            throw new UserNotFoundException("Follower with id " + followerId + " is not found!");
        }

        Optional<User> followee = userRepository.findById(followeeId);
        if (followee.isEmpty()) {
            throw new UserNotFoundException("Followee with id " + followeeId + " is not found!");
        }

        if (followRepository.existsByFollowerAndFollowee(follower.get(), followee.get())) {
            throw new Exception("Follower with id " + followerId + " is already following followee with id " + followeeId);
        }

        Follow follow = new Follow();
        follow.setFollower(follower.get());
        follow.setFollowee(followee.get());

        followRepository.save(follow);
    }

    @Override
    @Transactional
    public void unfollow(int followerId, int followeeId) throws Exception {
        Optional<User> follower = userRepository.findById(followerId);
        if (follower.isEmpty()) {
            throw new UserNotFoundException("Follower with id " + followerId + " is not found!");
        }

        Optional<User> followee = userRepository.findById(followeeId);
        if (followee.isEmpty()) {
            throw new UserNotFoundException("Followee with id " + followeeId + " is not found!");
        }

        boolean existsByFollowerAndFollowee = followRepository.existsByFollowerAndFollowee(follower.get(), followee.get());

        if (!existsByFollowerAndFollowee) {
            throw new Exception("Follower with id " + followerId + " is not following followee with id " + followeeId);
        }

        followRepository.deleteByFollowerAndFollowee(follower.get(), followee.get());
    }

    @Override
    public int countFollowers(int id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new UserNotFoundException("User with id " + id + " is not found!");
        }

        return followRepository.countByFollowee(user.get());
    }

    @Override
    public int countFollowees(int id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new UserNotFoundException("User with id " + id + " is not found!");
        }

        return followRepository.countByFollower(user.get());
    }
}
package com.dinhchieu.demo.service;


public interface FollowService {
     void follow(int followerId, int followeeId) throws Exception;
     void unfollow(int followerId, int followeeId) throws Exception;
     int countFollowers(int Id); // Đếm số người theo dõi của user có id là Id
     int countFollowees(int followerId);
}

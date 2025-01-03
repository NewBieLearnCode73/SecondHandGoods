package com.dinhchieu.demo.controller;

import com.dinhchieu.demo.dto.request.FollowRequestDTO;
import com.dinhchieu.demo.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class FollowController {
    @Autowired
    private FollowService followService;

    @GetMapping("/api/v1/followers/{id}")
    public ResponseEntity<?> getFollowers(@PathVariable int id) {
        return ResponseEntity.ok(followService.countFollowers(id));
    }

    @GetMapping("/api/v1/followees/{id}")
    public ResponseEntity<?> getFollowees(@PathVariable int id) {
        return ResponseEntity.ok(followService.countFollowees(id));
    }

    @PostMapping("/api/v1/follow")
    public ResponseEntity<?> follow(@RequestBody FollowRequestDTO followRequestDTO) throws Exception {
        followService.follow(followRequestDTO.getFollowerId(), followRequestDTO.getFolloweeId());
        return ResponseEntity.ok("Follow successfully");
    }

    @DeleteMapping("/api/v1/follow")
    public ResponseEntity<?> unfollow(@RequestBody FollowRequestDTO followRequestDTO) throws Exception {
        followService.unfollow(followRequestDTO.getFollowerId(), followRequestDTO.getFolloweeId());
        return ResponseEntity.ok("Unfollow successfully");
    }


}

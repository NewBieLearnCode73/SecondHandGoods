package com.dinhchieu.demo.dto.request;

import lombok.Data;

@Data
public class FollowRequestDTO {
    private int followerId;
    private int followeeId;
}

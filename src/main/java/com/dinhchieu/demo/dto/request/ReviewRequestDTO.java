package com.dinhchieu.demo.dto.request;

import lombok.Data;

@Data
public class ReviewRequestDTO {
    private int buyerId;
    private int rating;
    private String comment;
    private int productId;
}

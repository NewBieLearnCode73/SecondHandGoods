package com.dinhchieu.demo.dto.response;

import lombok.Data;

@Data
public class ReviewResponseDTO {
    private int id;
    private int buyerId;
    private int rating;
    private String comment;
    private Long createAt;
    private int productId;
}

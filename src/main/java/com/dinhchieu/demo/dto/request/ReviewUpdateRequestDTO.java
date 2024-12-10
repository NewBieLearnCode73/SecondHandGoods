package com.dinhchieu.demo.dto.request;

import lombok.Data;

@Data
public class ReviewUpdateRequestDTO {
    private int rating;
    private String comment;
}

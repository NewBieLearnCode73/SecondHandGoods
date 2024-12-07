package com.dinhchieu.demo.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class ProductRequestDTO {
    private int userId;
    private int typeId;
    private String name;
    private int quantity;
    private String description;
    private String warranty;
}

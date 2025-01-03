package com.dinhchieu.demo.dto.response;

import com.dinhchieu.demo.entity.Image;
import jakarta.persistence.Lob;
import lombok.Data;

import java.util.List;

@Data
public class ProductDetailResponseDTO {
    private int id;
    private String name;
    private int quantity;
    private String description;
    private double price;
    private String warranty;
    private Long createAt;
    private String typeName;
    private String ownerUsername;
    private String state;
    @Lob
    private List<String> imageDataList;
}

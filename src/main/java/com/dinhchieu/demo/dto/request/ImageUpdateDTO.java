package com.dinhchieu.demo.dto.request;

import jakarta.persistence.Lob;
import lombok.Data;

@Data
public class ImageUpdateDTO {
    @Lob
    private String data;
}

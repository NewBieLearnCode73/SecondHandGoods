package com.dinhchieu.demo.dto.response;

import lombok.Data;

@Data
public class PaymentControllerResponseDTO {
    private String code;
    private String message;


    public PaymentControllerResponseDTO(String code, String message) {
        this.code = code;
        this.message = message;
    }
}

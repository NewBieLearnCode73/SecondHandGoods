package com.dinhchieu.demo.dto.response;

import lombok.Data;

@Data
public class PaymentResponseDTO {
    private String code;
    private String message;
    private String paymentUrl;

    public PaymentResponseDTO(String code, String message,String paymentUrl) {
        this.paymentUrl = paymentUrl;
        this.code = code;
        this.message = message;
    }
}
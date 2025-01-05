package com.dinhchieu.demo.dto.request;

import lombok.Data;

@Data
public class PaymentRequestDTO {
    private int userId;
    private long amount;
    private String bankCode;
}

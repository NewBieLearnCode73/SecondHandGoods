package com.dinhchieu.demo.dto.request;

import lombok.Data;
import lombok.NonNull;

@Data
public class ProductStateUpdateRequestDTO {
    @NonNull
    private int stateId;
    private String rejectionReason;
}

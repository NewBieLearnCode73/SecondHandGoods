package com.dinhchieu.demo.dto.request;

import lombok.Data;

@Data
public class UpdatePasswordRequestDTO {
    private String oldPassword;
    private String newPassword;
}

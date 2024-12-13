package com.dinhchieu.demo.dto.request;

import lombok.Data;

@Data
public class UserUpdateInformRequestDTO {
    private String email;
    private String phone;
    private String address;
}

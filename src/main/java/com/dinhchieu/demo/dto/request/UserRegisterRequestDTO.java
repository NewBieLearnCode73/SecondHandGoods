package com.dinhchieu.demo.dto.request;

import lombok.Data;

@Data
public class UserRegisterRequestDTO {
    private String username;
    private String password;
    private String email;
    private String phone;
    private String address;
}


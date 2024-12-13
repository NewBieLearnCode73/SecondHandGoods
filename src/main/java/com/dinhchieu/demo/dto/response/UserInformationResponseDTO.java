package com.dinhchieu.demo.dto.response;

import lombok.Data;

@Data
public class UserInformationResponseDTO {
    private int id;
    private String username;
    private String email;
    private String phone;
    private Float balance;
    private String address;
    private Boolean activation;
    private String accountState;
    private String roleName;
}

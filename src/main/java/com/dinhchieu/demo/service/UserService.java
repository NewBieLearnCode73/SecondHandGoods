package com.dinhchieu.demo.service;

import com.dinhchieu.demo.dto.request.UserRegisterRequestDTO;
import com.dinhchieu.demo.dto.request.UserUpdateInformRequestDTO;
import com.dinhchieu.demo.dto.response.PaginationResponseDTO;
import com.dinhchieu.demo.dto.response.UserInformationResponseDTO;

import java.util.Map;

public interface UserService {
    UserInformationResponseDTO getUserById(int id);
    UserInformationResponseDTO getUserByUsername(String username);
    UserInformationResponseDTO getUserByEmail(String email);
    PaginationResponseDTO<UserInformationResponseDTO> getAllUsers(int pageNo, int pageSize, String sortBy, Map<String, String> filters);


    UserInformationResponseDTO registerUser(UserRegisterRequestDTO userRegisterRequestDTO);
    UserInformationResponseDTO updateBasicInformationUser(int userId, UserUpdateInformRequestDTO userUpdateInformRequestDTO);
    UserInformationResponseDTO updateUserAccountState(int userId, String accountState);
    void updateUserPassword(int userId, String oldPassword, String newPassword);


    void addBalance(int userId, float balance);
    void subtractBalance(int userId, float balance);
}
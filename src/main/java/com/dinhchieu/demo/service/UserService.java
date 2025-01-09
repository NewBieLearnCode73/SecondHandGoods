package com.dinhchieu.demo.service;

import com.dinhchieu.demo.dto.request.UserRegisterRequestDTO;
import com.dinhchieu.demo.dto.request.UserUpdateInformRequestDTO;
import com.dinhchieu.demo.dto.response.PaginationResponseDTO;
import com.dinhchieu.demo.dto.response.UserInformationResponseDTO;
import com.dinhchieu.demo.entity.User;

import java.util.Map;

public interface UserService {
    UserInformationResponseDTO getUserById(int id);
    UserInformationResponseDTO getUserByUsername(String username);
    UserInformationResponseDTO getUserByEmail(String email);
    PaginationResponseDTO<UserInformationResponseDTO> getAllUsers(int pageNo, int pageSize, String sortBy, Map<String, String> filters);
    void activeUser(String email, String token);

    UserInformationResponseDTO registerUser(UserRegisterRequestDTO userRegisterRequestDTO);
    UserInformationResponseDTO updateBasicInformationUser(int userId, UserUpdateInformRequestDTO userUpdateInformRequestDTO);
    UserInformationResponseDTO updateUserAccountState(int userId, String accountState);
    void updateUserPassword(int userId, String oldPassword, String newPassword);


    User getUserEntityById(int id);

    void addBalance(int userId, long balance);
    void subtractBalance(int userId, long balance);
}
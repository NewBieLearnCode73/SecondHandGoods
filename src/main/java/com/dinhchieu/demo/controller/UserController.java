package com.dinhchieu.demo.controller;

import com.dinhchieu.demo.dto.request.AccountStateRequestDTO;
import com.dinhchieu.demo.dto.request.UpdatePasswordRequestDTO;
import com.dinhchieu.demo.dto.request.UserRegisterRequestDTO;
import com.dinhchieu.demo.dto.request.UserUpdateInformRequestDTO;
import com.dinhchieu.demo.dto.response.PaginationResponseDTO;
import com.dinhchieu.demo.dto.response.UserInformationResponseDTO;
import com.dinhchieu.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/api/v1/users")
    public ResponseEntity<?> showAllUsers(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(required = false) Map<String, String> filters
            )
    {
        PaginationResponseDTO<UserInformationResponseDTO> response = userService.getAllUsers(pageNo, pageSize, sortBy, filters);
        return ResponseEntity.status(HttpStatus.OK.value()).body(response);
    }

    @GetMapping("/api/v1/users/{id}")
    public ResponseEntity<?> getUserById(@PathVariable int id) {
        return ResponseEntity.status(HttpStatus.OK.value()).body(userService.getUserById(id));
    }

    @GetMapping("/api/v1/users/username/{username}")
    public ResponseEntity<?> getUserByUsername(@PathVariable String username) {
        return ResponseEntity.status(HttpStatus.OK.value()).body(userService.getUserByUsername(username));
    }

    @GetMapping("/api/v1/users/email/{email}")
    public ResponseEntity<?> getUserByEmail(@PathVariable String email) {
        return ResponseEntity.status(HttpStatus.OK.value()).body(userService.getUserByEmail(email));
    }

    @PostMapping("/api/v1/users/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRegisterRequestDTO userRegisterRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED.value()).body(userService.registerUser(userRegisterRequestDTO));
    }

    @PatchMapping("/api/v1/users/updateProfile/{id}")
    public ResponseEntity<?> updateBasicInformationUser(@PathVariable int id, @RequestBody UserUpdateInformRequestDTO userUpdateInformRequestDTO) {
        return ResponseEntity.status(HttpStatus.OK.value()).body(userService.updateBasicInformationUser(id, userUpdateInformRequestDTO));
    }

    @PatchMapping("/api/v1/users/updateAccountState/{id}")
    public ResponseEntity<?> updateAccountStateUser(@PathVariable int id, @RequestBody AccountStateRequestDTO accountState) {
        return ResponseEntity.status(HttpStatus.OK.value()).body(userService.updateUserAccountState(id, accountState.getAccountState()));
    }

    @PatchMapping("/api/v1/users/updatePassword/{id}")
    public ResponseEntity<?> updatePasswordUser(@PathVariable int id, @RequestBody UpdatePasswordRequestDTO passwords) {
        userService.updateUserPassword(id, passwords.getOldPassword(), passwords.getNewPassword());
        return ResponseEntity.status(HttpStatus.OK.value()).body("Update password successfully!");
    }

}

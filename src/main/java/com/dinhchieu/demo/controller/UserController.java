package com.dinhchieu.demo.controller;

import com.dinhchieu.demo.dto.response.PaginationResponseDTO;
import com.dinhchieu.demo.dto.response.UserInformationResponseDTO;
import com.dinhchieu.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}

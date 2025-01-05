package com.dinhchieu.demo.controller;

import com.dinhchieu.demo.entity.LoginForm;
import com.dinhchieu.demo.service.Impl.UserDetailService;
import com.dinhchieu.demo.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class SecurityController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailService userDetailService;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/api/v1/login")
    public ResponseEntity<?> authAndGetToken(@RequestBody LoginForm loginForm){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginForm.username(), loginForm.password())
        );


        if(authentication.isAuthenticated()){
            Map<String, String> token = new HashMap<>();

            UserDetails userDetails = userDetailService.loadUserByUsername(loginForm.username());
            String accessToken = jwtUtils.generateToken(userDetails);
            String refreshToken = jwtUtils.generateRefreshToken(userDetails);
            String role = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining());

            token.put("username", loginForm.username());
            token.put("access_token", accessToken);
            token.put("refresh_token", refreshToken);
            token.put("role", role);


            return ResponseEntity.status(HttpStatus.OK.value()).body(token);
        }
        else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED.value()).body(Map.of("message", "Unauthorized"));
        }
    }

    @PostMapping("/api/v1/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestBody Map<String, String> token){
        String refreshToken = token.get("refresh_token");
        if(jwtUtils.isTokenValid(refreshToken)){


            String username = jwtUtils.extractUsername(refreshToken);
            UserDetails userDetails = userDetailService.loadUserByUsername(username);
            String newAccessToken = jwtUtils.generateToken(userDetails);
            String newRefreshToken = jwtUtils.generateRefreshToken(userDetails);

            Map<String, String> newToken = new HashMap<>();
            newToken.put("username", username);
            newToken.put("access_token", newAccessToken);
            newToken.put("refresh_token", newRefreshToken);
            newToken.put("role", userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining()));


            return ResponseEntity.status(HttpStatus.OK.value()).body(newToken);
        }
        else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(Map.of("message", "Invalid token"));
        }
    }

}

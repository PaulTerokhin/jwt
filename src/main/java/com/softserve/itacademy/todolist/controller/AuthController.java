package com.softserve.itacademy.todolist.controller;

import com.softserve.itacademy.todolist.dto.AuthResponse;
import com.softserve.itacademy.todolist.dto.LoginRequest;
import com.softserve.itacademy.todolist.security.JWTUtils;
import com.softserve.itacademy.todolist.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private AuthenticationManager authenticationManager;
    private JWTUtils jwtUtils;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, JWTUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken
                            (loginRequest.getUsername(), loginRequest.getPassword()));
            UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();

            String jwtToken = jwtUtils.generateTokenFromUsername(user.getUsername());
            AuthResponse authResponse = new AuthResponse(loginRequest.getUsername(), jwtToken);
            return ResponseEntity.ok().body(authResponse);
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }


}


package com.friday.fieldNotes.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.friday.fieldNotes.doa.model.AuthResponse;
import com.friday.fieldNotes.doa.model.LoginRequest;
import com.friday.fieldNotes.services.AuthenticationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationService authenticationService;

    @PostMapping
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
       UserDetails userDetails = authenticationService.authenticate(loginRequest.getEmail(), loginRequest.getPassword());
       String tokenValue = authenticationService.generateToken(userDetails);
       AuthResponse authResponse = AuthResponse.builder().token(tokenValue).expiresIn(86400).build();
       return ResponseEntity.ok(authResponse);
    }
}

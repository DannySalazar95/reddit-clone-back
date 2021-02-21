package com.dsalazar.reddit.controllers;

import com.dsalazar.reddit.dto.AuthenticationResponse;
import com.dsalazar.reddit.dto.LoginRequest;
import com.dsalazar.reddit.dto.RegisterRequest;
import com.dsalazar.reddit.services.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@Valid @RequestBody RegisterRequest registerRequest)
    {
        authService.signup(registerRequest);
        return ResponseEntity.status(HttpStatus.OK).body("User registration successful");
    }

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody LoginRequest loginRequest)
    {
        return authService.login(loginRequest);
    }

}

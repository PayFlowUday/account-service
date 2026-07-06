package com.payflow.account.controller;

import com.payflow.account.dto.request.LoginRequestDto;
import com.payflow.account.dto.request.ResetPasswordRequest;
import com.payflow.account.dto.response.LoginResponseDto;
import com.payflow.account.dto.request.RegisterRequest;
import com.payflow.account.dto.response.RegistrationResponseDto;
import com.payflow.account.service.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/registerUser")
    public ResponseEntity<RegistrationResponseDto> registerUser(@RequestBody @Valid RegisterRequest registrationRequest){

        return ResponseEntity.ok(authenticationService.registerUser(registrationRequest));

    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody @Valid LoginRequestDto loginRequest){
        return ResponseEntity.ok(authenticationService.getUser(loginRequest));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<Map<String, String>> forgotPassword(@RequestParam String email) {
        authenticationService.forgotPassword(email);
        return ResponseEntity.ok(Map.of("message", "Password reset token sent to " + email));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Map<String, String>> resetPassword(@RequestBody ResetPasswordRequest request) {
        authenticationService.resetPassword(request);
        return ResponseEntity.ok(Map.of("message", "Password changed successfully"));
    }



}

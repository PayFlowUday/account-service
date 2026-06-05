package com.payflow.account.controller;

import com.payflow.account.dto.request.RegisterRequest;
import com.payflow.account.dto.response.RegistrationResponseDto;
import com.payflow.account.service.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/v1/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping
    public ResponseEntity<RegistrationResponseDto> registerUser(@RequestBody @Valid RegisterRequest registrationRequest){

        return ResponseEntity.ok(authenticationService.registerUser(registrationRequest));

    }

}

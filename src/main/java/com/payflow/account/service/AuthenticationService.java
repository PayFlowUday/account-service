package com.payflow.account.service;

import com.payflow.account.dto.request.LoginRequestDto;
import com.payflow.account.dto.request.RegisterRequest;
import com.payflow.account.dto.response.LoginResponseDto;
import com.payflow.account.dto.response.RegistrationResponseDto;
import jakarta.validation.Valid;

public interface AuthenticationService {
    RegistrationResponseDto registerUser(RegisterRequest registrationRequest);

    LoginResponseDto getUser(@Valid LoginRequestDto loginRequest);
}

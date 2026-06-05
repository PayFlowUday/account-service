package com.payflow.account.service;

import com.payflow.account.dto.request.RegisterRequest;
import com.payflow.account.dto.response.RegistrationResponseDto;

public interface AuthenticationService {
    RegistrationResponseDto registerUser(RegisterRequest registrationRequest);

}

package com.payflow.account.service.impl;

import com.payflow.account.dto.request.RegisterRequest;
import com.payflow.account.dto.response.RegistrationResponseDto;
import com.payflow.account.repository.UserRepository;
import com.payflow.account.service.AuthenticationService;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;

    public AuthenticationServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public RegistrationResponseDto registerUser(RegisterRequest registrationRequest) {
        if(userRepository.existsByEmail(registrationRequest.getEmail())){
            throw new EmailAlreadyExistsException("Email already exist");
        }
    }
}

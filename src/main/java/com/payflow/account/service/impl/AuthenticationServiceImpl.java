package com.payflow.account.service.impl;

import com.payflow.account.dto.request.LoginRequestDto;
import com.payflow.account.dto.request.RegisterRequest;
import com.payflow.account.dto.response.LoginResponseDto;
import com.payflow.account.dto.response.RegistrationResponseDto;
import com.payflow.account.entity.User;
import com.payflow.account.exception.UserNotFoundException;
import com.payflow.account.exception.InvalidCredentialsException;
import com.payflow.account.mapper.UserMapper;
import com.payflow.account.repository.UserRepository;
import com.payflow.account.security.JwtService;
import com.payflow.account.service.AuthenticationService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final JwtService jwtService;

    public AuthenticationServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, UserMapper userMapper, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
        this.jwtService = jwtService;
    }

    @Override
    public RegistrationResponseDto registerUser(RegisterRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElse(new User());

        userMapper.updateEntity(request, user);

        user.setPassword(passwordEncoder.encode(request.getPassword()));

        user = userRepository.save(user);

        return userMapper.toRegisterResponse(user);
    }

    @Override
    public LoginResponseDto getUser(LoginRequestDto loginRequest) {
        User user=userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(()->new UserNotFoundException("Invalid email or password"));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid password");
        }

        String token = jwtService.generateToken(user.getEmail());

       return userMapper.toLoginResponse(user,token);

    }
}

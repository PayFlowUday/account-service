package com.payflow.account.service.impl;

import com.payflow.account.dto.request.LoginRequestDto;
import com.payflow.account.dto.request.RegisterRequest;
import com.payflow.account.dto.request.ResetPasswordRequest;
import com.payflow.account.dto.response.LoginResponseDto;
import com.payflow.account.dto.response.RegistrationResponseDto;
import com.payflow.account.entity.User;
import com.payflow.account.entity.Wallet;
import com.payflow.account.exception.AccountLockedException;
import com.payflow.account.exception.InvalidTokenException;
import com.payflow.account.exception.UserNotFoundException;
import com.payflow.account.exception.InvalidCredentialsException;
import com.payflow.account.mapper.UserMapper;
import com.payflow.account.repository.UserRepository;
import com.payflow.account.repository.WalletRepository;
import com.payflow.account.security.JwtService;
import com.payflow.account.service.AuthenticationService;
import com.payflow.account.service.EmailService;
import com.payflow.account.utils.enums.Currency;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final JwtService jwtService;
    private final WalletRepository walletRepository;
    private final EmailService emailService;

    @Value("${app.reset-token.expiry-minutes}")
    private int expiryMinutes;

    public AuthenticationServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, UserMapper userMapper, JwtService jwtService, WalletRepository walletRepository, EmailService emailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
        this.jwtService = jwtService;
        this.walletRepository = walletRepository;
        this.emailService = emailService;
    }

    @Override
    public RegistrationResponseDto registerUser(RegisterRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElse(new User());

        userMapper.updateEntity(request, user);

        user.setPassword(passwordEncoder.encode(request.getPassword()));

        user = userRepository.save(user);

        Wallet wallet = Wallet.builder()
                .user(user)
                .balance(BigDecimal.ZERO)
                .currency(Currency.INR)
                .isActive(true)
                .build();
        wallet = walletRepository.save(wallet);

        user.setWallet(wallet);

        return userMapper.toRegisterResponse(user);
    }

    @Transactional
    @Override
    public LoginResponseDto getUser(LoginRequestDto loginRequest) {

        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new UserNotFoundException("Invalid email or password"));

        // 1. check if account is locked
        if (user.isLocked()) {
            throw new AccountLockedException("Account is locked due to too many failed attempts. Contact support.");
        }

        // 2. wrong password
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {

            user.setFailedLoginAttempts(user.getFailedLoginAttempts() + 1);

            if (user.getFailedLoginAttempts() >= 3) {
                user.setLocked(true);
                user.setLockedAt(Instant.now());
            }

            userRepository.save(user);
            throw new InvalidCredentialsException("Invalid email or password");
        }

        // 3. successful login — reset failed attempts
        user.setFailedLoginAttempts(0);
        user.setLocked(false);
        user.setLockedAt(null);
        userRepository.save(user);

        String token = jwtService.generateToken(user.getEmail());
        return userMapper.toLoginResponse(user, token);
    }

    @Transactional
    @Override
    public void forgotPassword(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("No account found with email: " + email));

        if (user.isLocked()) {
            throw new AccountLockedException("Account is locked. Please contact support.");
        }

        // generate reset token
        String resetToken = UUID.randomUUID().toString();
        user.setResetToken(resetToken);
        user.setResetTokenExpiry(Instant.now().plusSeconds(expiryMinutes * 60L));
        userRepository.save(user);

        // send email
        emailService.sendPasswordResetEmail(email, resetToken);
    }

    @Override
    public void resetPassword(ResetPasswordRequest request) {
        User user = userRepository.findByResetToken(request.getToken())
                .orElseThrow(() -> new InvalidTokenException("Invalid reset token"));

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        user.setResetToken(null);
        user.setResetTokenExpiry(null);

        userRepository.save(user);

    }
}

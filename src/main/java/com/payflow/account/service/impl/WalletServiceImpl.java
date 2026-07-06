package com.payflow.account.service.impl;

import com.payflow.account.dto.response.WalletResponseDto;
import com.payflow.account.dto.response.WalletTransactionResponseDto;
import com.payflow.account.entity.Wallet;
import com.payflow.account.exception.InsufficientBalanceException;
import com.payflow.account.exception.WalletInactiveException;
import com.payflow.account.exception.WalletNotFoundException;
import com.payflow.account.mapper.WalletMapper;
import com.payflow.account.repository.WalletRepository;
import com.payflow.account.service.WalletService;
import com.payflow.account.utils.enums.TransactionType;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;
    private final WalletMapper walletMapper;


    public WalletServiceImpl(WalletRepository walletRepository, WalletMapper walletMapper) {
        this.walletRepository = walletRepository;
        this.walletMapper = walletMapper;
    }

    @Override
    public WalletResponseDto getBalance(Long userId) {

        Wallet wallet=walletRepository.findByUserId(userId).orElseThrow(()->new WalletNotFoundException("No Wallet Found for User "+ userId));
        return walletMapper.toDto(wallet);

    }

    @Transactional
    @Override
    public WalletTransactionResponseDto debitAmount(BigDecimal amount, Long userId) {
        Wallet wallet = walletRepository.findByUserIdWithLock(userId)
                .orElseThrow(() -> new WalletNotFoundException("Wallet not found for user: " + userId));

        if (!wallet.isActive()) {
            throw new WalletInactiveException("Wallet is inactive");
        }

        if(amount.compareTo(wallet.getBalance())>0){
            throw new InsufficientBalanceException("Insufficient balance. Available: " + wallet.getBalance());
        }

        wallet.setBalance(wallet.getBalance().subtract(amount));

        walletRepository.save(wallet);

        WalletTransactionResponseDto dto = walletMapper.toTransactionDto(wallet);
        dto.setAmount(amount);
        dto.setTransactionType(TransactionType.DEBIT);
        return dto;

    }

    @Transactional
    @Override
    public WalletTransactionResponseDto creditAmount(BigDecimal amount, Long userId) {
        Wallet wallet = walletRepository.findByUserIdWithLock(userId)
                .orElseThrow(() -> new WalletNotFoundException("Wallet not found for user: " + userId));

        if (!wallet.isActive()) {
            throw new WalletInactiveException("Wallet is inactive");
        }

        wallet.setBalance(wallet.getBalance().add(amount));

        walletRepository.save(wallet);

        WalletTransactionResponseDto dto = walletMapper.toTransactionDto(wallet);
        dto.setAmount(amount);
        dto.setTransactionType(TransactionType.CREDIT);
        return dto;
    }
}

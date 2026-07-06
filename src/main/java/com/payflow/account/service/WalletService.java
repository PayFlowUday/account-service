package com.payflow.account.service;

import com.payflow.account.dto.response.WalletResponseDto;
import com.payflow.account.dto.response.WalletTransactionResponseDto;

import java.math.BigDecimal;

public interface WalletService {
    WalletResponseDto getBalance(Long userId);

    WalletTransactionResponseDto debitAmount(BigDecimal amount, Long userId);

    WalletTransactionResponseDto creditAmount(BigDecimal amount, Long userId);
}

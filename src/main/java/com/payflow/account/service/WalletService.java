package com.payflow.account.service;

import com.payflow.account.dto.response.WalletResponseDto;

public interface WalletService {
    WalletResponseDto getBalance(Long userId);
}

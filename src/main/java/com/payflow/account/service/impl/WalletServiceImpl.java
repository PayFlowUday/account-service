package com.payflow.account.service.impl;

import com.payflow.account.dto.response.WalletResponseDto;
import com.payflow.account.entity.Wallet;
import com.payflow.account.exception.WalletNotFoundException;
import com.payflow.account.mapper.WalletMapper;
import com.payflow.account.repository.WalletRepository;
import com.payflow.account.service.WalletService;
import com.payflow.account.utils.EntityUtils;
import com.thoughtworks.xstream.converters.reflection.MissingFieldException;
import org.springframework.stereotype.Service;

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
}

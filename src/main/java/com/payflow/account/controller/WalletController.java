package com.payflow.account.controller;

import com.payflow.account.dto.request.WalletTransactionRequestDto;
import com.payflow.account.dto.response.WalletResponseDto;
import com.payflow.account.dto.response.WalletTransactionResponseDto;
import com.payflow.account.service.WalletService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/wallet/v1")
public class WalletController {

    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @GetMapping("/balance")
    public ResponseEntity<WalletResponseDto> getBalance(HttpServletRequest request){
        Long userId=(Long) request.getAttribute("userId");
        System.out.println("UserId = " + userId);
        return ResponseEntity.ok(walletService.getBalance(userId));
    }

    @PostMapping("/debit")
    public ResponseEntity<WalletTransactionResponseDto> debit(
            HttpServletRequest request,
            @Valid @RequestBody WalletTransactionRequestDto debitRequest) {
        Long userId = (Long) request.getAttribute("userId");
        return ResponseEntity.ok(walletService.debitAmount(debitRequest.getAmount(), userId));
    }

    @PostMapping("/credit")
    public ResponseEntity<WalletTransactionResponseDto> credit(
            HttpServletRequest request,
            @Valid @RequestBody WalletTransactionRequestDto creditRequest) {
        Long userId = (Long) request.getAttribute("userId");
        return ResponseEntity.ok(walletService.creditAmount(creditRequest.getAmount(), userId));
    }


}

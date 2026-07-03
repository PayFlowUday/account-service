package com.payflow.account.controller;

import com.payflow.account.dto.response.WalletResponseDto;
import com.payflow.account.service.WalletService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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


}

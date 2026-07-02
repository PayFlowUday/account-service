package com.payflow.account.dto.response;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
public class WalletResponseDto {

    private Long walletId;
    private BigDecimal balance;
    private String currency;
}
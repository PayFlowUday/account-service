package com.payflow.account.dto.response;

import com.payflow.account.utils.enums.Currency;
import com.payflow.account.utils.enums.TransactionType;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
public class WalletTransactionResponseDto {
    private Long walletId;
    private BigDecimal amount;
    private BigDecimal balance;
    private Currency currency;
    private TransactionType transactionType;
}
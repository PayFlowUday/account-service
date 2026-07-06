package com.payflow.account.exception;

public class WalletInactiveException extends RuntimeException {

    public WalletInactiveException(String message) {
        super(message);
    }
}
package com.payflow.account.exception;

public class WalletNotFoundException  extends RuntimeException{
    public WalletNotFoundException(String message){
        super(message);
    }
}

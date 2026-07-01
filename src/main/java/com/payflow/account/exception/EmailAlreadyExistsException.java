package com.payflow.account.exception;

public class EmailAlreadyExistsException extends RuntimeException{
 public EmailAlreadyExistsException(String message){
     super(message);
 }
}

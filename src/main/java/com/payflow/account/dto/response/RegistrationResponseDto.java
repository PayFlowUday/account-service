package com.payflow.account.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrationResponseDto {

    private String message;
    private long userId;
    private String fullName;
    private String email;

}

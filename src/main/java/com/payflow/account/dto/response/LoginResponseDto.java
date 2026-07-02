package com.payflow.account.dto.response;

import com.payflow.account.utils.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDto {

    private String email;
    private String fullName;
    private Role role;
    private String token;
    private String UserId;

}

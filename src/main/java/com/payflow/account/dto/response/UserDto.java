package com.payflow.account.dto.response;

import com.payflow.account.utils.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private Long userId;
    private String fullName;
    private String email;
    private Role role;
    private boolean isActive;
}
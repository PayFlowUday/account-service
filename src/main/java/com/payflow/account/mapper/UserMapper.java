package com.payflow.account.mapper;

import com.payflow.account.dto.request.RegisterRequest;
import com.payflow.account.dto.response.LoginResponseDto;
import com.payflow.account.dto.response.RegistrationResponseDto;
import com.payflow.account.dto.response.UserDto;
import com.payflow.account.entity.User;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(
        componentModel = "spring",
        builder = @Builder(disableBuilder = true), uses = {WalletMapper.class}
)
public interface UserMapper {

    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "wallet", ignore = true)
    @Mapping(target = "uuid", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    User toEntity(RegisterRequest request);

    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "wallet", ignore = true)
    @Mapping(target = "uuid", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntity(RegisterRequest request, @MappingTarget User user);

    @Mapping(target = "message", constant = "User registered successfully")
    @Mapping(target = "wallet", source = "wallet")
    RegistrationResponseDto toRegisterResponse(User user);

    UserDto toDto(User user);

    @Mapping(target = "token", source = "token")
    LoginResponseDto toLoginResponse(User user, String token);
}
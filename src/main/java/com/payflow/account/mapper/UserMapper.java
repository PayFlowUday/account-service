package com.payflow.account.mapper;

import com.payflow.account.dto.request.RegisterRequest;
import com.payflow.account.dto.response.RegistrationResponseDto;
import com.payflow.account.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "wallet", ignore = true)
    @Mapping(target = "uuid", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    User toEntity(RegisterRequest request);

    @Mapping(target = "message", constant = "User registered successfully")
    RegistrationResponseDto toRegisterResponse(User user);
}
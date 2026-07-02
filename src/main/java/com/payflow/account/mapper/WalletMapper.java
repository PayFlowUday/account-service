package com.payflow.account.mapper;

import com.payflow.account.dto.response.WalletResponseDto;
import com.payflow.account.entity.Wallet;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring",
        builder = @Builder(disableBuilder = true)
)
public interface WalletMapper {

    // Entity → DTO
    WalletResponseDto toDto(Wallet wallet);

    // DTO → Entity (useful when creating wallet from request in future)
    @Mapping(target = "walletId", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "uuid", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Wallet toEntity(WalletResponseDto walletResponseDto);
}
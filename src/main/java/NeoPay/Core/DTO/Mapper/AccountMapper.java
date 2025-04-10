package NeoPay.Core.DTO.Mapper;

import NeoPay.Core.DTO.Request.AccountRequest;
import NeoPay.Core.DTO.Response.AccountResponse;
import NeoPay.Core.Models.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    // Map DTO -> Entity (Request)
    Account toEntity(AccountRequest dto);
    // Map Entity -> DTO (Response)
//    @Mapping(source = "user.fullName", target = "userName")
//    @Mapping(source = "user.id", target = "userId")
    AccountResponse toDTO(Account account);
}

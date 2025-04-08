package NeoPay.Core.DTO.Mapper;

import NeoPay.Core.DTO.Request.TransactionRequest;
import NeoPay.Core.DTO.Response.TransactionResponse;
import NeoPay.Core.Models.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TransactionMapper {
    // Map DTO -> Entity (Request)
    Transaction toEntity(TransactionRequest dto);
    // Map Entity -> DTO (Response)
    @Mapping(source = "sender.accountNumber", target = "sender")
    @Mapping(source = "receiver.accountNumber", target = "receiver")
    @Mapping(source = "reference", target = "reference")
    TransactionResponse toDTO(Transaction transaction);
}

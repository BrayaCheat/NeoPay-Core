package NeoPay.Core.DTO.Mapper;

import NeoPay.Core.DTO.Request.RegisterRequest;
import NeoPay.Core.DTO.Response.UserResponse;
import NeoPay.Core.Models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    // Map DTO -> Entity (Request)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    User toEntity(RegisterRequest dto);

    // Map Entity -> DTO (Response)
    UserResponse toDTO(User user);

}
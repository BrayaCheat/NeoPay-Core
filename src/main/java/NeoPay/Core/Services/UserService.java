package NeoPay.Core.Services;

import NeoPay.Core.DTO.Response.UserResponse;
import NeoPay.Core.Models.User;

import java.math.BigDecimal;
import java.util.List;

public interface UserService {
    //General
    UserResponse getSelf();

    //Admin
    List<UserResponse> findAllUsers();
}

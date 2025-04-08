package NeoPay.Core.Services;

import NeoPay.Core.DTO.Response.UserResponse;
import NeoPay.Core.Models.User;

import java.util.List;

public interface UserService {
    //General
    UserResponse getSelf();

    //Admin
    List<User> findAllUsers();
}

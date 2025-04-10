package NeoPay.Core.Controllers;

import NeoPay.Core.DTO.Response.UserResponse;
import NeoPay.Core.Services.ServiceImpl.UserServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserServiceImpl userService;

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getSelf(){
        return ResponseEntity.status(200).body(userService.getSelf());
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> findAllUsers(){
        return ResponseEntity.status(200).body(userService.findAllUsers());
    }
}

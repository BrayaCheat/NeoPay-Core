package NeoPay.Core.Controllers;

import NeoPay.Core.DTO.Request.LoginRequest;
import NeoPay.Core.DTO.Request.RegisterRequest;
import NeoPay.Core.DTO.Response.LoginResponse;
import NeoPay.Core.DTO.Response.UserResponse;
import NeoPay.Core.Models.User;
import NeoPay.Core.Services.AuthenticationService;
import NeoPay.Core.Services.JwtService;
import org.apache.kafka.common.security.auth.Login;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {
    private final JwtService jwtService;

    private final AuthenticationService authenticationService;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody RegisterRequest registerUserDto) {
        return ResponseEntity.status(201).body(authenticationService.signup(registerUserDto));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginUserDto) {
        User authenticatedUser = authenticationService.login(loginUserDto);
        String jwtToken = jwtService.generateToken(authenticatedUser);
        LoginResponse loginResponse = LoginResponse.builder()
                .token(jwtToken)
                .expiresIn(jwtService.getExpirationTime())
                .build();
        return ResponseEntity.status(201).body(loginResponse);
    }
}

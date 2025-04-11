package NeoPay.Core.Services;

import NeoPay.Core.DTO.Request.LoginRequest;
import NeoPay.Core.DTO.Request.RegisterRequest;
import NeoPay.Core.Models.Role;
import NeoPay.Core.Models.User;
import NeoPay.Core.Repositories.RoleRepository;
import NeoPay.Core.Repositories.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final RoleRepository roleRepository;


    public AuthenticationService(
            UserRepository userRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder,
            RoleRepository roleRepository
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    public User signup(RegisterRequest dto) {
        Role userRole = roleRepository.findByRole("USER").orElseGet(() -> roleRepository.save(Role.builder().role("USER").build()));
        Set<Role> role = new HashSet<>();
        role.add(userRole);
        User user = User.builder()
                .fullName(dto.getFullName())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .isActive(true)
                .isDeleted(false)
                .isLocked(false)
                .lastLogin(LocalDateTime.now())
                .role(role)
                .build();
        return userRepository.save(user);
    }

    public User login(LoginRequest dto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        dto.getEmail(),
                        dto.getPassword()
                )
        );

        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow();

        user.setLastLogin(LocalDateTime.now());
        userRepository.save(user);

        return user;
    }

    public void logout(){

    }

}

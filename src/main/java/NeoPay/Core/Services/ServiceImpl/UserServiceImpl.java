package NeoPay.Core.Services.ServiceImpl;

import NeoPay.Core.DTO.Mapper.UserMapper;
import NeoPay.Core.DTO.Response.UserResponse;
import NeoPay.Core.Models.User;
import NeoPay.Core.Repositories.UserRepository;
import NeoPay.Core.Services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserResponse getSelf() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        return userMapper.toDTO(currentUser);
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }
}

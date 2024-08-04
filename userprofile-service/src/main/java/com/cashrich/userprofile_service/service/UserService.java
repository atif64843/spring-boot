package com.cashrich.userprofile_service.service;

import com.cashrich.userprofile_service.entity.UserEntity;
import com.cashrich.userprofile_service.model.UserRequest;
import com.cashrich.userprofile_service.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CoinMarketCapService coinMarketCapService;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, CoinMarketCapService coinMarketCapService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.coinMarketCapService = coinMarketCapService;
    }

    public UserRequest registerUser(@Valid UserRequest userRequest) {
        if (userRepository.findByUsername(userRequest.getUsername()).isPresent()) {
            throw new RuntimeException("Username is already taken");
        }
        if (userRepository.findByEmail(userRequest.getEmail()).isPresent()) {
            throw new RuntimeException("Email is already in use");
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(userRequest.getUsername());
        userEntity.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        userEntity.setFirstName(userRequest.getFirstName());
        userEntity.setLastName(userRequest.getLastName());
        userEntity.setEmail(userRequest.getEmail());
        userEntity.setMobile(userRequest.getMobile());

        UserEntity savedUser = userRepository.save(userEntity);
        return mapToUserModel(savedUser);
    }

    public UserRequest updateUser(Long userId, @Valid UserRequest updatedUserRequest) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        userEntity.setFirstName(updatedUserRequest.getFirstName());
        userEntity.setLastName(updatedUserRequest.getLastName());
        userEntity.setMobile(updatedUserRequest.getMobile());
        if (updatedUserRequest.getPassword() != null && !updatedUserRequest.getPassword().isEmpty()) {
            userEntity.setPassword(passwordEncoder.encode(updatedUserRequest.getPassword()));
        }

        UserEntity updatedUser = userRepository.save(userEntity);
        return mapToUserModel(updatedUser);
    }

    public UserRequest getUserByUsername(String username) {
        UserEntity userEntity = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return mapToUserModel(userEntity);
    }

    public String fetchCoinData(Long userId) {
        return coinMarketCapService.fetchCoinData(userId);
    }

    private UserRequest mapToUserModel(UserEntity userEntity) {
        UserRequest userRequest = new UserRequest();
        userRequest.setUsername(userEntity.getUsername());
        userRequest.setFirstName(userEntity.getFirstName());
        userRequest.setLastName(userEntity.getLastName());
        userRequest.setEmail(userEntity.getEmail());
        userRequest.setMobile(userEntity.getMobile());
        return userRequest;
    }
}

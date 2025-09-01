package com.farmdev.userservice.service;

import com.farmdev.userservice.dto.CreateUserRequest;
import com.farmdev.userservice.dto.LoginRequest;
import com.farmdev.userservice.dto.UserResponse;
import com.farmdev.userservice.model.User;
import com.farmdev.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    
    public Optional<UserResponse> getUserById(Long id) {
        return userRepository.findById(id)
                .map(this::convertToResponse);
    }
    
    public Optional<UserResponse> getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(this::convertToResponse);
    }
    
    public UserResponse createUser(CreateUserRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists: " + request.getUsername());
        }
        
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists: " + request.getEmail());
        }
        
        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .build();
        
        User savedUser = userRepository.save(user);
        return convertToResponse(savedUser);
    }
    
    public Optional<UserResponse> updateUser(Long id, CreateUserRequest request) {
        return userRepository.findById(id)
                .map(existingUser -> {
                    if (!existingUser.getUsername().equals(request.getUsername()) && 
                        userRepository.existsByUsername(request.getUsername())) {
                        throw new RuntimeException("Username already exists: " + request.getUsername());
                    }
                    
                    if (!existingUser.getEmail().equals(request.getEmail()) && 
                        userRepository.existsByEmail(request.getEmail())) {
                        throw new RuntimeException("Email already exists: " + request.getEmail());
                    }
                    
                    existingUser.setUsername(request.getUsername());
                    existingUser.setEmail(request.getEmail());
                    existingUser.setFirstName(request.getFirstName());
                    existingUser.setLastName(request.getLastName());
                    
                    if (request.getPassword() != null && !request.getPassword().trim().isEmpty()) {
                        existingUser.setPassword(passwordEncoder.encode(request.getPassword()));
                    }
                    
                    return convertToResponse(userRepository.save(existingUser));
                });
    }
    
    public boolean deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    public Optional<UserResponse> login(LoginRequest loginRequest) {
        Optional<User> userOpt = userRepository.findByUsernameOrEmail(
            loginRequest.getUsernameOrEmail(), 
            loginRequest.getUsernameOrEmail()
        );
        
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
                return Optional.of(convertToResponse(user));
            }
        }
        
        return Optional.empty();
    }
    
    private UserResponse convertToResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .createdAt(user.getCreatedAt())
                .build();
    }
} 
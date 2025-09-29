package com.example.ecommerce.service.impl;

import com.example.ecommerce.dto.UserDto;
import com.example.ecommerce.dto.UserRegistrationDto;
import com.example.ecommerce.dto.UserUpdateDto;
import com.example.ecommerce.entity.User;
import com.example.ecommerce.repository.UserRepository;
import com.example.ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserDto createUser(UserRegistrationDto userDto) {
        User user = User.builder()
                .username(userDto.getUsername())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .email(userDto.getEmail())
                .roles(userDto.getRoles())
                .build();

        User saved = userRepository.save(user);

        UserDto userDtoResponse = UserDto.builder()
                .id(saved.getId())
                .username(saved.getUsername())
                .email(saved.getEmail())
                .roles(saved.getRoles())
                .build();

        return userDtoResponse;
    }

    @Override
    public UserDto getUserById(Long id) {
        return userRepository.findById(id)
                .map(user -> UserDto.builder()
                        .id(user.getId())
                        .username(user.getUsername())
                        .email(user.getEmail())
                        .roles(user.getRoles())
                        .build())
                .orElse(null);
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> UserDto.builder()
                        .id(user.getId())
                        .username(user.getUsername())
                        .email(user.getEmail())
                        .roles(user.getRoles())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public UserDto updateUser(Long id, UserUpdateDto userUpdateDto) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new com.example.ecommerce.exception.EntityNotFoundException("User not found with id: " + id));
        if (userUpdateDto.getUsername() != null) {
            user.setUsername(userUpdateDto.getUsername());
        }
        if (userUpdateDto.getEmail() != null) {
            user.setEmail(userUpdateDto.getEmail());
        }
        if (userUpdateDto.getRoles() != null && !userUpdateDto.getRoles().isEmpty()) {
            user.setRoles(userUpdateDto.getRoles());
        }
        User updated = userRepository.save(user);
        return UserDto.builder()
                .id(updated.getId())
                .username(updated.getUsername())
                .email(updated.getEmail())
                .roles(updated.getRoles())
                .build();
    }

    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new com.example.ecommerce.exception.EntityNotFoundException("User not found with id: " + id));
        userRepository.deleteById(user.getId());
    }
}

package com.example.ecommerce.service;

import com.example.ecommerce.dto.UserDto;
import com.example.ecommerce.dto.UserRegistrationDto;
import com.example.ecommerce.dto.UserUpdateDto;

import java.util.List;

public interface UserService {
    UserDto createUser(UserRegistrationDto userDto);
    UserDto getUserById(Long id);
    List<UserDto> getAllUsers();
    UserDto updateUser(Long id, UserUpdateDto userDto);
    void deleteUser(Long id);
}

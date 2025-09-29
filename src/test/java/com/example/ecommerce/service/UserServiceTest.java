package com.example.ecommerce.service;

import com.example.ecommerce.dto.UserDto;
import com.example.ecommerce.dto.UserRegistrationDto;
import com.example.ecommerce.entity.User;
import com.example.ecommerce.entity.UserRole;
import com.example.ecommerce.exception.EntityNotFoundException;
import com.example.ecommerce.repository.UserRepository;
import com.example.ecommerce.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void testFindUserById() {
        User user = User.builder()
                .id(1L)
                .username("serviceuser")
                .password("servicepass")
                .email("service@testing.com")
                .build();

        when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(user));

        UserDto result = userService.getUserById(1L);

        assertNotNull(result);
        assertEquals("serviceuser", result.getUsername());
        assertEquals("servicepass", user.getPassword());
        assertEquals("service@testing.com", result.getEmail());

        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void testCreateUser() {
        // create user registration DTO
        UserRegistrationDto userDto = UserRegistrationDto.builder()
                .username("newuser")
                .password("newuserpass@123")
                .email("user@testing.com")
                .roles(new HashSet<>(Set.of(UserRole.CUSTOMER)))
                .build();

        // Stub passwordEncoder.encode
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

        User savedUser = User.builder()
                .id(2L)
                .username(userDto.getUsername())
                .password("encodedPassword")
                .email(userDto.getEmail())
                .roles(userDto.getRoles())
                .build();

        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        UserDto createdUser = userService.createUser(userDto);

        assertNotNull(createdUser);
        assertEquals("newuser", createdUser.getUsername());
        assertEquals("encodedPassword", savedUser.getPassword());
        assertEquals("user@testing.com", createdUser.getEmail());
        assertTrue(createdUser.getRoles().contains(UserRole.CUSTOMER));
    }

    @Test
    void testGetAllUsers() {
        User user1 = User.builder().id(1L).username("user1").email("user1@testing.com").build();
        User user2 = User.builder().id(2L).username("user2").email("user2@testing.com").build();
        when(userRepository.findAll()).thenReturn(java.util.List.of(user1, user2));

        java.util.List<UserDto> users = userService.getAllUsers();

        assertEquals(2, users.size());
        assertEquals("user1", users.get(0).getUsername());
        assertEquals("user2", users.get(1).getUsername());
    }

    @Test
    void testUpdateUser_Valid() {
        User existingUser = User.builder().id(1L).username("olduser").email("old@testing.com").build();
        when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(existingUser));

        // Assuming UserUpdateDto exists and has builder
        com.example.ecommerce.dto.UserUpdateDto updateDto = com.example.ecommerce.dto.UserUpdateDto.builder().email("new@testing.com").build();
        User updatedUser = User.builder().id(1L).username("olduser").email("new@testing.com").build();
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        UserDto result = userService.updateUser(1L, updateDto);

        assertEquals("new@testing.com", result.getEmail());
    }

    @Test
    void testUpdateUser_NonExistent() {
        when(userRepository.findById(99L)).thenReturn(java.util.Optional.empty());
        com.example.ecommerce.dto.UserUpdateDto updateDto = com.example.ecommerce.dto.UserUpdateDto.builder().email("new@testing.com").build();

        assertThrows(EntityNotFoundException.class, () -> userService.updateUser(99L, updateDto));
    }

    @Test
    void testUpdateUser_NoFields() {
        User existingUser = User.builder().id(1L).username("olduser").email("old@testing.com").build();
        when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(existingUser));
        com.example.ecommerce.dto.UserUpdateDto updateDto = com.example.ecommerce.dto.UserUpdateDto.builder().build(); // No fields set

        // Assuming validation throws exception for no fields
        assertThrows(RuntimeException.class, () -> userService.updateUser(1L, updateDto));
    }

    @Test
    void testDeleteUser_Existing() {
        User user = User.builder().id(1L).username("todelete").build();
        when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(user));
//        doNothing().when(userRepository).deleteById(1L);

        assertDoesNotThrow(() -> userService.deleteUser(1L));
        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteUser_NonExistent() {
        when(userRepository.findById(99L)).thenReturn(java.util.Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.deleteUser(99L));
    }
}

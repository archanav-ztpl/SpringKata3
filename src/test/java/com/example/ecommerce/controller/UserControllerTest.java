package com.example.ecommerce.controller;

import com.example.ecommerce.service.UserService;
import com.example.ecommerce.dto.UserDto;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import java.util.List;

@WebMvcTest(UserController.class) // Loads only web layer
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;

    @Test
    @WithMockUser // Simulate authenticated user to avoid 401
    void testGetUserById() throws Exception {
        // Arrange: mock the service response
        UserDto userDto = UserDto.builder()
                .id(1L)
                .username("testuser")
                .email("testuser@example.com")
                .build();

        when(userService.getUserById(1L)).thenReturn(userDto);

        // Act & Assert: perform GET and check response
        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.username").value("testuser"))
                .andExpect(jsonPath("$.email").value("testuser@example.com"));
    }

    @Test
    @WithMockUser
    void testGetAllUsers() throws Exception {
        List<UserDto> users = List.of(
                UserDto.builder().id(1L).username("user1").email("user1@example.com").build(),
                UserDto.builder().id(2L).username("user2").email("user2@example.com").build()
        );
        when(userService.getAllUsers()).thenReturn(users);
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].username").value("user1"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].username").value("user2"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testCreateUser() throws Exception {
        UserDto userDto = UserDto.builder().id(1L).username("newuser").email("newuser@example.com").build();
        when(userService.createUser(any())).thenReturn(userDto);
        String requestBody = "{" +
                "\"username\": \"newuser\"," +
                "\"password\": \"Password@123\"," +
                "\"email\": \"newuser@example.com\"}";
        mockMvc.perform(post("/api/users")
                .with(csrf())
                .contentType("application/json")
                .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.username").value("newuser"))
                .andExpect(jsonPath("$.email").value("newuser@example.com"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testUpdateUser() throws Exception {
        UserDto userDto = UserDto.builder().id(1L).username("updateduser").email("updated@example.com").build();
        when(userService.updateUser(eq(1L), any())).thenReturn(userDto);
        String requestBody = "{" +
                "\"username\": \"updateduser\"," +
                "\"email\": \"updated@example.com\"}";
        mockMvc.perform(patch("/api/users/1")
                .with(csrf())
                .contentType("application/json")
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.username").value("updateduser"))
                .andExpect(jsonPath("$.email").value("updated@example.com"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testDeleteUser() throws Exception {
        doNothing().when(userService).deleteUser(1L);
        mockMvc.perform(delete("/api/users/1")
                .with(csrf()))
                .andExpect(status().isNoContent());
    }
    // Add more tests for other endpoints
}

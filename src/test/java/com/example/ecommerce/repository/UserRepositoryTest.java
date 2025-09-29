package com.example.ecommerce.repository;

import com.example.ecommerce.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest // Loads only JPA components, uses H2 in-memory DB
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    void testSaveAndFindUser() {
        // create user
        User user = User.builder()
                .username("testuser")
                .password("password")
                .email("test.user@testing.com")
                .build();
        // save user
        User savedUser = userRepository.save(user);
        assertNotNull(savedUser.getId());
        // find user
        User foundUser = userRepository.findById(savedUser.getId()).orElse(null);
        assertNotNull(foundUser);
        assertEquals("testuser", foundUser.getUsername());
        assertEquals("password", foundUser.getPassword());
        assertEquals("test.user@testing.com", foundUser.getEmail());

    }

    @Test
    void testFindAllUsers() {
        // create users
        User user1 = User.builder().username("user1").password("pass1").email("email1@testing.com").build();
        User user2 = User.builder().username("user2").password("pass2").email("email2@testing.com").build();

        // save users
        userRepository.save(user1);
        userRepository.save(user2);

        // find all users
        List<User> users = userRepository.findAll();
        assertEquals(2, users.size());

    }

    @Test
    void testUpdateUser() {
        // create user
        User user = User.builder()
                .username("updateuser")
                .password("oldpass")
                .email("update@testing.com")
                .build();

        // save user
        User savedUser = userRepository.save(user);

        // update user
        savedUser.setPassword("newpass");
        User updatedUser = userRepository.save(savedUser);

        // find user
        User foundUser = userRepository.findById(updatedUser.getId()).orElse(null);
        assertNotNull(foundUser);
        assertEquals("newpass", foundUser.getPassword());
    }

    @Test
    void testDeleteUser() {
        // create user
        User user = User.builder()
                .username("deleteuser")
                .password("tobedeleted")
                .email("delete@testing.com")
                .build();

        // save user
        User savedUser = userRepository.save(user);
        Long userId = savedUser.getId();
        assertNotNull(userId);

        // delete user
        userRepository.deleteById(userId);

        // verify deletion
        User foundUser = userRepository.findById(userId).orElse(null);
        assertNull(foundUser);
    }

}


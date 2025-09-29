package com.example.ecommerce.entity;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

// Unit tests for User entity
class UserEntityTest {
    // Example: Test default constructor and setters/getters
    @Test
    void testUserEntityFields() {
        User user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        user.setPassword("password");
        user.setEmail("test@example.com");
        user.setRoles(Set.of(UserRole.ADMIN, UserRole.CUSTOMER));

        assertEquals(1L, user.getId());
        assertEquals("testuser", user.getUsername());
        assertEquals("password", user.getPassword());
        assertEquals("test@example.com", user.getEmail());
        assertTrue(user.getRoles().contains(UserRole.ADMIN));
        assertTrue(user.getRoles().contains(UserRole.CUSTOMER));
    }

    @Test
    void testUserBuilder() {
        User user = User.builder()
                .id(2L)
                .username("builderuser")
                .password("builderpass")
                .email("builder@example.com")
                .roles(Set.of(UserRole.ADMIN))
                .build();
        assertEquals(2L, user.getId());
        assertEquals("builderuser", user.getUsername());
        assertEquals("builderpass", user.getPassword());
        assertEquals("builder@example.com", user.getEmail());
        assertEquals(Set.of(UserRole.ADMIN), user.getRoles());
    }

    @Test
    void testEqualsAndHashCode() {
        User user1 = User.builder().id(3L).username("user1").password("pass").email("a@b.com").roles(Set.of(UserRole.ADMIN)).build();
        User user2 = User.builder().id(3L).username("user1").password("pass").email("a@b.com").roles(Set.of(UserRole.ADMIN)).build();
        User user3 = User.builder().id(4L).username("user2").password("pass2").email("c@d.com").roles(Set.of(UserRole.CUSTOMER)).build();
        assertEquals(user1, user2); // same fields
        assertEquals(user1.hashCode(), user2.hashCode());
        assertNotEquals(user1, user3); // different fields
    }

    @Test
    void testToString() {
        User user = User.builder().id(5L).username("tostring").password("pass").email("to@str.com").roles(Set.of(UserRole.SELLER)).build();
        String str = user.toString();
        assertTrue(str.contains("tostring"));
        assertTrue(str.contains("SELLER"));
    }

    @Test
    void testNoArgsConstructor() {
        User user = new User();
        assertNull(user.getId());
        assertNull(user.getUsername());
        assertNull(user.getPassword());
        assertNull(user.getEmail());
        assertNull(user.getRoles());
    }

    @Test
    void testAllArgsConstructor() {
        User user = new User(6L, "allargs", "pass", "all@args.com", Set.of(UserRole.SUPPORT), null);
        assertEquals(6L, user.getId());
        assertEquals("allargs", user.getUsername());
        assertEquals("pass", user.getPassword());
        assertEquals("all@args.com", user.getEmail());
        assertEquals(Set.of(UserRole.SUPPORT), user.getRoles());
    }

    @Test
    void testRoleSetIntegrity() {
        User user = User.builder().roles(new HashSet<>(Set.of(UserRole.ADMIN, UserRole.CUSTOMER))).build();
        assertEquals(2, user.getRoles().size()); // Set should not allow duplicates
        assertTrue(user.getRoles().contains(UserRole.ADMIN));
        assertTrue(user.getRoles().contains(UserRole.CUSTOMER));

        // Try to add a duplicate role after creation
        user.getRoles().add(UserRole.ADMIN); // Set will not add duplicate
        assertEquals(2, user.getRoles().size());
    }

    @Test
    void testNullOrEmptyRoles() {
        User user = User.builder().roles(null).build();
        assertNull(user.getRoles());
        user.setRoles(Set.of());
        assertTrue(user.getRoles().isEmpty());
    }
}
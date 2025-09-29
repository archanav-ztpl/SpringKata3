package com.example.ecommerce.repository;

import com.example.ecommerce.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest // Loads only JPA components, uses H2 in-memory DB
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    void testSaveAndFindUser() {
        // TODO: Save a User, retrieve it, assert correctness
    }
    // Add more tests for custom queries
}


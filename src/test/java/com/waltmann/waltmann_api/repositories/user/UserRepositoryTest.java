package com.waltmann.waltmann_api.repositories.user;

import com.waltmann.waltmann_api.domain.user.User;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@ActiveProfiles("test")
public class UserRepositoryTest {
        @Autowired
        EntityManager entityManager;

    @Autowired
    UserRepository repository;

    @Test
    @DisplayName("Should create and get user by id")
    void getUserById() {
        User user = new User();

        user.setEmail("example@test.com");
        user.setName("example");
        user.setPassword("example");

        repository.save(user);

        Optional<User> userOptional = repository.findById(user.getId());
        assertTrue(userOptional.isPresent());
        assertEquals(user.getName(), userOptional.get().getName());
        assertEquals(user.getEmail(), userOptional.get().getEmail());
        assertEquals(user.getPassword(), userOptional.get().getPassword());
    }

    @Test
    @DisplayName("Should create and get user by email")
    void getUserByEmail() {
        User user = new User();

        user.setEmail("example@test.com");
        user.setName("example");
        user.setPassword("example");

        repository.save(user);

        Optional<User> userOptional = repository.findByEmail(user.getEmail());
        assertTrue(userOptional.isPresent());
        assertEquals(user.getName(), userOptional.get().getName());
        assertEquals(user.getEmail(), userOptional.get().getEmail());
        assertEquals(user.getPassword(), userOptional.get().getPassword());
    }
}

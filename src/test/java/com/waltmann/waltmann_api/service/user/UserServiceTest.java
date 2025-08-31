package com.waltmann.waltmann_api.service.user;

import com.waltmann.waltmann_api.domain.user.User;
import com.waltmann.waltmann_api.exceptions.NotFoundException;
import com.waltmann.waltmann_api.repositories.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {
    @Mock
    private UserRepository repository;

    @InjectMocks
    private UserDetailsServiceImpl service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should load user by email when user exists")
    void loadUserByUsernameSuccess() {
        String email = "john.doe@example.com";
        String password = "$2a$10$hashedPasswordExample";
        String name = "John Doe";

        User user = createUser(name, email, password);

        when(repository.findByEmail(email)).thenReturn(Optional.of(user));

        UserDetails result = service.loadUserByUsername(email);

        assertNotNull(result);
        assertEquals(email, result.getUsername());
        assertEquals(password, result.getPassword());
        assertTrue(result.isEnabled());
        assertTrue(result.isAccountNonExpired());
        assertTrue(result.isAccountNonLocked());
        assertTrue(result.isCredentialsNonExpired());
        verify(repository).findByEmail(email);
    }

    @Test
    @DisplayName("Should throw NotFoundException when user does not exist")
    void loadUserByUsernameNotFound() {
        String email = "nonexistent@example.com";

        when(repository.findByEmail(email)).thenReturn(Optional.empty());

        NotFoundException result = assertThrows(
                NotFoundException.class,
                () -> service.loadUserByUsername(email)
        );

        assertEquals(String.format("User does not exist, email: %s", email), result.getMessage());
        verify(repository).findByEmail(email);
    }

    @Test
    @DisplayName("Should create UserDetails with correct username and password mapping")
    void loadUserByUsernameCorrectMapping() {
        String email = "test@example.com";
        String hashedPassword = "$2a$10$testHashedPassword";
        String name = "Test User";

        User user = createUser(name, email, hashedPassword);

        when(repository.findByEmail(email)).thenReturn(Optional.of(user));

        UserDetails result = service.loadUserByUsername(email);

        // Verify that email is mapped to username in UserDetails
        assertEquals(user.getEmail(), result.getUsername());
        assertEquals(user.getPassword(), result.getPassword());
        verify(repository).findByEmail(email);
    }

    @Test
    @DisplayName("Should handle empty email gracefully")
    void loadUserByUsernameEmptyEmail() {
        String email = "";

        when(repository.findByEmail(email)).thenReturn(Optional.empty());

        NotFoundException result = assertThrows(
                NotFoundException.class,
                () -> service.loadUserByUsername(email)
        );

        assertEquals(String.format("User does not exist, email: %s", email), result.getMessage());
        verify(repository).findByEmail(email);
    }

    @Test
    @DisplayName("Should handle null email gracefully")
    void loadUserByUsernameNullEmail() {
        String email = null;

        when(repository.findByEmail(email)).thenReturn(Optional.empty());

        NotFoundException result = assertThrows(
                NotFoundException.class,
                () -> service.loadUserByUsername(email)
        );

        assertEquals(String.format("User does not exist, email: %s", email), result.getMessage());
        verify(repository).findByEmail(email);
    }

    private User createUser(String name, String email, String password) {
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        return user;
    }
}

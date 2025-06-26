package com.waltmann.waltmann_api.repositories.user;

import com.waltmann.waltmann_api.domain.user.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(@Email(message = "Invalid email format") @NotBlank(message = "Email cannot be blank") String email);
}

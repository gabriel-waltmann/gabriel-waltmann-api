package com.waltmann.waltmann_api.repositories.publics;

import com.waltmann.waltmann_api.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PublicUserRepository extends JpaRepository<User, UUID> {
}

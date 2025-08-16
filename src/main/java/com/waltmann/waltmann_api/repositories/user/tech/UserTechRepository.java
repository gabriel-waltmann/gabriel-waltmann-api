package com.waltmann.waltmann_api.repositories.user.tech;

import com.waltmann.waltmann_api.domain.user.tech.UserTech;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface UserTechRepository extends JpaRepository<UserTech, UUID> {
    List<UserTech> findByUserId(UUID userId);
}

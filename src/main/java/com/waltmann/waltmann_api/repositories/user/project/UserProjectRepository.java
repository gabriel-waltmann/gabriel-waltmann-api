package com.waltmann.waltmann_api.repositories.user.project;

import com.waltmann.waltmann_api.domain.user.project.UserProject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface UserProjectRepository extends JpaRepository<UserProject, UUID> {
    List<UserProject> findByUserId(UUID userId);
}

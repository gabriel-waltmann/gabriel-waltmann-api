package com.waltmann.waltmann_api.repositories.project;

import com.waltmann.waltmann_api.domain.project.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProjectRepository extends JpaRepository<Project, UUID> {
}

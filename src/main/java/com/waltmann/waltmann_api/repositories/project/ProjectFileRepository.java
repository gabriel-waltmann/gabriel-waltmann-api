package com.waltmann.waltmann_api.repositories.project;

import com.waltmann.waltmann_api.domain.project.ProjectFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProjectFileRepository extends JpaRepository<ProjectFile, UUID> {
}

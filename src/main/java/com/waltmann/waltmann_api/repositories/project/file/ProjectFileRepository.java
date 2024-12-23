package com.waltmann.waltmann_api.repositories.project.file;

import com.waltmann.waltmann_api.domain.project.file.ProjectFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ProjectFileRepository extends JpaRepository<ProjectFile, UUID> {
  List<ProjectFile> findByProjectId(UUID projectId);
}

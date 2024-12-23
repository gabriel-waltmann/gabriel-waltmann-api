package com.waltmann.waltmann_api.repositories.project.tech;

import com.waltmann.waltmann_api.domain.project.tech.ProjectTech;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ProjectTechRepository extends JpaRepository<ProjectTech, UUID> {
  List<ProjectTech> findByProjectId(UUID projectId);
}

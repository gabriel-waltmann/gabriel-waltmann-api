package com.waltmann.waltmann_api.repositories.project;

import com.waltmann.waltmann_api.domain.project.ProjectTech;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProjectTechRepository extends JpaRepository<ProjectTech, UUID> {
}

package com.waltmann.waltmann_api.service.project.tech;

import com.waltmann.waltmann_api.domain.project.Project;
import com.waltmann.waltmann_api.domain.project.tech.ProjectTech;
import com.waltmann.waltmann_api.domain.tech.Tech;
import com.waltmann.waltmann_api.repositories.project.tech.ProjectTechRepository;
import com.waltmann.waltmann_api.service.tech.TechService;
import com.waltmann.waltmann_api.service.project.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProjectTechService {
  @Autowired
  private ProjectTechRepository repository;

  @Autowired
  private ProjectService projectService;

  @Autowired
  private TechService techService;

  private ProjectTech create(UUID projectId, UUID techId) {

    Project project = projectService.retrievesOne(projectId);

    if (project == null) {
      throw new RuntimeException("Project not found");
    }

    Tech tech = techService.retrievesOne(techId);

    ProjectTech projectTech = new ProjectTech();
    projectTech.setProject(project);
    projectTech.setTech(tech);

    repository.save(projectTech);

    return projectTech;
  }
}

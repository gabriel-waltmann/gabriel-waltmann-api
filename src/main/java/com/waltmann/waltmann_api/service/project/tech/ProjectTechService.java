package com.waltmann.waltmann_api.service.project.tech;

import com.waltmann.waltmann_api.domain.project.Project;
import com.waltmann.waltmann_api.domain.project.tech.ProjectTech;
import com.waltmann.waltmann_api.domain.tech.Tech;
import com.waltmann.waltmann_api.repositories.project.ProjectRepository;
import com.waltmann.waltmann_api.repositories.project.tech.ProjectTechRepository;
import com.waltmann.waltmann_api.service.tech.TechService;
import com.waltmann.waltmann_api.service.project.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProjectTechService {
  @Autowired
  private ProjectTechRepository repository;

  @Autowired
  private ProjectRepository projectRepository;

  @Autowired
  private TechService techService;

  public ProjectTech create(UUID projectId, UUID techId) {
    Project project = projectRepository.findById(projectId)
        .orElseThrow(() -> new RuntimeException("Project not found"));

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

  public List<ProjectTech> retrieves(UUID projectId) {
    projectRepository.findById(projectId)
        .orElseThrow(() -> new RuntimeException("Project not found"));

    return repository.findByProjectId(projectId);
  }

  public ProjectTech retrievesOne(UUID id, UUID projectId) {
    projectRepository.findById(projectId)
        .orElseThrow(() -> new RuntimeException("Project not found"));

    return repository.findById(id).orElse(null);
  }

  public void deleteOne(UUID id, UUID projectId) {
    projectRepository.findById(projectId)
        .orElseThrow(() -> new RuntimeException("Project not found"));

    repository.deleteById(id);
  }

  public void delete(UUID projectId) {
    List<ProjectTech> projectTechs = repository.findByProjectId(projectId);

    for (ProjectTech projectTech : projectTechs) {
      deleteOne(projectTech.getId(), projectId);
    }
  }
}

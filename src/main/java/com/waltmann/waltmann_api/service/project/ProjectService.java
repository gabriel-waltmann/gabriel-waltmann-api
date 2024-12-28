package com.waltmann.waltmann_api.service.project;

import com.waltmann.waltmann_api.domain.project.Project;
import com.waltmann.waltmann_api.domain.project.ProjectRequestDTO;
import com.waltmann.waltmann_api.repositories.project.ProjectRepository;
import com.waltmann.waltmann_api.service.file.FileService;
import com.waltmann.waltmann_api.service.project.file.ProjectFileService;
import com.waltmann.waltmann_api.service.project.tech.ProjectTechService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProjectService {
  @Autowired
  private ProjectRepository repository;

  @Autowired
  private ProjectFileService projectFileService;

  @Autowired
  private ProjectTechService projectTechService;

  public Project create(ProjectRequestDTO data) {
    if (data.title() == null || data.description() == null) {
      throw new RuntimeException("Invalid data");
    }

    if (data.title().isEmpty() || data.description().isEmpty()) {
      throw new RuntimeException("Invalid data");
    }

    Project project = new Project();

    project.setTitle(data.title());
    project.setDescription(data.description());

    repository.save(project);

    return project;
  }

  public List<Project> retrieves(int page, int size) {
    Pageable pageable = PageRequest.of(page, size);

    Page<Project> projectsPage = repository.findAll(pageable);

    return projectsPage.stream().toList();
  }

  public Project retrievesOne(UUID id) {
    Project project = repository.findById(id).orElse(null);

    if (project == null) {
      throw new RuntimeException("Project not found");
    }

    return project;
  }

  public Project update(UUID id, ProjectRequestDTO data) {
    Project project = repository.findById(id).orElse(null);

    if (project == null) {
      throw new RuntimeException("Project not found");
    }

    if (data.title() == null || data.description() == null) {
      throw new RuntimeException("Invalid data");
    }

    if (data.title().isEmpty() || data.description().isEmpty()) {
      throw new RuntimeException("Invalid data");
    }

    project.setTitle(data.title());
    project.setDescription(data.description());

    repository.save(project);

    return project;
  }

  public void delete(UUID id) {
    Project project = repository.findById(id).orElse(null);

    if (project == null) {
      throw new RuntimeException("Project not found");
    }

    projectFileService.delete(id);

    projectTechService.delete(id);

    repository.deleteById(id);
  }
}

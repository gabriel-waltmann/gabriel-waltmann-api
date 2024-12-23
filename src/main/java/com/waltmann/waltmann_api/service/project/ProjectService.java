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
  private FileService fileService;

  @Autowired
  private ProjectFileService projectFileService;

  @Autowired
  private ProjectTechService projectTechService;

  public Project create(ProjectRequestDTO data) {
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
    return repository.findById(id).orElse(null);
  }

  public Project update(UUID id, ProjectRequestDTO data) {
    Project project = repository.findById(id).orElse(null);

    if (project == null) {
      return null;
    }

    project.setTitle(data.title());
    project.setDescription(data.description());

    repository.save(project);

    return project;
  }

  public void delete(UUID id) {
    projectFileService.delete(id);

    projectTechService.delete(id);

    repository.deleteById(id);
  }
}

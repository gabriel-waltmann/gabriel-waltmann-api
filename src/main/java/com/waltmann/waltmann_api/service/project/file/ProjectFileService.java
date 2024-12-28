package com.waltmann.waltmann_api.service.project.file;

import com.waltmann.waltmann_api.domain.file.File;
import com.waltmann.waltmann_api.domain.project.Project;
import com.waltmann.waltmann_api.domain.project.file.ProjectFile;
import com.waltmann.waltmann_api.repositories.project.file.ProjectFileRepository;
import com.waltmann.waltmann_api.repositories.project.ProjectRepository;
import com.waltmann.waltmann_api.service.file.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Service
public class ProjectFileService {
  @Autowired
  private ProjectFileRepository repository;

  @Autowired
  private ProjectRepository projectRepository;

  @Autowired
  private FileService fileService;

  public ProjectFile create(UUID projectId, MultipartFile multipartFile) {
    Project project = projectRepository.findById(projectId)
        .orElseThrow(() -> new RuntimeException("Project not found"));

    if (multipartFile == null) {
      throw new RuntimeException("File not found");
    }

    File file = fileService.create(multipartFile);

    ProjectFile projectFile = new ProjectFile();

    projectFile.setProject(project);
    projectFile.setFile(file);

    repository.save(projectFile);

    return projectFile;
  }

  public ProjectFile retrievesOne(UUID id, UUID projectId) {
    projectRepository.findById(projectId)
        .orElseThrow(() -> new RuntimeException("Project not found"));

    return repository.findById(id)
        .orElseThrow(() -> new RuntimeException("Project file not found"));
  }

  public List<ProjectFile> retrieves(UUID projectId) {
    projectRepository.findById(projectId)
        .orElseThrow(() -> new RuntimeException("Project not found"));

    List<ProjectFile> projectFiles = repository.findByProjectId(projectId);

    return projectFiles.stream().toList();
  }

  public void deleteOne(UUID id, UUID projectId) {
    projectRepository.findById(projectId)
        .orElseThrow(() -> new RuntimeException("Project not found"));

    ProjectFile projectFile = retrievesOne(id, projectId);

    if (projectFile == null) {
      throw new RuntimeException("Project file not found");
    }

    UUID fileId = projectFile.getFile().getId();
    File file = fileService.retrievesOne(fileId);

    if (file == null) {
      throw new RuntimeException("File not found");
    }

    fileService.delete(fileId);

    repository.delete(projectFile);
  }

  public void delete(UUID projectId) {
    projectRepository.findById(projectId)
        .orElseThrow(() -> new RuntimeException("Project not found"));

    List<ProjectFile> projectFiles = repository.findByProjectId(projectId);

    for (ProjectFile projectFile : projectFiles) {
      deleteOne(projectFile.getId(), projectId);
    }
  }
}

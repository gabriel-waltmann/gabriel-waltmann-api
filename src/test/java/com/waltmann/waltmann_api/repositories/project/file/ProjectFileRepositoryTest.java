package com.waltmann.waltmann_api.repositories.project.file;

import com.waltmann.waltmann_api.domain.file.File;
import com.waltmann.waltmann_api.domain.project.Project;
import com.waltmann.waltmann_api.domain.project.file.ProjectFile;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class ProjectFileRepositoryTest {

  @Autowired
  EntityManager entityManager;

  @Autowired
  private ProjectFileRepository repository;

  @Test
  @DisplayName("Should get project techs by project id")
  void findByProjectIdSuccess() {
    Project project = createProject("Project 1", "Description 1");

    File file1 = createFile("File 1");
    File file2 = createFile("File 2");

    createProjectFile(project, file1);
    createProjectFile(project, file2);

    assertEquals(2, repository.findByProjectId(project.getId()).size());
  }

  @Test
  @DisplayName("Should get empty list when project not found")
  void findByProjectIdNotFound() {
    List<ProjectFile> projectTechs = repository.findByProjectId(UUID.randomUUID());

    assertTrue(projectTechs.isEmpty());
  }

  private Project createProject(String projectTitle, String projectDescription) {
    Project project = new Project();
    project.setTitle(projectTitle);
    project.setDescription(projectDescription);
    entityManager.persist(project);

    return project;
  }

  private File createFile(String name) {
    File file = new File();
    file.setName(name);
    entityManager.persist(file);

    return file;
  }

  private ProjectFile createProjectFile(Project project, File file) {
    ProjectFile projectTech = new ProjectFile();
    projectTech.setProject(project);
    projectTech.setFile(file);
    entityManager.persist(projectTech);

    return projectTech;
  }
}
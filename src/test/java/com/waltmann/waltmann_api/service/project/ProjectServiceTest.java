package com.waltmann.waltmann_api.service.project;

import com.waltmann.waltmann_api.domain.project.Project;
import com.waltmann.waltmann_api.domain.project.ProjectRequestDTO;
import com.waltmann.waltmann_api.repositories.project.ProjectRepository;
import com.waltmann.waltmann_api.repositories.project.file.ProjectFileRepository;
import com.waltmann.waltmann_api.repositories.project.tech.ProjectTechRepository;
import com.waltmann.waltmann_api.service.project.file.ProjectFileService;
import com.waltmann.waltmann_api.service.project.tech.ProjectTechService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class ProjectServiceTest {
  @Mock
  private ProjectRepository repository;

  @Autowired
  @InjectMocks
  private ProjectService service;

  @Mock
  private ProjectFileService projectFileService;

  @Mock
  private ProjectTechService projectTechService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  @DisplayName("Should create project successfully when data is valid")
  void createProjectSuccess() {
    ProjectRequestDTO projectRequestDTO = new ProjectRequestDTO(
        "Project 1",
        "Description 1"
    );

    Project project1 = new Project();
    project1.setTitle(projectRequestDTO.title());
    project1.setDescription(projectRequestDTO.description());

    when(repository.save(project1)).thenReturn(project1);

    Project project = service.create(projectRequestDTO);

    assertThat(project).isNotNull();
    assertThat(project.getTitle()).isEqualTo(projectRequestDTO.title());
    assertThat(project.getDescription()).isEqualTo(projectRequestDTO.description());
  }

  @Test
  @DisplayName("Should throw exception when data is not valid")
  void createProjectFail() {
    ProjectRequestDTO projectRequestDTO = new ProjectRequestDTO(
        "",
        ""
    );

    Project project1 = new Project();
    project1.setTitle(projectRequestDTO.title());
    project1.setDescription(projectRequestDTO.description());

    Exception thrown = Assertions.assertThrows(
        RuntimeException.class,
        () -> service.create(projectRequestDTO)
    );

    Assertions.assertEquals("Invalid data", thrown.getMessage());
  }

  @Test
  @DisplayName("Should retrieve projects successfully")
  void retrieves() {
    Project project1 = new Project();
    project1.setTitle("Project 1");
    project1.setDescription("Description 1");

    Project project2 = new Project();
    project2.setTitle("Project 2");
    project2.setDescription("Description 2");

    List<Project> projects = new ArrayList<>();
    projects.add(project1);
    projects.add(project2);

    Pageable pageable = PageRequest.of(0, 10);

    Page<Project> projectPage = new PageImpl<>(projects, pageable, projects.size());

    when(repository.findAll(pageable)).thenReturn(projectPage);

    assertEquals(2, service.retrieves(0, 10).size());
  }

  @Test
  @DisplayName("Should retrieve project successfully when id is valid")
  void retrievesOneSuccess() {
    Project project1 = new Project();
    project1.setTitle("Project 1");
    project1.setDescription("Description 1");

    when(repository.findById(project1.getId())).thenReturn(java.util.Optional.of(project1));

    assertThat(service.retrievesOne(project1.getId())).isNotNull();
  }

  @Test
  @DisplayName("Should throw exception when id is valid")
  void retrievesOneFail() {
    UUID id = UUID.randomUUID();

    Exception thrown = Assertions.assertThrows(
        RuntimeException.class,
        () -> service.retrievesOne(id)
    );

    Assertions.assertEquals("Project not found", thrown.getMessage());
  }

  @Test
  @DisplayName("Should update project successfully when data is valid")
  void updateSuccess() {
    ProjectRequestDTO projectRequestDTO = new ProjectRequestDTO(
        "Project 1 updated",
        "Description 1 updated"
    );

    Project project1 = new Project();
    project1.setTitle(projectRequestDTO.title());
    project1.setDescription(projectRequestDTO.description());

    when(repository.findById(project1.getId())).thenReturn(java.util.Optional.of(project1));
    when(repository.save(project1)).thenReturn(project1);

    Project project = service.update(project1.getId(), projectRequestDTO);

    assertThat(project).isNotNull();
    assertThat(project.getTitle()).isEqualTo(projectRequestDTO.title());
    assertThat(project.getDescription()).isEqualTo(projectRequestDTO.description());
  }

  @Test
  @DisplayName("Should throw exception when data is not valid")
  void updateInvalidData() {
    ProjectRequestDTO projectRequestDTO = new ProjectRequestDTO(
        "",
        ""
    );

    Project project1 = new Project();
    project1.setTitle(projectRequestDTO.title());
    project1.setDescription(projectRequestDTO.description());

    when(repository.findById(project1.getId())).thenReturn(java.util.Optional.of(project1));

    Exception thrown = Assertions.assertThrows(
        RuntimeException.class,
        () -> service.update(project1.getId(), projectRequestDTO)
    );

    Assertions.assertEquals("Invalid data", thrown.getMessage());
  }

  @Test
  @DisplayName("Should throw exception when id is not valid")
  void updateInvalidId() {
    ProjectRequestDTO projectRequestDTO = new ProjectRequestDTO(
        "Project 1 updated",
        "Description 1 updated"
    );

    Project project1 = new Project();
    project1.setTitle(projectRequestDTO.title());
    project1.setDescription(projectRequestDTO.description());

    Exception thrown = Assertions.assertThrows(
        RuntimeException.class,
        () -> service.update(project1.getId(), projectRequestDTO)
    );

    Assertions.assertEquals("Project not found", thrown.getMessage());
  }

  @Test
  @DisplayName("Should delete project successfully when id is valid")
  void deleteSuccess() {
    Project project1 = new Project();
    project1.setTitle("Project 1");
    project1.setDescription("Description 1");

    when(repository.findById(project1.getId())).thenReturn(java.util.Optional.of(project1));

    service.delete(project1.getId());
  }

  @Test
  @DisplayName("Should throw exception when id is not valid")
  void deleteFail() {
    Exception thrown = Assertions.assertThrows(
      RuntimeException.class,
      () -> service.delete(UUID.randomUUID())
    );

    Assertions.assertEquals("Project not found", thrown.getMessage());
  }
}
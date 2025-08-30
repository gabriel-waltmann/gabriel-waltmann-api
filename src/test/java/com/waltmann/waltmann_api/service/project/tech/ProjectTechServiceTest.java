package com.waltmann.waltmann_api.service.project.tech;

import com.waltmann.waltmann_api.domain.tech.Tech;
import com.waltmann.waltmann_api.domain.project.Project;
import com.waltmann.waltmann_api.domain.project.ProjectRequestDTO;
import com.waltmann.waltmann_api.domain.project.tech.ProjectTech;
import com.waltmann.waltmann_api.repositories.tech.TechRepository;
import com.waltmann.waltmann_api.repositories.project.ProjectRepository;
import com.waltmann.waltmann_api.repositories.project.tech.ProjectTechRepository;
import com.waltmann.waltmann_api.service.tech.TechService;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.when;

class ProjectTechServiceTest {
  @Mock
  private ProjectTechRepository repository;

  @InjectMocks
  private ProjectTechService service;

  @Mock
  private ProjectRepository projectRepository;

  @Mock
  private TechService techService;

  @Mock
  private TechRepository techRepository;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  @DisplayName("Should create project tech successfully when data is valid")
  void createSuccess() {
    Project project = new Project();
    Tech tech = new Tech();

    ProjectTech projectTech = new ProjectTech();
    projectTech.setProject(project);
    projectTech.setTech(tech);

    when(projectRepository.findById(project.getId())).thenReturn(Optional.of(project));
    when(techRepository.findById(tech.getId())).thenReturn(Optional.of(tech));
    when(repository.save(projectTech)).thenReturn(projectTech);

    ProjectTech result = service.create(project.getId(), tech.getId());

    assertNotNull(result);
  }

  @Test
  @DisplayName("Should throw exception when tech id is not valid")
  void createFailInvalidTechId() {
    UUID techId = UUID.randomUUID();

    Project project = new Project();

    when(projectRepository.findById(project.getId())).thenReturn(Optional.of(project));

    Exception thrown = assertThrows(
        RuntimeException.class,
        () -> service.create(techId, project.getId())
    );

    assertEquals("Tech not found", thrown.getMessage());
  }

  @Test
  @DisplayName("Should throw exception when project id is not valid")
  void createFailInvalidProjectId() {
    UUID projectId = UUID.randomUUID();

    Tech tech = new Tech();

    when(techRepository.findById(tech.getId())).thenReturn(Optional.of(tech));
    Exception thrown = assertThrows(
        RuntimeException.class,
        () -> service.create(tech.getId(), projectId)
    );

    assertEquals("Project not found", thrown.getMessage());
  }

  @Test
  @DisplayName("Should retrieve project tech successfully when data is valid")
  void retrievesOneSuccess() {
    ProjectRequestDTO projectRequestDTO = new ProjectRequestDTO(
        "Project 1",
        "Description 1"
    );

    Project project1 = new Project();
    project1.setTitle(projectRequestDTO.title());
    project1.setDescription(projectRequestDTO.description());

    ProjectTech projectTech1 = new ProjectTech();
    projectTech1.setProject(project1);

    when(repository.findById(projectTech1.getId())).thenReturn(Optional.of(projectTech1));
    when(projectRepository.findById(project1.getId())).thenReturn(Optional.of(project1));

    assertNotNull(service.retrievesOne(projectTech1.getId(), project1.getId()));
  }

  @Test
  @DisplayName("Should throw exception when project id is not valid")
  void retrievesOneFailNotFoundProject() {
    UUID id = UUID.randomUUID();
    UUID projectId = UUID.randomUUID();

    Exception thrown = assertThrows(
        RuntimeException.class,
        () -> service.retrievesOne(id, projectId)
    );

    assertEquals("Project not found", thrown.getMessage());
  }

  @Test
  @DisplayName("Should throw exception when id is not valid")
  void retrievesOneFailNotFound() {
    UUID id = UUID.randomUUID();
    Project project = new Project();
    UUID projectId = project.getId();

    when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));

    Exception thrown = assertThrows(
        RuntimeException.class,
        () -> service.retrievesOne(id, projectId)
    );

    assertEquals("Project tech not found", thrown.getMessage());
  }

  @Test
  @DisplayName("Should retrieve project techs successfully when data is valid")
  void retrievesSuccess() {
    Project project = new Project();

    when(projectRepository.findById(project.getId())).thenReturn(Optional.of(project));

    List<ProjectTech> projectTechs = new ArrayList<ProjectTech>();

    ProjectTech projectTech1 = new ProjectTech();
    projectTech1.setProject(project);
    projectTechs.add(projectTech1);

    ProjectTech projectTech2 = new ProjectTech();
    projectTech2.setProject(project);
    projectTechs.add(projectTech2);

    when(repository.findByProjectId(projectTech1.getProject().getId())).thenReturn(projectTechs);

    assertNotNull(service.retrieves(projectTech1.getProject().getId()));
  }

  @Test
  @DisplayName("Should throw exception when project id is not valid")
  void retrievesFailNotFoundProject() {
    UUID projectId = UUID.randomUUID();

    Exception thrown = assertThrows(
        RuntimeException.class,
        () -> service.retrieves(projectId)
    );

    assertEquals("Project not found", thrown.getMessage());
  }

  @Test
  @DisplayName("Should delete project tech successfully when data is valid")
  void deleteOneSuccess() {
    Project project = new Project();
    Tech tech = new Tech();
    ProjectTech projectTech = new ProjectTech();
    projectTech.setProject(project);
    projectTech.setTech(tech);

    when(projectRepository.findById(project.getId())).thenReturn(Optional.of(project));
    when(techService.retrievesOne(projectTech.getTech().getId())).thenReturn(tech);
    when(repository.findById(projectTech.getId())).thenReturn(Optional.of(projectTech));

    service.deleteOne(projectTech.getId(), project.getId());
  }

  @Test
  @DisplayName("Should throw exception when id is not valid")
  void deleteOneFailNotFound() {
    UUID id = UUID.randomUUID();
    Project project = new Project();

    when(projectRepository.findById(project.getId())).thenReturn(Optional.of(project));

    Exception thrown = assertThrows(
        RuntimeException.class,
        () -> service.deleteOne(id, project.getId())
    );

    assertEquals("Project tech not found", thrown.getMessage());
  }

  @Test
  @DisplayName("Should throw exception when project id is not valid")
  void deleteOneFailNotFoundProject() {
    UUID id = UUID.randomUUID();
    UUID projectId = UUID.randomUUID();

    Exception thrown = assertThrows(
        RuntimeException.class,
        () -> service.deleteOne(id, projectId)
    );

    assertEquals("Project not found", thrown.getMessage());
  }

  @Test
  @DisplayName("Should delete project techs successfully")
  void deleteSuccess() {
    Project project = new Project();

    when(projectRepository.findById(project.getId())).thenReturn(Optional.of(project));

    service.delete(project.getId());
  }

  @Test
  @DisplayName("Should throw exception when project id is not valid")
  void deleteFailNotFound() {
    UUID projectId = UUID.randomUUID();

    Exception thrown = assertThrows(
        RuntimeException.class,
        () -> service.delete(projectId)
    );

    assertEquals("Project not found", thrown.getMessage());
  }
}
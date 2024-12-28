package com.waltmann.waltmann_api.service.project.file;

import com.waltmann.waltmann_api.domain.file.File;
import com.waltmann.waltmann_api.domain.project.Project;
import com.waltmann.waltmann_api.domain.project.ProjectRequestDTO;
import com.waltmann.waltmann_api.domain.project.file.ProjectFile;
import com.waltmann.waltmann_api.repositories.file.FileRepository;
import com.waltmann.waltmann_api.repositories.project.ProjectRepository;
import com.waltmann.waltmann_api.repositories.project.file.ProjectFileRepository;
import com.waltmann.waltmann_api.service.file.FileService;
import com.waltmann.waltmann_api.service.project.ProjectService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

class ProjectFileServiceTest {
  @Mock
  private ProjectFileRepository repository;

  @Autowired
  @InjectMocks
  private ProjectFileService service;

  @Mock
  private ProjectRepository projectRepository;

  @Mock
  private ProjectService projectService;

  @Mock
  private FileService fileService;

  @Mock
  private FileRepository fileRepository;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  @DisplayName("Should create project file successfully when data is valid")
  void createSuccess() {
    Project project = new Project();
    File file = new File();
    MultipartFile multipartFile =new MockMultipartFile(
        "file",                     // Name of the parameter in the request
        "testfile.txt",             // Original filename
        "text/plain",               // Content type
        "This is a test file.".getBytes() // File content
    ) ;

    ProjectFile projectFile = new ProjectFile();
    projectFile.setProject(project);
    projectFile.setFile(file);


    when(projectRepository.findById(project.getId())).thenReturn(Optional.of(project));
    when(fileRepository.findById(file.getId())).thenReturn(Optional.of(file));
    when(repository.save(projectFile)).thenReturn(projectFile);

    ProjectFile result = service.create(project.getId(), multipartFile);

    assertThat(result).isNotNull();
  }

  @Test
  @DisplayName("Should throw exception when file is not valid")
  void createFailInvalidData() {
    ProjectRequestDTO projectRequestDTO = new ProjectRequestDTO(
        "Project 1",
        "Description 1"
    );

    Project project1 = new Project();
    project1.setTitle(projectRequestDTO.title());
    project1.setDescription(projectRequestDTO.description());

    when(projectRepository.save(project1)).thenReturn(project1);
    when(projectRepository.findById(project1.getId())).thenReturn(Optional.of(project1));

    Exception thrown = Assertions.assertThrows(
        RuntimeException.class,
        () -> service.create(project1.getId(), null)
    );

    Assertions.assertEquals("File not found", thrown.getMessage());
  }

  @Test
  @DisplayName("Should throw exception when project id is not valid")
  void createFailInvalidProjectId() {
    Exception thrown = Assertions.assertThrows(
        RuntimeException.class,
        () -> service.create(UUID.randomUUID(), null)
    );

    Assertions.assertEquals("Project not found", thrown.getMessage());
  }

  @Test
  @DisplayName("Should retrieve project file successfully when data is valid")
  void retrievesOneSuccess() {
    ProjectRequestDTO projectRequestDTO = new ProjectRequestDTO(
        "Project 1",
        "Description 1"
    );

    Project project1 = new Project();
    project1.setTitle(projectRequestDTO.title());
    project1.setDescription(projectRequestDTO.description());

    ProjectFile projectFile1 = new ProjectFile();
    projectFile1.setProject(project1);

    when(repository.findById(projectFile1.getId())).thenReturn(Optional.of(projectFile1));
    when(projectRepository.findById(project1.getId())).thenReturn(Optional.of(project1));

    assertThat(service.retrievesOne(projectFile1.getId(), project1.getId())).isNotNull();
  }

  @Test
  @DisplayName("Should throw exception when project id is not valid")
  void retrievesOneFailNotFoundProject() {
    UUID id = UUID.randomUUID();
    UUID projectId = UUID.randomUUID();

    Exception thrown = Assertions.assertThrows(
        RuntimeException.class,
        () -> service.retrievesOne(id, projectId)
    );

    Assertions.assertEquals("Project not found", thrown.getMessage());
  }

  @Test
  @DisplayName("Should throw exception when id is not valid")
  void retrievesOneFailNotFound() {
    UUID id = UUID.randomUUID();
    Project project = new Project();
    UUID projectId = project.getId();

    when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));

    Exception thrown = Assertions.assertThrows(
        RuntimeException.class,
        () -> service.retrievesOne(id, projectId)
    );

    Assertions.assertEquals("Project file not found", thrown.getMessage());
  }

  @Test
  @DisplayName("Should retrieve project files successfully when data is valid")
  void retrievesSuccess() {
    Project project = new Project();

    when(projectRepository.findById(project.getId())).thenReturn(Optional.of(project));

    List<ProjectFile> projectFiles = new ArrayList<ProjectFile>();

    ProjectFile projectFile1 = new ProjectFile();
    projectFile1.setProject(project);
    projectFiles.add(projectFile1);

    ProjectFile projectFile2 = new ProjectFile();
    projectFile2.setProject(project);
    projectFiles.add(projectFile2);

    when(repository.findByProjectId(projectFile1.getProject().getId())).thenReturn(projectFiles);

    assertThat(service.retrieves(projectFile1.getProject().getId())).isNotNull();
  }

  @Test
  @DisplayName("Should throw exception when project id is not valid")
  void retrievesFailNotFoundProject() {
    UUID projectId = UUID.randomUUID();

    Exception thrown = Assertions.assertThrows(
        RuntimeException.class,
        () -> service.retrieves(projectId)
    );

    Assertions.assertEquals("Project not found", thrown.getMessage());
  }

  @Test
  @DisplayName("Should delete project file successfully when data is valid")
  void deleteOneSuccess() {
    Project project = new Project();
    File file = new File();
    ProjectFile projectFile = new ProjectFile();
    projectFile.setProject(project);
    projectFile.setFile(file);

    when(projectRepository.findById(project.getId())).thenReturn(Optional.of(project));
    when(fileService.retrievesOne(projectFile.getFile().getId())).thenReturn(file);
    when(repository.findById(projectFile.getId())).thenReturn(Optional.of(projectFile));

    service.deleteOne(projectFile.getId(), project.getId());
  }

  @Test
  @DisplayName("Should throw exception when id is not valid")
  void deleteOneFailNotFound() {
    UUID id = UUID.randomUUID();
    Project project = new Project();

    when(projectRepository.findById(project.getId())).thenReturn(Optional.of(project));

    Exception thrown = Assertions.assertThrows(
        RuntimeException.class,
        () -> service.deleteOne(id, project.getId())
    );

    Assertions.assertEquals("Project file not found", thrown.getMessage());
  }

  @Test
  @DisplayName("Should throw exception when project id is not valid")
  void deleteOneFailNotFoundProject() {
    UUID id = UUID.randomUUID();
    UUID projectId = UUID.randomUUID();

    Exception thrown = Assertions.assertThrows(
        RuntimeException.class,
        () -> service.deleteOne(id, projectId)
    );

    Assertions.assertEquals("Project not found", thrown.getMessage());
  }

  @Test
  @DisplayName("Should delete project files successfully")
  void deleteSuccess() {
    Project project = new Project();

    when(projectRepository.findById(project.getId())).thenReturn(Optional.of(project));

    service.delete(project.getId());
  }

  @Test
  @DisplayName("Should throw exception when project id is not valid")
  void deleteFailNotFound() {
    UUID projectId = UUID.randomUUID();

    Exception thrown = Assertions.assertThrows(
        RuntimeException.class,
        () -> service.delete(projectId)
    );

    Assertions.assertEquals("Project not found", thrown.getMessage());
  }
}
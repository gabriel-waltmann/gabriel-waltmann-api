package com.waltmann.waltmann_api.service.project.file;

import com.waltmann.waltmann_api.domain.file.File;
import com.waltmann.waltmann_api.domain.project.Project;
import com.waltmann.waltmann_api.domain.project.file.ProjectFile;
import com.waltmann.waltmann_api.repositories.project.ProjectRepository;
import com.waltmann.waltmann_api.repositories.project.file.ProjectFileRepository;
import com.waltmann.waltmann_api.service.file.FileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProjectFileServiceTest {
    @Mock
    private ProjectFileRepository repository;

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private FileService fileService;

    @InjectMocks
    private ProjectFileService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should create project file successfully when project exists and file is valid")
    void createSuccess() {
        UUID projectId = UUID.randomUUID();
        Project project = createProject("Test Project", "Test Description");
        MultipartFile multipartFile = createMultipartFile();
        File file = createFile("test-file.txt", "test-key");

        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));
        when(fileService.create(multipartFile)).thenReturn(file);
        when(repository.save(any(ProjectFile.class))).thenAnswer(invocation -> {
            ProjectFile projectFileToSave = invocation.getArgument(0);
            projectFileToSave.setId(UUID.randomUUID());
            return projectFileToSave;
        });

        ProjectFile result = service.create(projectId, multipartFile);

        assertNotNull(result.getId());
        assertEquals(project, result.getProject());
        assertEquals(file, result.getFile());
        verify(projectRepository).findById(projectId);
        verify(fileService).create(multipartFile);
        verify(repository).save(any(ProjectFile.class));
    }

    @Test
    @DisplayName("Should throw exception when project is not found for create")
    void createFailProjectNotFound() {
        UUID projectId = UUID.randomUUID();
        MultipartFile multipartFile = createMultipartFile();

        when(projectRepository.findById(projectId)).thenReturn(Optional.empty());

        Exception thrown = assertThrows(
                RuntimeException.class,
                () -> service.create(projectId, multipartFile)
        );

        assertEquals("Project not found", thrown.getMessage());
        verify(projectRepository).findById(projectId);
        verify(fileService, never()).create(any(MultipartFile.class));
        verify(repository, never()).save(any(ProjectFile.class));
    }

    @Test
    @DisplayName("Should throw exception when multipart file is null")
    void createFailFileNull() {
        UUID projectId = UUID.randomUUID();
        Project project = createProject("Test Project", "Test Description");

        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));

        Exception thrown = assertThrows(
                RuntimeException.class,
                () -> service.create(projectId, null)
        );

        assertEquals("File not found", thrown.getMessage());
        verify(projectRepository).findById(projectId);
        verify(fileService, never()).create(any(MultipartFile.class));
        verify(repository, never()).save(any(ProjectFile.class));
    }

    @Test
    @DisplayName("Should retrieve project file successfully when project and file exist")
    void retrievesOneSuccess() {
        UUID projectId = UUID.randomUUID();
        UUID fileId = UUID.randomUUID();
        Project project = createProject("Test Project", "Test Description");
        ProjectFile projectFile = createProjectFile(project, createFile("test-file.txt", "test-key"));

        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));
        when(repository.findById(fileId)).thenReturn(Optional.of(projectFile));

        ProjectFile result = service.retrievesOne(fileId, projectId);

        assertEquals(projectFile.getId(), result.getId());
        assertEquals(project, result.getProject());
        verify(projectRepository).findById(projectId);
        verify(repository).findById(fileId);
    }

    @Test
    @DisplayName("Should throw exception when project is not found for retrievesOne")
    void retrievesOneFailProjectNotFound() {
        UUID projectId = UUID.randomUUID();
        UUID fileId = UUID.randomUUID();

        when(projectRepository.findById(projectId)).thenReturn(Optional.empty());

        Exception thrown = assertThrows(
                RuntimeException.class,
                () -> service.retrievesOne(fileId, projectId)
        );

        assertEquals("Project not found", thrown.getMessage());
        verify(projectRepository).findById(projectId);
        verify(repository, never()).findById(any(UUID.class));
    }

    @Test
    @DisplayName("Should throw exception when file is not found for retrievesOne")
    void retrievesOneFailFileNotFound() {
        UUID projectId = UUID.randomUUID();
        UUID fileId = UUID.randomUUID();
        Project project = createProject("Test Project", "Test Description");

        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));
        when(repository.findById(fileId)).thenReturn(Optional.empty());

        Exception thrown = assertThrows(
                RuntimeException.class,
                () -> service.retrievesOne(fileId, projectId)
        );

        assertEquals("Project file not found", thrown.getMessage());
        verify(projectRepository).findById(projectId);
        verify(repository).findById(fileId);
    }

    @Test
    @DisplayName("Should retrieve project files successfully when project exists")
    void retrievesSuccess() {
        UUID projectId = UUID.randomUUID();
        Project project = createProject("Test Project", "Test Description");
        List<ProjectFile> projectFiles = Arrays.asList(
                createProjectFile(project, createFile("file1.txt", "key1")),
                createProjectFile(project, createFile("file2.txt", "key2"))
        );

        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));
        when(repository.findByProjectId(projectId)).thenReturn(projectFiles);

        List<ProjectFile> result = service.retrieves(projectId);

        assertEquals(2, result.size());
        assertEquals("file1.txt", result.get(0).getFile().getName());
        assertEquals("file2.txt", result.get(1).getFile().getName());
        verify(projectRepository).findById(projectId);
        verify(repository).findByProjectId(projectId);
    }

    @Test
    @DisplayName("Should throw exception when project is not found for retrieves")
    void retrievesFailProjectNotFound() {
        UUID projectId = UUID.randomUUID();

        when(projectRepository.findById(projectId)).thenReturn(Optional.empty());

        Exception thrown = assertThrows(
                RuntimeException.class,
                () -> service.retrieves(projectId)
        );

        assertEquals("Project not found", thrown.getMessage());
        verify(projectRepository).findById(projectId);
        verify(repository, never()).findByProjectId(any(UUID.class));
    }

    @Test
    @DisplayName("Should delete project file successfully when it exists")
    void deleteSuccess() {
        UUID projectFileId = UUID.randomUUID();

        doNothing().when(repository).deleteById(projectFileId);

        Boolean result = service.delete(projectFileId);

        assertTrue(result);
        verify(repository).deleteById(projectFileId);
    }

    private MultipartFile createMultipartFile() {
        return new MockMultipartFile(
                "file",
                "testfile.txt",
                "text/plain",
                "This is a test file.".getBytes()
        );
    }

    private Project createProject(String title, String description) {
        Project project = new Project();
        project.setId(UUID.randomUUID());
        project.setTitle(title);
        project.setDescription(description);
        return project;
    }

    private File createFile(String name, String key) {
        File file = new File();
        file.setId(UUID.randomUUID());
        file.setName(name);
        file.setKey(key);
        return file;
    }

    private ProjectFile createProjectFile(Project project, File file) {
        ProjectFile projectFile = new ProjectFile();
        projectFile.setId(UUID.randomUUID());
        projectFile.setProject(project);
        projectFile.setFile(file);
        return projectFile;
    }
}
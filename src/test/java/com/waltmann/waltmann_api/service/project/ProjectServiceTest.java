package com.waltmann.waltmann_api.service.project;

import com.waltmann.waltmann_api.domain.project.Project;
import com.waltmann.waltmann_api.domain.project.ProjectRequestDTO;
import com.waltmann.waltmann_api.repositories.project.ProjectRepository;
import com.waltmann.waltmann_api.service.project.file.ProjectFileService;
import com.waltmann.waltmann_api.service.project.tech.ProjectTechService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProjectServiceTest {
    @Mock
    private ProjectRepository repository;

    @Mock
    private ProjectFileService projectFileService;

    @Mock
    private ProjectTechService projectTechService;

    @InjectMocks
    private ProjectService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should create project successfully when data is valid")
    void createProjectSuccess() {
        ProjectRequestDTO projectRequestDTO = new ProjectRequestDTO(
                "Project 1",
                "Description 1"
        );

        // Mock repository save to return project with ID set
        when(repository.save(any(Project.class))).thenAnswer(invocation -> {
            Project projectToSave = invocation.getArgument(0);
            projectToSave.setId(UUID.randomUUID());
            return projectToSave;
        });

        Project result = service.create(projectRequestDTO);

        assertNotNull(result.getId());
        assertEquals(projectRequestDTO.title(), result.getTitle());
        assertEquals(projectRequestDTO.description(), result.getDescription());
        verify(repository).save(any(Project.class));
    }

    @Test
    @DisplayName("Should throw exception when data is not valid")
    void createProjectFail() {
        ProjectRequestDTO projectRequestDTO = new ProjectRequestDTO(
                "",
                ""
        );

        Exception thrown = assertThrows(
                RuntimeException.class,
                () -> service.create(projectRequestDTO)
        );

        assertEquals("Invalid data", thrown.getMessage());
        verify(repository, never()).save(any(Project.class));
    }

    @Test
    @DisplayName("Should retrieve projects successfully")
    void retrievesSuccess() {
        int page = 0;
        int size = 10;
        List<Project> projects = Arrays.asList(
                createProject("Project 1", "Description 1"),
                createProject("Project 2", "Description 2")
        );

        Page<Project> projectPage = new PageImpl<>(projects);
        when(repository.findAll(any(Pageable.class))).thenReturn(projectPage);

        List<Project> result = service.retrieves(page, size);

        assertEquals(2, result.size());
        assertEquals("Project 1", result.get(0).getTitle());
        assertEquals("Project 2", result.get(1).getTitle());
        verify(repository).findAll(PageRequest.of(page, size));
    }

    @Test
    @DisplayName("Should retrieve project successfully when id is valid")
    void retrievesOneSuccess() {
        Project project = createProject("Project 1", "Description 1");

        when(repository.findById(project.getId())).thenReturn(Optional.of(project));

        Project result = service.retrievesOne(project.getId());

        assertEquals(project.getId(), result.getId());
        assertEquals(project.getTitle(), result.getTitle());
        assertEquals(project.getDescription(), result.getDescription());
        verify(repository).findById(project.getId());
    }

    @Test
    @DisplayName("Should throw exception when id is not found for retrieve")
    void retrievesOneFail() {
        UUID nonExistentId = UUID.randomUUID();

        when(repository.findById(nonExistentId)).thenReturn(Optional.empty());

        Exception thrown = assertThrows(
                RuntimeException.class,
                () -> service.retrievesOne(nonExistentId)
        );

        assertEquals("Project not found", thrown.getMessage());
        verify(repository).findById(nonExistentId);
    }

    @Test
    @DisplayName("Should update project successfully when data is valid")
    void updateSuccess() {
        Project existingProject = createProject("Old Project", "Old Description");
        ProjectRequestDTO updateRequest = new ProjectRequestDTO(
                "Updated Project",
                "Updated Description"
        );

        when(repository.findById(existingProject.getId())).thenReturn(Optional.of(existingProject));
        when(repository.save(any(Project.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Project result = service.update(existingProject.getId(), updateRequest);

        assertEquals(existingProject.getId(), result.getId());
        assertEquals(updateRequest.title(), result.getTitle());
        assertEquals(updateRequest.description(), result.getDescription());
        verify(repository).findById(existingProject.getId());
        verify(repository).save(existingProject);
    }

    @Test
    @DisplayName("Should throw exception when id is not found for update")
    void updateFail() {
        UUID nonExistentId = UUID.randomUUID();
        ProjectRequestDTO updateRequest = new ProjectRequestDTO(
                "Updated Project",
                "Updated Description"
        );

        when(repository.findById(nonExistentId)).thenReturn(Optional.empty());

        Exception thrown = assertThrows(
                RuntimeException.class,
                () -> service.update(nonExistentId, updateRequest)
        );

        assertEquals("Project not found", thrown.getMessage());
        verify(repository).findById(nonExistentId);
        verify(repository, never()).save(any(Project.class));
    }

    @Test
    @DisplayName("Should delete project successfully when id is valid")
    void deleteSuccess() {
        Project existingProject = createProject("Project to Delete", "Description");

        when(repository.findById(existingProject.getId())).thenReturn(Optional.of(existingProject));
        doNothing().when(repository).delete(any(Project.class));

        Boolean result = service.delete(existingProject.getId());

        assertTrue(result);
        verify(repository).findById(existingProject.getId());
        verify(repository).delete(existingProject);
    }

    @Test
    @DisplayName("Should throw exception when id is not found for delete")
    void deleteFail() {
        UUID nonExistentId = UUID.randomUUID();

        when(repository.findById(nonExistentId)).thenReturn(Optional.empty());

        Exception thrown = assertThrows(
                RuntimeException.class,
                () -> service.delete(nonExistentId)
        );

        assertEquals("Project not found", thrown.getMessage());
        verify(repository).findById(nonExistentId);
        verify(repository, never()).delete(any(Project.class));
    }

    private Project createProject(String title, String description) {
        Project project = new Project();
        project.setId(UUID.randomUUID());
        project.setTitle(title);
        project.setDescription(description);
        return project;
    }
}
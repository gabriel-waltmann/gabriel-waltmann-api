package com.waltmann.waltmann_api.service.project.tech;

import com.waltmann.waltmann_api.domain.project.Project;
import com.waltmann.waltmann_api.domain.project.tech.ProjectTech;
import com.waltmann.waltmann_api.domain.tech.Tech;
import com.waltmann.waltmann_api.repositories.project.ProjectRepository;
import com.waltmann.waltmann_api.repositories.project.tech.ProjectTechRepository;
import com.waltmann.waltmann_api.repositories.tech.TechRepository;
import com.waltmann.waltmann_api.service.tech.TechService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProjectTechServiceTest {
    @Mock
    private ProjectTechRepository repository;

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private TechRepository techRepository;

    @Mock
    private TechService techService;

    @InjectMocks
    private ProjectTechService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should create project tech successfully when project and tech exist")
    void createSuccess() {
        UUID projectId = UUID.randomUUID();
        UUID techId = UUID.randomUUID();
        Project project = createProject("Test Project", "Test Description");
        Tech tech = createTech("Java");

        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));
        when(techRepository.findById(techId)).thenReturn(Optional.of(tech));
        when(repository.save(any(ProjectTech.class))).thenAnswer(invocation -> {
            ProjectTech projectTechToSave = invocation.getArgument(0);
            projectTechToSave.setId(UUID.randomUUID());
            return projectTechToSave;
        });

        ProjectTech result = service.create(techId, projectId);

        assertNotNull(result.getId());
        assertEquals(project, result.getProject());
        assertEquals(tech, result.getTech());
        verify(projectRepository).findById(projectId);
        verify(techRepository).findById(techId);
        verify(repository).save(any(ProjectTech.class));
    }

    @Test
    @DisplayName("Should throw exception when project is not found for create")
    void createFailProjectNotFound() {
        UUID projectId = UUID.randomUUID();
        UUID techId = UUID.randomUUID();

        when(projectRepository.findById(projectId)).thenReturn(Optional.empty());

        Exception thrown = assertThrows(
                RuntimeException.class,
                () -> service.create(techId, projectId)
        );

        assertEquals("Project not found", thrown.getMessage());
        verify(projectRepository).findById(projectId);
        verify(techRepository, never()).findById(any(UUID.class));
        verify(repository, never()).save(any(ProjectTech.class));
    }

    @Test
    @DisplayName("Should throw exception when tech is not found for create")
    void createFailTechNotFound() {
        UUID projectId = UUID.randomUUID();
        UUID techId = UUID.randomUUID();
        Project project = createProject("Test Project", "Test Description");

        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));
        when(techRepository.findById(techId)).thenReturn(Optional.empty());

        Exception thrown = assertThrows(
                RuntimeException.class,
                () -> service.create(techId, projectId)
        );

        assertEquals("Tech not found", thrown.getMessage());
        verify(projectRepository).findById(projectId);
        verify(techRepository).findById(techId);
        verify(repository, never()).save(any(ProjectTech.class));
    }

    @Test
    @DisplayName("Should retrieve project techs successfully when project exists")
    void retrievesSuccess() {
        UUID projectId = UUID.randomUUID();
        Project project = createProject("Test Project", "Test Description");
        Tech tech1 = createTech("Java");
        Tech tech2 = createTech("Spring");
        List<ProjectTech> projectTechs = Arrays.asList(
                createProjectTech(project, tech1),
                createProjectTech(project, tech2)
        );

        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));
        when(repository.findByProjectId(projectId)).thenReturn(projectTechs);

        List<ProjectTech> result = service.retrieves(projectId);

        assertEquals(2, result.size());
        assertEquals("Java", result.get(0).getTech().getName());
        assertEquals("Spring", result.get(1).getTech().getName());
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
    @DisplayName("Should delete project tech successfully when it exists")
    void deleteSuccess() {
        UUID projectTechId = UUID.randomUUID();

        doNothing().when(repository).deleteById(projectTechId);

        Boolean result = service.delete(projectTechId);

        assertTrue(result);
        verify(repository).deleteById(projectTechId);
    }

    private Project createProject(String title, String description) {
        Project project = new Project();
        project.setId(UUID.randomUUID());
        project.setTitle(title);
        project.setDescription(description);
        return project;
    }

    private Tech createTech(String name) {
        Tech tech = new Tech();
        tech.setId(UUID.randomUUID());
        tech.setName(name);
        return tech;
    }

    private ProjectTech createProjectTech(Project project, Tech tech) {
        ProjectTech projectTech = new ProjectTech();
        projectTech.setId(UUID.randomUUID());
        projectTech.setProject(project);
        projectTech.setTech(tech);
        return projectTech;
    }
}
package com.waltmann.waltmann_api.repositories.project.tech;

import com.waltmann.waltmann_api.domain.project.Project;
import com.waltmann.waltmann_api.domain.project.tech.ProjectTech;
import com.waltmann.waltmann_api.domain.tech.Tech;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class ProjectTechRepositoryTest {

  @Autowired
  EntityManager entityManager;

  @Autowired
  private ProjectTechRepository repository;

  @Test
  @DisplayName("Should get project techs by project id")
  void findByProjectIdSuccess() {
    Project project = createProject("Project 1", "Description 1");

    Tech tech1 = createTech("Tech 1");
    Tech tech2 = createTech("Tech 2");

    createProjectTech(project, tech1);
    createProjectTech(project, tech2);

    assertEquals(2, repository.findByProjectId(project.getId()).size());
  }

  @Test
  @DisplayName("Should get empty list when project not found")
  void findByProjectIdNotFound() {
    List<ProjectTech> projectTechs = repository.findByProjectId(UUID.randomUUID());

    assertThat(projectTechs.isEmpty()).isTrue();
  }

  private Project createProject(String projectTitle, String projectDescription) {
    Project project = new Project();
    project.setTitle(projectTitle);
    project.setDescription(projectDescription);
    entityManager.persist(project);

    return project;
  }

  private Tech createTech(String name) {
    Tech tech = new Tech();
    tech.setName(name);
     entityManager.persist(tech);

    return tech;
  }

  private ProjectTech createProjectTech(Project project, Tech tech) {
    ProjectTech projectTech = new ProjectTech();
    projectTech.setProject(project);
    projectTech.setTech(tech);
    entityManager.persist(projectTech);

    return projectTech;
  }
}
package com.waltmann.waltmann_api.controller.project.tech;

import com.waltmann.waltmann_api.domain.project.tech.ProjectTech;
import com.waltmann.waltmann_api.service.project.tech.ProjectTechService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/project/{projectId}/tech")
public class ProjectTechController {
  @Autowired
  private ProjectTechService projectService;

  @PostMapping
  private ResponseEntity<ProjectTech> create(
      @PathVariable UUID projectId,
      @RequestParam UUID techId
  ) {
    ProjectTech projectTech = projectService.create(projectId, techId);

    return ResponseEntity.ok(projectTech);
  }

  @GetMapping
  private ResponseEntity<List<ProjectTech>> retrieves(
      @PathVariable UUID projectId
  ) {
    List<ProjectTech> projectTechs = projectService.retrieves(projectId);

    return ResponseEntity.ok(projectTechs);
  }

  @GetMapping("/{id}")
  private ResponseEntity<ProjectTech> retrievesOne(
      @PathVariable UUID projectId,
      @PathVariable UUID id
  ) {
    ProjectTech projectTech = projectService.retrievesOne(id, projectId);

    return ResponseEntity.ok(projectTech);
  }

  @DeleteMapping("/{id}")
  private ResponseEntity<Boolean> delete(
      @PathVariable UUID projectId,
      @PathVariable UUID id
  ) {
    projectService.deleteOne(id, projectId);

    return ResponseEntity.ok(true);
  }
}

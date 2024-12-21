package com.waltmann.waltmann_api.controller.project;

import com.waltmann.waltmann_api.domain.project.Project;
import com.waltmann.waltmann_api.domain.project.ProjectRequestDTO;
import com.waltmann.waltmann_api.service.project.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/project")
public class ProjectController {
  @Autowired
  private ProjectService projectService;

  @PostMapping
  public ResponseEntity<Project> create(@RequestBody ProjectRequestDTO body) {
    Project project = this.projectService.create(body);

    return ResponseEntity.ok(project);
  }

  @GetMapping
  public ResponseEntity<List<Project>> retrieves(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size
  ) {
    List<Project> projects = this.projectService.retrieves(page, size);

    return ResponseEntity.ok(projects);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Project> retrievesOne(@PathVariable UUID id) {
    Project project = this.projectService.retrievesOne(id);

    return ResponseEntity.ok(project);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Project> update(
      @PathVariable UUID id,
      @RequestBody ProjectRequestDTO body
  ) {
    Project project = this.projectService.update(id, body);

    return ResponseEntity.ok(project);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Boolean> delete(@PathVariable UUID id) {
    this.projectService.delete(id);

    return ResponseEntity.ok().build();
  }
}

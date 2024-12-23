package com.waltmann.waltmann_api.controller.project.file;

import com.waltmann.waltmann_api.domain.project.file.ProjectFile;
import com.waltmann.waltmann_api.service.project.file.ProjectFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/project/{projectId}/file")
public class ProjectFileController {
  @Autowired
  private ProjectFileService projectFileService;

  @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  private ResponseEntity<ProjectFile> create(
      @RequestParam MultipartFile file,
      @PathVariable UUID projectId
  ) {
    ProjectFile projectFile = projectFileService.create(projectId, file);

    return ResponseEntity.ok(projectFile);
  }

  @DeleteMapping("/{id}")
  private ResponseEntity<Boolean> delete(
      @PathVariable UUID projectId,
      @PathVariable UUID id
  ) {
    projectFileService.deleteOne(id, projectId);

    return ResponseEntity.ok(true);
  }

  @GetMapping
  private ResponseEntity<List<ProjectFile>> retrieves(
      @PathVariable UUID projectId
  ) {
    List<ProjectFile> projectFiles = projectFileService.retrieves(projectId);

    return ResponseEntity.ok(projectFiles);
  }

  @GetMapping("/{id}")
  private ResponseEntity<ProjectFile> retrievesOne(
      @PathVariable UUID projectId,
      @PathVariable UUID id
  ) {
    ProjectFile projectFile = projectFileService.retrievesOne(id, projectId);

    return ResponseEntity.ok(projectFile);
  }
}

package com.waltmann.waltmann_api.controller.project;

import com.waltmann.waltmann_api.domain.project.tech.ProjectTech;
import com.waltmann.waltmann_api.service.project.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/project/{id}/tech")
public class ProjectTechController {
//  @Autowired
//  private ProjectTechService projectService;

//  @PostMapping
//  private ResponseEntity<ProjectTech> create(
//      @PathVariable UUID projectId,
//      @RequestParam UUID techId
//  ) {
//    ProjectTech projectTech = projectTec
//  }
}

package com.waltmann.waltmann_api.controller.user.project;

import com.waltmann.waltmann_api.domain.user.project.UserProject;
import com.waltmann.waltmann_api.service.user.project.UserProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/user/{userId}/project")
public class UserProjectController {
    @Autowired
    private UserProjectService service;

    @PostMapping
    public UserProject create(
            @PathVariable UUID userId,
            @RequestParam UUID project_id
            ) {
        return service.create(userId, project_id);
    }

    @DeleteMapping("/{userProjectId}")
    public void delete(
            @PathVariable UUID userId,
            @PathVariable UUID userProjectId
    ) {
        service.delete(userId, userProjectId);
    }
}

package com.waltmann.waltmann_api.controller.publics;

import com.waltmann.waltmann_api.domain.user.User;
import com.waltmann.waltmann_api.domain.user.project.UserProject;
import com.waltmann.waltmann_api.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/public/user/{userId}")
public class PublicUserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<User> retrievesOne(@PathVariable UUID userId) {
        User user = userService.retrievesOne(userId);

        return ResponseEntity.ok(user);
    }

    @GetMapping("/project")
    public ResponseEntity<List<UserProject>> retrievesProjects(@PathVariable UUID userId) {
        List<UserProject> userProjects = userService.retrievesProjects(userId);

        return ResponseEntity.ok(userProjects);
    }

}

package com.waltmann.waltmann_api.controller.user.tech;

import com.waltmann.waltmann_api.domain.user.tech.UserTech;
import com.waltmann.waltmann_api.service.user.tech.UserTechService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/user/{userId}/tech")
public class UserTechController {
    @Autowired
    private UserTechService service;

    @PostMapping
    public UserTech create(
            @PathVariable UUID userId,
            @RequestParam UUID tech_id
            ) {
        return service.create(userId, tech_id);
    }

    @DeleteMapping("/{userTechId}")
    public void delete(
            @PathVariable UUID userId,
            @PathVariable UUID userTechId
    ) {
        service.delete(userId, userTechId);
    }
}

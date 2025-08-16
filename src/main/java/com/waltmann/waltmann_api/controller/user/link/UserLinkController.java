package com.waltmann.waltmann_api.controller.user.link;

import com.waltmann.waltmann_api.domain.user.link.UserLink;
import com.waltmann.waltmann_api.service.user.link.UserLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/user/{userId}/link")
public class UserLinkController {
    @Autowired
    private UserLinkService service;

    @PostMapping
    public UserLink create(
        @PathVariable UUID userId,
        @RequestParam String name,
        @RequestParam String link
    ) {
        return service.create(userId, name, link);
    }

    @DeleteMapping("/{userLinkId}")
    public void delete(
            @PathVariable UUID userId,
            @PathVariable UUID userLinkId
    ) {
        service.delete(userId, userLinkId);
    }
}

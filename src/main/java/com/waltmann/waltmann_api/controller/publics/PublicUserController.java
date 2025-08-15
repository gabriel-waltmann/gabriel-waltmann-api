package com.waltmann.waltmann_api.controller.publics;

import com.waltmann.waltmann_api.domain.user.User;
import com.waltmann.waltmann_api.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/public/user")
public class PublicUserController {
    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<User> retrievesOne(@PathVariable UUID id) {
        User user = userService.retrievesOne(id);

        return ResponseEntity.ok(user);
    }

}

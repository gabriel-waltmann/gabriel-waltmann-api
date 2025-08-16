package com.waltmann.waltmann_api.controller.user;

import com.waltmann.waltmann_api.domain.user.User;
import com.waltmann.waltmann_api.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService service;

    @PutMapping("/{id}")
    public ResponseEntity<User> update(
        @PathVariable UUID id,
        @RequestParam String name,
        @RequestParam String email,
        @RequestParam String phone_number,
        @RequestParam String title,
        @RequestParam String about,
        @RequestParam MultipartFile profile_file
    ) {
        User user = service.update(id, name, email, phone_number, title, about, profile_file);

        return ResponseEntity.ok(user);
    }
}

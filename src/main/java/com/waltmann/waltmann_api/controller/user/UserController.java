package com.waltmann.waltmann_api.controller.user;

import com.waltmann.waltmann_api.domain.user.User;
import com.waltmann.waltmann_api.domain.user.UserRequestDTO;
import com.waltmann.waltmann_api.service.user.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> register(@Valid @RequestBody UserRequestDTO body) {
        User user = this.userService.create(body);

        return ResponseEntity.ok(user);
    }
}

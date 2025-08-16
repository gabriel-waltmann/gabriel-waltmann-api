package com.waltmann.waltmann_api.controller.auth;

import com.waltmann.waltmann_api.domain.user.User;
import com.waltmann.waltmann_api.domain.user.UserLoginDTO;
import com.waltmann.waltmann_api.domain.user.UserLoginResponse;
import com.waltmann.waltmann_api.domain.user.UserRegisterDTO;
import com.waltmann.waltmann_api.helper.JwtHelper;
import com.waltmann.waltmann_api.service.auth.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<User> register(@Valid @RequestBody UserRegisterDTO body) {
        User user = this.authService.register(body);

        return ResponseEntity.ok(user);
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponse> login(@Valid @RequestBody UserLoginDTO body) {
        String email = body.email();
        String password = body.password();

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        String token = JwtHelper.generateToken(body.email());
        return ResponseEntity.ok(new UserLoginResponse(email, token));
    }
}

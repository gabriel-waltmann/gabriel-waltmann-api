package com.waltmann.waltmann_api.service.user;

import com.waltmann.waltmann_api.domain.user.User;
import com.waltmann.waltmann_api.domain.user.UserRequestDTO;
import com.waltmann.waltmann_api.exceptions.DuplicateException;
import com.waltmann.waltmann_api.repositories.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public User create(UserRequestDTO data) {
        String name = data.name();
        String email = data.email();
        String password = data.password();

        Optional<User> existingUser = repository.findByEmail(email);

        if (existingUser.isPresent()) {
            throw new DuplicateException(email);
        }

        String hashedPassword = passwordEncoder.encode(password);

        User user = new User();

        user.setName(name);

        user.setEmail(email);

        user.setPassword(hashedPassword);

        repository.save(user);

        return user;
    }
}

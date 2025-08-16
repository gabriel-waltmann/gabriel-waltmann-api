package com.waltmann.waltmann_api.service.user;

import com.waltmann.waltmann_api.domain.user.User;
import com.waltmann.waltmann_api.repositories.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;


    public User retrievesOne(UUID id) {
        return repository.findById(id).orElse(null);
    }

}

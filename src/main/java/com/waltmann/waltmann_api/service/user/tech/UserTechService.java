package com.waltmann.waltmann_api.service.user.tech;

import com.waltmann.waltmann_api.domain.project.Project;
import com.waltmann.waltmann_api.domain.tech.Tech;
import com.waltmann.waltmann_api.domain.user.User;
import com.waltmann.waltmann_api.domain.user.tech.UserTech;
import com.waltmann.waltmann_api.repositories.tech.TechRepository;
import com.waltmann.waltmann_api.repositories.user.UserRepository;
import com.waltmann.waltmann_api.repositories.user.tech.UserTechRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserTechService {
    @Autowired
    private UserTechRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TechRepository techRepository;

    public UserTech create(UUID userId, UUID techId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Tech project = techRepository.findById(techId)
                .orElseThrow(() -> new RuntimeException("Tech not found"));

        UserTech userProject = new UserTech();

        userProject.setUser(user);

        userProject.setProject(project);

        repository.save(userProject);

        return userProject;
    }

    public List<UserTech> retrieves(UUID userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return repository.findByUserId(userId);
    }

    public UserTech retrievesOne(UUID userId, UUID userTechId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return repository.findById(userTechId)
                .orElseThrow(() -> new RuntimeException("User tech not found"));
    }

    public void delete(UUID userId, UUID userTechId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        repository.findById(userTechId)
                .orElseThrow(() -> new RuntimeException("User tech not found"));

        repository.deleteById(userTechId);
    }

}

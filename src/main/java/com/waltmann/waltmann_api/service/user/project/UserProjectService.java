package com.waltmann.waltmann_api.service.user.project;

import com.waltmann.waltmann_api.domain.project.Project;
import com.waltmann.waltmann_api.domain.user.User;
import com.waltmann.waltmann_api.domain.user.project.UserProject;
import com.waltmann.waltmann_api.repositories.project.ProjectRepository;
import com.waltmann.waltmann_api.repositories.user.UserRepository;
import com.waltmann.waltmann_api.repositories.user.project.UserProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserProjectService {
    @Autowired
    private UserProjectRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectRepository projectRepository;

    public UserProject create(UUID userId, UUID projectId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        UserProject userProject = new UserProject();

        userProject.setUser(user);

        userProject.setProject(project);

        repository.save(userProject);

        System.out.println("Return user project");

        return userProject;
    }

    public List<UserProject> retrieves(UUID userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return repository.findByUserId(userId);
    }

    public UserProject retrievesOne(UUID userId, UUID userProjectId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return  repository.findById(userProjectId)
                .orElseThrow(() -> new RuntimeException("User project not found"));
    }

    public void delete(UUID userId, UUID userProjectId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        repository.findById(userProjectId)
                .orElseThrow(() -> new RuntimeException("User project not found"));

        repository.deleteById(userProjectId);
    }

}

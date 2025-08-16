package com.waltmann.waltmann_api.service.user;

import com.waltmann.waltmann_api.domain.file.File;
import com.waltmann.waltmann_api.domain.user.User;
import com.waltmann.waltmann_api.domain.user.link.UserLink;
import com.waltmann.waltmann_api.domain.user.project.UserProject;
import com.waltmann.waltmann_api.domain.user.tech.UserTech;
import com.waltmann.waltmann_api.repositories.user.UserRepository;
import com.waltmann.waltmann_api.repositories.user.link.UserLinkRepository;
import com.waltmann.waltmann_api.repositories.user.project.UserProjectRepository;
import com.waltmann.waltmann_api.repositories.user.tech.UserTechRepository;
import com.waltmann.waltmann_api.service.file.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    @Autowired
    private UserProjectRepository  userProjectRepository;

    @Autowired
    private UserTechRepository  userTechRepository;

    @Autowired
    private UserLinkRepository userLinkRepository;

    @Autowired
    FileService fileService;

    public User retrievesOne(UUID userId) {
        return repository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public List<UserProject> retrievesProjects(UUID userId) {
        repository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return userProjectRepository.findByUserId(userId);
    }

    public List<UserTech>  retrievesTechs(UUID userId) {
        repository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return userTechRepository.findByUserId(userId);
    }

    public List<UserLink>  retrievesLinks(UUID userId) {
        repository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return userLinkRepository.findByUserId(userId);
    }

    public User update(
        UUID id,
        String name,
        String email,
        String phone_number,
        String title,
        String about,
        MultipartFile profileFile
    ) {
        User user = retrievesOne(id);

        if (user == null) {
            throw new RuntimeException("User not found");
        }

        File oldFile = user.getProfileFile();
        if (oldFile != null) {
            fileService.delete(oldFile.getId());
        }

        File file = fileService.create(profileFile);

        user.setName(name);
        user.setEmail(email);
        user.setPhone_number(phone_number);
        user.setTitle(title);
        user.setAbout(about);
        user.setProfileFile(file);

        repository.save(user);

        return user;
    }

}

package com.waltmann.waltmann_api.service.user;

import com.waltmann.waltmann_api.domain.file.File;
import com.waltmann.waltmann_api.domain.user.User;
import com.waltmann.waltmann_api.repositories.user.UserRepository;
import com.waltmann.waltmann_api.service.file.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    @Autowired
    FileService fileService;

    public User retrievesOne(UUID id) {
        return repository.findById(id).orElse(null);
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

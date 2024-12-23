package com.waltmann.waltmann_api.service.tech;

import com.waltmann.waltmann_api.domain.file.File;
import com.waltmann.waltmann_api.domain.link.Link;
import com.waltmann.waltmann_api.domain.tech.Tech;
import com.waltmann.waltmann_api.repositories.tech.TechRepository;
import com.waltmann.waltmann_api.service.file.FileService;
import com.waltmann.waltmann_api.service.link.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Service
public class TechService {
    @Autowired
    private TechRepository repository;

    @Autowired
    private FileService fileService;

    @Autowired
    private LinkService linkService;

    public List<Tech> retrieves() {
        return repository.findAll();
    }

    public Tech retrievesOne(UUID id) {
        return repository.findById(id).orElse(null);
    }

    public Tech create(String name, String linkName, String linkKey, MultipartFile multipartFile) {
        Link link = linkService.create(linkName, linkKey);
        File file = fileService.create(multipartFile);

        Tech tech = new Tech();
        tech.setName(name);
        tech.setLink(link);
        tech.setFile(file);

        repository.save(tech);

        return tech;
    }

    public Tech update(UUID id, String name, String linkName, String linkKey, MultipartFile multipartFile) {
        Tech tech = retrievesOne(id);

        if (tech == null) {
            throw new RuntimeException("Tech not found");
        }

        linkService.delete(tech.getLink().getId());
        fileService.delete(tech.getFile().getId());

        Link link = linkService.create(linkName, linkKey);
        File file = fileService.create(multipartFile);

        tech.setName(name);
        tech.setLink(link);
        tech.setFile(file);

        repository.save(tech);

        return tech;
    }

    public Boolean delete(UUID id) {
        Tech tech = retrievesOne(id);

        if (tech == null) {
            throw new RuntimeException("Tech not found");
        }

        linkService.delete(tech.getLink().getId());

        fileService.delete(tech.getFile().getId());

        repository.deleteById(id);

        return true;
    }
}

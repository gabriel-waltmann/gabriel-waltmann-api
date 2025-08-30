package com.waltmann.waltmann_api.repositories.tech;

import com.waltmann.waltmann_api.domain.file.File;
import com.waltmann.waltmann_api.domain.link.Link;
import com.waltmann.waltmann_api.domain.tech.Tech;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
public class TechRepositoryTest {
    @Autowired
    TechRepository repository;

    @Autowired
    EntityManager entityManager;

    @Test
    @DisplayName("Should get tech by id")
    void getById() {
        Link link = new Link();
        link.setName("test");
        link.setKey("test.com");

        File file = new File();
        file.setName("test");

        Tech tech = new Tech();
        tech.setName("tech");
        tech.setLink(link);
        tech.setFile(file);

        repository.save(tech);

        entityManager.persist(tech);

        Optional<Tech> optional = repository.findById(tech.getId());
        assertTrue(optional.isPresent());
    }
}
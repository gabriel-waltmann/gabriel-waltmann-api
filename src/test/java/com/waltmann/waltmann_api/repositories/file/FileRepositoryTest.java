package com.waltmann.waltmann_api.repositories.file;

import com.waltmann.waltmann_api.domain.file.File;
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
public class FileRepositoryTest {
    @Autowired
    FileRepository repository;

    @Autowired
    EntityManager entityManager;

    @Test
    @DisplayName("Should get file by id")
    void findById() {
        File file = new File();
        file.setName("test");

        entityManager.persist(file);

        repository.save(file);

        Optional<File> optional = repository.findById(file.getId());
        assertTrue(optional.isPresent());
        assertEquals(file.getName(), optional.get().getName());
    }
}

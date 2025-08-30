package com.waltmann.waltmann_api.repositories.link;

import com.waltmann.waltmann_api.domain.link.Link;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@ActiveProfiles("test")
public class LinkRepositoryTest {
    @Autowired
    LinkRepository repository;

    @Autowired
    EntityManager entityManager;

    @Test
    @DisplayName("Should get link by id")
    void getById() {
        Link link = new Link();
        link.setName("test");
        link.setKey("test.com");

        entityManager.persist(link);

        repository.save(link);

        Optional<Link> optional = repository.findById(link.getId());
        assertTrue(optional.isPresent());
        assertEquals(optional.get().getName(), link.getName());
        assertEquals(optional.get().getKey(), link.getKey());

    }
}

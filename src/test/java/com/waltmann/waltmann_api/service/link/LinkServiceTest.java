package com.waltmann.waltmann_api.service.link;

import com.waltmann.waltmann_api.domain.link.Link;
import com.waltmann.waltmann_api.repositories.link.LinkRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class LinkServiceTest {
    @Mock
    private LinkRepository repository;

    @InjectMocks
    private LinkService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should create link when data is valid")
    void createSuccess() {
        String name = "Test Link";
        String key = "https://example.com";

        // Mock repository save to return link with ID set
        when(repository.save(any(Link.class))).thenAnswer(invocation -> {
            Link linkToSave = invocation.getArgument(0);
            linkToSave.setId(UUID.randomUUID());
            return linkToSave;
        });

        Link result = service.create(name, key);

        assertNotNull(result.getId());
        assertEquals(name, result.getName());
        assertEquals(key, result.getKey());
        verify(repository).save(any(Link.class));
    }

    @Test
    @DisplayName("Should retrieve paginated links when page and size are valid")
    void retrievesSuccess() {
        int page = 0;
        int size = 10;
        List<Link> links = Arrays.asList(
                createLink("Link 1", "https://example1.com"),
                createLink("Link 2", "https://example2.com")
        );

        Page<Link> linkPage = new PageImpl<>(links);
        when(repository.findAll(any(Pageable.class))).thenReturn(linkPage);

        List<Link> result = service.retrieves(page, size);

        assertEquals(2, result.size());
        assertEquals("Link 1", result.get(0).getName());
        assertEquals("Link 2", result.get(1).getName());
        verify(repository).findAll(PageRequest.of(page, size));
    }

    @Test
    @DisplayName("Should retrieve one link when link id is valid")
    void retrievesOneSuccess() {
        Link link = createLink("Test Link", "https://example.com");

        when(repository.findById(link.getId())).thenReturn(Optional.of(link));

        Link result = service.retrievesOne(link.getId());

        assertEquals(link.getId(), result.getId());
        assertEquals(link.getName(), result.getName());
        assertEquals(link.getKey(), result.getKey());
        verify(repository).findById(link.getId());
    }

    @Test
    @DisplayName("Should throw exception when link id is not found for retrieve")
    void retrievesOneFail() {
        UUID nonExistentId = UUID.randomUUID();

        when(repository.findById(nonExistentId)).thenReturn(Optional.empty());

        Exception result = assertThrows(
                RuntimeException.class,
                () -> service.retrievesOne(nonExistentId)
        );

        assertTrue(result.getMessage().contains("No value present"));
        verify(repository).findById(nonExistentId);
    }

    @Test
    @DisplayName("Should update link when link id is valid and data is provided")
    void updateSuccess() {
        Link existingLink = createLink("Old Link", "https://old-example.com");
        String newName = "Updated Link";
        String newKey = "https://updated-example.com";

        when(repository.findById(existingLink.getId())).thenReturn(Optional.of(existingLink));
        when(repository.save(any(Link.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Link result = service.update(existingLink.getId(), newName, newKey);

        assertEquals(existingLink.getId(), result.getId());
        assertEquals(newName, result.getName());
        assertEquals(newKey, result.getKey());
        verify(repository).findById(existingLink.getId());
        verify(repository).save(existingLink);
    }

    @Test
    @DisplayName("Should throw exception when link id is not found for update")
    void updateFail() {
        UUID nonExistentId = UUID.randomUUID();

        when(repository.findById(nonExistentId)).thenReturn(Optional.empty());

        Exception result = assertThrows(
                RuntimeException.class,
                () -> service.update(nonExistentId, "New Name", "https://new-example.com")
        );

        assertTrue(result.getMessage().contains("No value present"));
        verify(repository).findById(nonExistentId);
        verify(repository, never()).save(any(Link.class));
    }

    @Test
    @DisplayName("Should delete link when link id is valid")
    void deleteSuccess() {
        UUID linkId = UUID.randomUUID();

        doNothing().when(repository).deleteById(linkId);

        Boolean result = service.delete(linkId);

        assertTrue(result);
        verify(repository).deleteById(linkId);
    }

    @Test
    @DisplayName("Should return true even when deleting non-existent link")
    void deleteNonExistentLink() {
        UUID nonExistentId = UUID.randomUUID();

        doNothing().when(repository).deleteById(nonExistentId);

        Boolean result = service.delete(nonExistentId);

        assertTrue(result);
        verify(repository).deleteById(nonExistentId);
    }

    private Link createLink(String name, String key) {
        Link link = new Link();
        link.setId(UUID.randomUUID());
        link.setName(name);
        link.setKey(key);
        return link;
    }
}

package com.waltmann.waltmann_api.service.tech;

import com.waltmann.waltmann_api.domain.file.File;
import com.waltmann.waltmann_api.domain.link.Link;
import com.waltmann.waltmann_api.domain.tech.Tech;
import com.waltmann.waltmann_api.repositories.tech.TechRepository;
import com.waltmann.waltmann_api.service.file.FileService;
import com.waltmann.waltmann_api.service.link.LinkService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TechServiceTest {
    @Mock
    private TechRepository repository;

    @Mock
    private FileService fileService;

    @Mock
    private LinkService linkService;

    @InjectMocks
    private TechService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should create tech when data is valid")
    void createSuccess() {
        String name = "Java";
        String linkName = "Java Official";
        String linkKey = "https://java.com";
        MultipartFile multipartFile = createMultipartFile();

        Link link = createLink("Java Official", "https://java.com");
        File file = createFile("java-logo.png", "java-logo-key");

        when(linkService.create(linkName, linkKey)).thenReturn(link);
        when(fileService.create(multipartFile)).thenReturn(file);
        when(repository.save(any(Tech.class))).thenAnswer(invocation -> {
            Tech techToSave = invocation.getArgument(0);
            techToSave.setId(UUID.randomUUID());
            return techToSave;
        });

        Tech result = service.create(name, linkName, linkKey, multipartFile);

        assertNotNull(result.getId());
        assertEquals(name, result.getName());
        assertEquals(link, result.getLink());
        assertEquals(file, result.getFile());
        verify(linkService).create(linkName, linkKey);
        verify(fileService).create(multipartFile);
        verify(repository).save(any(Tech.class));
    }

    @Test
    @DisplayName("Should retrieve all techs successfully")
    void retrievesSuccess() {
        List<Tech> techs = Arrays.asList(
                createTech("Java", createLink("Java Official", "https://java.com"), createFile("java.png", "java-key")),
                createTech("Spring", createLink("Spring Official", "https://spring.io"), createFile("spring.png", "spring-key"))
        );

        when(repository.findAll()).thenReturn(techs);

        List<Tech> result = service.retrieves();

        assertEquals(2, result.size());
        assertEquals("Java", result.get(0).getName());
        assertEquals("Spring", result.get(1).getName());
        verify(repository).findAll();
    }

    @Test
    @DisplayName("Should retrieve one tech when tech id is valid")
    void retrievesOneSuccess() {
        Tech tech = createTech("Java", createLink("Java Official", "https://java.com"), createFile("java.png", "java-key"));

        when(repository.findById(tech.getId())).thenReturn(Optional.of(tech));

        Tech result = service.retrievesOne(tech.getId());

        assertEquals(tech.getId(), result.getId());
        assertEquals(tech.getName(), result.getName());
        assertEquals(tech.getLink(), result.getLink());
        assertEquals(tech.getFile(), result.getFile());
        verify(repository).findById(tech.getId());
    }

    @Test
    @DisplayName("Should return null when tech id is not found for retrieve")
    void retrievesOneNotFound() {
        UUID nonExistentId = UUID.randomUUID();

        when(repository.findById(nonExistentId)).thenReturn(Optional.empty());

        Tech result = service.retrievesOne(nonExistentId);

        assertNull(result);
        verify(repository).findById(nonExistentId);
    }

    @Test
    @DisplayName("Should update tech when tech id is valid and data is provided")
    void updateSuccess() {
        // Create existing tech with known IDs
        Link existingLink = createLink("Old Link", "https://old.com");
        File existingFile = createFile("old.png", "old-key");
        Tech existingTech = createTech("Old Java", existingLink, existingFile);

        String newName = "Updated Java";
        String newLinkName = "New Java Official";
        String newLinkKey = "https://newjava.com";
        MultipartFile newFile = createMultipartFile();

        Link newLink = createLink("New Java Official", "https://newjava.com");
        File newFileObj = createFile("new-java.png", "new-java-key");

        when(repository.findById(existingTech.getId())).thenReturn(Optional.of(existingTech));
        when(linkService.delete(existingLink.getId())).thenReturn(true);
        when(fileService.delete(existingFile.getId())).thenReturn(true);
        when(linkService.create(newLinkName, newLinkKey)).thenReturn(newLink);
        when(fileService.create(newFile)).thenReturn(newFileObj);
        when(repository.save(any(Tech.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Tech result = service.update(existingTech.getId(), newName, newLinkName, newLinkKey, newFile);

        assertEquals(existingTech.getId(), result.getId());
        assertEquals(newName, result.getName());
        assertEquals(newLink, result.getLink());
        assertEquals(newFileObj, result.getFile());
        verify(repository).findById(existingTech.getId());
        verify(linkService).delete(existingLink.getId()); // Use the actual existing link ID
        verify(fileService).delete(existingFile.getId()); // Use the actual existing file ID
        verify(linkService).create(newLinkName, newLinkKey);
        verify(fileService).create(newFile);
        verify(repository).save(existingTech);
    }

    @Test
    @DisplayName("Should throw exception when tech id is not found for update")
    void updateFail() {
        UUID nonExistentId = UUID.randomUUID();
        String newName = "New Tech";
        String newLinkName = "New Link";
        String newLinkKey = "https://new.com";
        MultipartFile newFile = createMultipartFile();

        when(repository.findById(nonExistentId)).thenReturn(Optional.empty());

        Exception result = assertThrows(
                RuntimeException.class,
                () -> service.update(nonExistentId, newName, newLinkName, newLinkKey, newFile)
        );

        assertEquals("Tech not found", result.getMessage());
        verify(repository).findById(nonExistentId);
        verify(linkService, never()).delete(any(UUID.class));
        verify(fileService, never()).delete(any(UUID.class));
        verify(linkService, never()).create(anyString(), anyString());
        verify(fileService, never()).create(any(MultipartFile.class));
        verify(repository, never()).save(any(Tech.class));
    }

    @Test
    @DisplayName("Should delete tech when tech id is valid")
    void deleteSuccess() {
        Tech existingTech = createTech("Java", createLink("Java Official", "https://java.com"), createFile("java.png", "java-key"));

        when(repository.findById(existingTech.getId())).thenReturn(Optional.of(existingTech));
        when(linkService.delete(existingTech.getLink().getId())).thenReturn(true);
        when(fileService.delete(existingTech.getFile().getId())).thenReturn(true);
        doNothing().when(repository).deleteById(existingTech.getId());

        Boolean result = service.delete(existingTech.getId());

        assertTrue(result);
        verify(repository).findById(existingTech.getId());
        verify(linkService).delete(existingTech.getLink().getId());
        verify(fileService).delete(existingTech.getFile().getId());
        verify(repository).deleteById(existingTech.getId());
    }

    @Test
    @DisplayName("Should throw exception when tech id is not found for delete")
    void deleteFail() {
        UUID nonExistentId = UUID.randomUUID();

        when(repository.findById(nonExistentId)).thenReturn(Optional.empty());

        Exception result = assertThrows(
                RuntimeException.class,
                () -> service.delete(nonExistentId)
        );

        assertEquals("Tech not found", result.getMessage());
        verify(repository).findById(nonExistentId);
        verify(linkService, never()).delete(any(UUID.class));
        verify(fileService, never()).delete(any(UUID.class));
        verify(repository, never()).deleteById(any(UUID.class));
    }

    private MultipartFile createMultipartFile() {
        return new MockMultipartFile(
                "file",
                "test-tech.png",
                "image/png",
                "This is a test tech image.".getBytes()
        );
    }

    private Link createLink(String name, String key) {
        Link link = new Link();
        link.setId(UUID.randomUUID());
        link.setName(name);
        link.setKey(key);
        return link;
    }

    private File createFile(String name, String key) {
        File file = new File();
        file.setId(UUID.randomUUID());
        file.setName(name);
        file.setKey(key);
        return file;
    }

    private Tech createTech(String name, Link link, File file) {
        Tech tech = new Tech();
        tech.setId(UUID.randomUUID());
        tech.setName(name);
        tech.setLink(link);
        tech.setFile(file);
        return tech;
    }
}

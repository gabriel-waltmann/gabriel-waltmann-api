package com.waltmann.waltmann_api.service.file;

import com.waltmann.waltmann_api.domain.file.File;
import com.waltmann.waltmann_api.repositories.file.FileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Utilities;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.DeleteObjectResponse;
import software.amazon.awssdk.services.s3.model.GetUrlRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;
import software.amazon.awssdk.core.sync.RequestBody;

import java.net.URL;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FileServiceTest {
    @Mock
    FileRepository repository;

    @Mock
    S3Client s3Client;

    @Mock
    S3Utilities s3Utilities;

    @InjectMocks
    private FileService service;

    @BeforeEach
    void setUp() {
        try {
            MockitoAnnotations.openMocks(this);

            // Set the bucketName field using reflection since it's a @Value field
            java.lang.reflect.Field bucketNameField = FileService.class.getDeclaredField("bucketName");
            bucketNameField.setAccessible(true);
            bucketNameField.set(service, "test-bucket");
        } catch (Exception e) {
            // Handle reflection exception
        }
    }

    @Test
    @DisplayName("Should create file when multipart file is present")
    void createSuccess() {
        MultipartFile multipartFile = createMultipartFile();

        // Mock S3 operations properly
        when(s3Client.putObject(any(PutObjectRequest.class), any(RequestBody.class)))
                .thenReturn(PutObjectResponse.builder().build());
        when(s3Client.utilities()).thenReturn(s3Utilities);

        // Mock the URL generation to return a valid URL string
        when(s3Utilities.getUrl(any(GetUrlRequest.class)))
                .thenAnswer(invocation -> {
                    try {
                        return new URL("https://test-bucket.s3.amazonaws.com/test-file-key");
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });

        // Mock repository save to return file with ID set
        when(repository.save(any(File.class))).thenAnswer(invocation -> {
            File fileToSave = invocation.getArgument(0);
            fileToSave.setId(UUID.randomUUID());
            return fileToSave;
        });

        File result = service.create(multipartFile);

        assertNotNull(result.getId());
        assertNotNull(result.getName());
        assertNotNull(result.getKey());
    }

    @Test
    @DisplayName("Should get file when file id is valid")
    void retrievesOneSuccess() {
        MultipartFile multipartFile = createMultipartFile();
        File file = createFile(multipartFile);

        when(repository.findById(file.getId())).thenReturn(Optional.of(file));

        File result = service.retrievesOne(file.getId());

        assertEquals(file.getId(), result.getId());
    }

    @Test
    @DisplayName("Should throw not found exception when file id is not valid")
    void retrievesOneFail() {
        MultipartFile multipartFile = createMultipartFile();
        File file = createFile(multipartFile);

        Exception result = assertThrows(
                RuntimeException.class,
                () -> service.retrievesOne(file.getId())
        );

        assertEquals("File not found", result.getMessage());

    }

    @Test
    @DisplayName("Should delete file when file id is valid")
    void deleteSuccess() {
        MultipartFile multipartFile = createMultipartFile();
        File file = createFile(multipartFile);

        when(repository.findById(file.getId())).thenReturn(Optional.of(file));

        // Mock S3 delete operation
        when(s3Client.deleteObject(any(DeleteObjectRequest.class)))
                .thenReturn(DeleteObjectResponse.builder().build());

        // Mock repository delete
        doNothing().when(repository).delete(any(File.class));

        Boolean result = service.delete(file.getId());

        assertTrue(result);
    }

    @Test
    @DisplayName("Should throw not found exception when file id is valid")
    void deleteFail() {
        MultipartFile multipartFile = createMultipartFile();
        File file = createFile(multipartFile);

        Exception result = assertThrows(
                RuntimeException.class,
                () -> service.delete(file.getId())
        );

        assertEquals("File not found", result.getMessage());
    }

    private MultipartFile createMultipartFile() {
        return new MockMultipartFile(
                "file",
                "testfile.txt",
                "text/plain",
                "This is a test file.".getBytes()
        ) ;
    }

    private File createFile(MultipartFile multipartFile) {
        UUID id = UUID.randomUUID();

        String fileName = UUID.randomUUID() + "-" + multipartFile.getOriginalFilename();

        String key = multipartFile.getOriginalFilename();

        File file = new File();

        file.setName(fileName);

        file.setId(id);

        file.setKey(key);

        return file;
    }
}

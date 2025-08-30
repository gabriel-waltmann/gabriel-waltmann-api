package com.waltmann.waltmann_api.service.file;

import com.waltmann.waltmann_api.domain.file.File;
import com.waltmann.waltmann_api.repositories.file.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetUrlRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Objects;
import java.util.UUID;

@Service
public class FileService {
  @Autowired
  private FileRepository repository;

  @Autowired
  private S3Client s3Client;

  @Value("${aws.bucket.name}")
  private String bucketName;

  public File create(MultipartFile multipartFile) {
    String fileName = UUID.randomUUID() + "-" + multipartFile.getOriginalFilename();

    File file = new File();

    String fileKey = upload(fileName, multipartFile);
    
    file.setName(fileName);
    file.setKey(fileKey);
    
    return repository.save(file);
  }

  public File retrievesOne(UUID id) {
    return repository.findById(id)
        .orElseThrow(() -> new RuntimeException("File not found"));
  }

  public Boolean delete(UUID id) {
    File file = repository.findById(id)
        .orElseThrow(() -> new RuntimeException("File not found"));

    try {
      DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
          .bucket(bucketName)
          .key(file.getName())
          .build();

      s3Client.deleteObject(deleteObjectRequest);

      repository.delete(file);

      return true;
    } catch (Exception e) {
      System.out.println(e);

      return false;
    }
  }

  private String upload(String fileName, MultipartFile file) {
    try {
      java.io.File fileConv = this.convertMultipartToFile(file);

      PutObjectRequest putObjectRequest = PutObjectRequest.builder()
          .bucket(bucketName)
          .key(fileName)
          .build();

      RequestBody body = RequestBody.fromByteBuffer(ByteBuffer.wrap(file.getBytes()));

      s3Client.putObject(
          putObjectRequest,
          body
      );

      Boolean deleted = fileConv.delete();

      GetUrlRequest request = GetUrlRequest.builder()
          .bucket(bucketName)
          .key(fileName)
          .build();

      return s3Client.utilities().getUrl(request).toString();
    } catch (Exception e) {
      System.out.println(e);

      return null;
    }
  }

  private java.io.File convertMultipartToFile(MultipartFile file) throws IOException {
    String stringFile = Objects.requireNonNull(file.getOriginalFilename());
    java.io.File convFile = new java.io.File(stringFile);
    FileOutputStream fos = new FileOutputStream(convFile);
    fos.write(file.getBytes());
    fos.close();
    return convFile;
  }
}

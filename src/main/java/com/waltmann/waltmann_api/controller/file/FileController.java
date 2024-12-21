package com.waltmann.waltmann_api.controller.file;

import com.waltmann.waltmann_api.domain.file.File;
import com.waltmann.waltmann_api.service.file.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class FileController {
  @Autowired
  private FileService fileService;

  public File create(MultipartFile file) {
    return fileService.create(file);
  }
}

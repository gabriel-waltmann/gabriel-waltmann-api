package com.waltmann.waltmann_api.domain.project.file;

import org.springframework.web.multipart.MultipartFile;

public record ProjectFileRequestDTO(MultipartFile file){}

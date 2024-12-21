package com.waltmann.waltmann_api.domain.project.file;

import com.waltmann.waltmann_api.domain.file.File;

public record ProjectFileResponseDTO(
  ProjectFile projectFile,
  File file
){}

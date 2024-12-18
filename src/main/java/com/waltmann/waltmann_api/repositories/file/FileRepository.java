package com.waltmann.waltmann_api.repositories.file;

import com.waltmann.waltmann_api.domain.file.File;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FileRepository extends JpaRepository<File, UUID>  {
}

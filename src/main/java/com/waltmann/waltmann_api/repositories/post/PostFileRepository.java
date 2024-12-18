package com.waltmann.waltmann_api.repositories.post;

import com.waltmann.waltmann_api.domain.post.PostFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PostFileRepository extends JpaRepository<PostFile, UUID> {
}

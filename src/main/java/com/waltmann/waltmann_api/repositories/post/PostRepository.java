package com.waltmann.waltmann_api.repositories.post;

import com.waltmann.waltmann_api.domain.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PostRepository extends JpaRepository<Post, UUID>  {
}

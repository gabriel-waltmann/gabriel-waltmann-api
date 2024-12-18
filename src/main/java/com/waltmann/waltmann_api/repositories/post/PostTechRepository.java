package com.waltmann.waltmann_api.repositories.post;

import com.waltmann.waltmann_api.domain.post.PostTech;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PostTechRepository extends JpaRepository<PostTech, UUID> {
}

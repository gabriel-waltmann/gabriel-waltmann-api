package com.waltmann.waltmann_api.repositories.user.link;

import com.waltmann.waltmann_api.domain.user.link.UserLink;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface UserLinkRepository extends JpaRepository<UserLink, UUID> {
    List<UserLink> findByUserId(UUID userId);
}

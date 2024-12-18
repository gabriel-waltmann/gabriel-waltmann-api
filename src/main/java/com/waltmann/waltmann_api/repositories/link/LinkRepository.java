package com.waltmann.waltmann_api.repositories.link;

import com.waltmann.waltmann_api.domain.link.Link;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LinkRepository extends JpaRepository<Link, UUID>  {
}

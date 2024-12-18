package com.waltmann.waltmann_api.repositories.tech;

import com.waltmann.waltmann_api.domain.tech.Tech;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TechRepository extends JpaRepository<Tech, UUID> {
}

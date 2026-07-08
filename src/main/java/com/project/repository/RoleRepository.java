package com.project.repository;

import com.project.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface RoleRepository  extends JpaRepository<Role, Long> {
    Set<Role> findByNameIn(Set<String> name);

    boolean existsByName(String name);
}

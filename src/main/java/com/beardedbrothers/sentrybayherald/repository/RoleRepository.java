package com.beardedbrothers.sentrybayherald.repository;

import com.beardedbrothers.sentrybayherald.model.ERole;
import com.beardedbrothers.sentrybayherald.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}

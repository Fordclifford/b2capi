package com.mpesapush.repository;

import com.mpesapush.model.Roles;
import com.mpesapush.model.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Roles, Long> {
    Optional<Roles> findByName(RoleName roleName);
}
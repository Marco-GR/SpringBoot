package com.example.myFirstSpringApp.repository;

import com.example.myFirstSpringApp.model.Role;
import com.example.myFirstSpringApp.util.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName roleName);
}

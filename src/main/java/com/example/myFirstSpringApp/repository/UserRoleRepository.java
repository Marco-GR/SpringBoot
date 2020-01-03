package com.example.myFirstSpringApp.repository;

import com.example.myFirstSpringApp.model.Role;
import com.example.myFirstSpringApp.model.User;
import com.example.myFirstSpringApp.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRoleRepository extends JpaRepository <UserRole, Long> {

    List<UserRole> findByUser(User user);

    List<UserRole> findByRole(Role role);
}

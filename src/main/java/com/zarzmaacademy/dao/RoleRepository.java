package com.zarzmaacademy.dao;

import com.zarzmaacademy.model.Role;
import com.zarzmaacademy.model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

	Optional<Role> findByRoleName(Roles role);
}

package com.example.projectge.DAO;

import com.example.projectge.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Integer> {
    Role findRoleByRoleName(String role);
}

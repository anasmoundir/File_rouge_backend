package com.example.demo.repository;

import com.example.demo.model.UserRole;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository {
    UserRole findByRoleName(String teacher);
}

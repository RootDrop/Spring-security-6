package com.mapping.learn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mapping.learn.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
}

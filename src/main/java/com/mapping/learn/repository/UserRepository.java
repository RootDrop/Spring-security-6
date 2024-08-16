package com.mapping.learn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mapping.learn.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
  User findByEmail(String email);
}

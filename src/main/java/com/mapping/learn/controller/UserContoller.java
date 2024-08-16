package com.mapping.learn.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mapping.learn.request.LoginRequest;
import com.mapping.learn.response.LoginResponse;
import com.mapping.learn.service.UserService;

import jakarta.annotation.PostConstruct;

@RestController
public class UserContoller {

  @Autowired
  private UserService userService;

  @PostConstruct
  public void inItUserAndRole() {
    userService.inInUserAndPassword();
  }

  @PostMapping("/login")
  public LoginResponse login(@RequestBody LoginRequest loginRequest) {
    return userService.login(loginRequest);
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @GetMapping("/forAdmin")
  public String forAdmin() {
    return "This is for admin";
  }

  @PreAuthorize("hasAuthority('USER')")
  @GetMapping("/forUser")
  public String forUser() {
    return "This is for user";
  }

}

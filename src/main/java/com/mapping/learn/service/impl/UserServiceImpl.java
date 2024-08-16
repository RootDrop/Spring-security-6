package com.mapping.learn.service.impl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mapping.learn.model.Role;
import com.mapping.learn.model.User;
import com.mapping.learn.repository.RoleRepository;
import com.mapping.learn.repository.UserRepository;
import com.mapping.learn.request.LoginRequest;
import com.mapping.learn.response.LoginResponse;
import com.mapping.learn.service.UserService;
import com.mapping.learn.utils.JwtUtils;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

  private UserRepository userRepository;
  private RoleRepository roleRepository;
  private AuthenticationManager authenticationManager;
  private JwtUtils jwtUtils;
  private PasswordEncoder passwordEncoder;

  @Override
  public void inInUserAndPassword() {
    Role userRole = Role.builder().roleName("USER").roleDescription("This is the default role of user").build();
    roleRepository.save(userRole);

    Role adminRole = Role.builder().roleName("ADMIN").roleDescription("This is the admin role").build();
    roleRepository.save(adminRole);

    Set<Role> userRoles = new HashSet<>();
    userRoles.add(userRole);
    User user = User.builder().firstname("dhruv").lastname("vyas").email("dvyas@gmail.com")
        .password(getPasswordEncoder("Dhruv@123")).userRole(userRoles).build();
    userRepository.save(user);

    Set<Role> adminRoles = new HashSet<>();
    adminRoles.add(adminRole);
    User admin = User.builder().firstname("admin").lastname("admin").email("admin@gmail.com")
        .password(getPasswordEncoder("Admin@123")).userRole(adminRoles).build();
    userRepository.save(admin);
  }

  @Override
  public LoginResponse login(LoginRequest loginRequest) {
    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
        loginRequest.getEmail(), loginRequest.getPassword());
    LoginResponse loginResponse = new LoginResponse();
    try {
      authenticationManager.authenticate(authentication);
      String token = jwtUtils.generateToken(loginRequest.getEmail());
      User user = userRepository.findByEmail(loginRequest.getEmail());
      loginResponse.setUser(user);
      loginResponse.setToken(token);
      loginResponse.setMessage("User login successfully...!");
    } catch (BadCredentialsException e) {
      loginResponse.setMessage("Invalid email or password");
    } catch (Exception e) {
      loginResponse.setMessage("Authentication failed. Please check your credentials or contact support.");
    }
    return loginResponse;
  }

  public String getPasswordEncoder(String password) {
    return passwordEncoder.encode(password);
  }

}

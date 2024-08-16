package com.mapping.learn.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.mapping.learn.config.CustomUserDetails;
import com.mapping.learn.model.User;
import com.mapping.learn.repository.UserRepository;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

  @Autowired
  private UserRepository userRepository;

  private static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    logger.debug("Entering in loadUserByUsername Method...");
    User user = userRepository.findByEmail(username);
    if (user == null) {
      logger.error("Username not found: " + username);
      throw new UsernameNotFoundException("could not found user..!!");
    }
    logger.info("User Authenticated Successfully..!!!");
    return new CustomUserDetails(user);
  }
}

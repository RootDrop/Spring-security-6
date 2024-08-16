package com.mapping.learn.service;

import com.mapping.learn.request.LoginRequest;
import com.mapping.learn.response.LoginResponse;

public interface UserService {

  void inInUserAndPassword();

  LoginResponse login(LoginRequest loginRequest);
}

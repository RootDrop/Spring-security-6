package com.mapping.learn.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mapping.learn.model.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoginResponse {

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private User user;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private String token;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private String message;
}

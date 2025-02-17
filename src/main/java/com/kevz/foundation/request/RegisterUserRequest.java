package com.kevz.foundation.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@RequiredArgsConstructor
public class RegisterUserRequest {
  private String email;
  private String username;
  private String password;
}

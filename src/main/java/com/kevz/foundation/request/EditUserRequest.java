package com.kevz.foundation.request;

import lombok.Data;

@Data
public class EditUserRequest {
  private String username;
  private String email;
  private String password;
}

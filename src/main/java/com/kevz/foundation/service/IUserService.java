package com.kevz.foundation.service;

import com.kevz.foundation.request.EditUserRequest;
import com.kevz.foundation.request.RegisterUserRequest;
import com.kevz.foundation.request.ValidateUserRequest;
import com.kevz.foundation.model.User;
import com.kevz.foundation.model.UserRole;
import com.kevz.foundation.response.ValidateUserResponse;

public interface IUserService {
  ValidateUserResponse registerUser(RegisterUserRequest registerUserRequest);

  ValidateUserResponse validateUser(ValidateUserRequest validateUserRequest);

  ValidateUserResponse editUser(User user, EditUserRequest editUserRequest);

  User findById(Integer userId);

  void changeUserRole(Integer userId, UserRole userRole);

  boolean containsRole(UserRole userRole);

  void deleteUser(User user);
}

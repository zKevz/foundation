package com.thesis.corfundme.service;

import com.thesis.corfundme.model.User;
import com.thesis.corfundme.model.UserRole;
import com.thesis.corfundme.request.EditUserRequest;
import com.thesis.corfundme.request.RegisterUserRequest;
import com.thesis.corfundme.request.ValidateUserRequest;
import com.thesis.corfundme.response.ValidateUserResponse;

public interface IUserService {
  ValidateUserResponse registerUser(RegisterUserRequest registerUserRequest);

  ValidateUserResponse validateUser(ValidateUserRequest validateUserRequest);

  ValidateUserResponse editUser(User user, EditUserRequest editUserRequest);

  User findById(Integer userId);

  void changeUserRole(Integer userId, UserRole userRole);

  boolean containsRole(UserRole userRole);

  void deleteUser(User user);
}

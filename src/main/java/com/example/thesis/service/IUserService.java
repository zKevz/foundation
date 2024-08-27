package com.example.thesis.service;

import com.example.thesis.model.User;
import com.example.thesis.model.UserRole;
import com.example.thesis.request.RegisterUserRequest;
import com.example.thesis.request.ValidateUserRequest;
import com.example.thesis.response.ValidateUserResponse;

public interface IUserService {
  ValidateUserResponse registerUser(RegisterUserRequest registerUserRequest);
  ValidateUserResponse validateUser(ValidateUserRequest validateUserRequest);

  User findById(Integer userId);

  void changeUserRole(Integer userId, UserRole userRole);
}

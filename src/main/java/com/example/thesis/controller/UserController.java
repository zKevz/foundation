package com.example.thesis.controller;

import com.example.thesis.model.User;
import com.example.thesis.request.EditUserRequest;
import com.example.thesis.request.RegisterUserRequest;
import com.example.thesis.request.ValidateUserRequest;
import com.example.thesis.response.BaseResponse;
import com.example.thesis.response.ValidateUserResponse;
import com.example.thesis.service.IUserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

  @Autowired
  private IUserService userService;

  @PostMapping("/register")
  public BaseResponse<ValidateUserResponse> registerUser(@Valid @RequestBody RegisterUserRequest registerUserRequest) {
    try {
      return BaseResponse.ok(userService.registerUser(registerUserRequest));
    } catch (Exception e) {
      log.error("Register user failed: {}", e.getMessage(), e);
      return BaseResponse.error(e.getMessage());
    }
  }

  @PostMapping("/login")
  public BaseResponse<ValidateUserResponse> validateUser(@Valid @RequestBody ValidateUserRequest validateUserRequest) {
    try {
      return BaseResponse.ok(userService.validateUser(validateUserRequest));
    } catch (Exception e) {
      log.error("Register user failed: {}", e.getMessage(), e);
      return BaseResponse.error(e.getMessage());
    }
  }

  @PostMapping("/edit")
  public BaseResponse<ValidateUserResponse> editUser(@AuthenticationPrincipal User user, @Valid @RequestBody
  EditUserRequest editUserRequest) {
    try {
      return BaseResponse.ok(userService.editUser(user, editUserRequest));
    } catch (Exception e) {
      log.error("Register user failed: {}", e.getMessage(), e);
      return BaseResponse.error(e.getMessage());
    }
  }
}

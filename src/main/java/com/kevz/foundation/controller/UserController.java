package com.kevz.foundation.controller;

import com.kevz.foundation.request.EditUserRequest;
import com.kevz.foundation.request.RegisterUserRequest;
import com.kevz.foundation.request.ValidateUserRequest;
import com.kevz.foundation.response.BaseResponse;
import com.kevz.foundation.response.ValidateUserResponse;
import com.kevz.foundation.service.IUserService;
import com.kevz.foundation.model.User;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
      log.info("LOGIN! {} {}", validateUserRequest.getEmail(), validateUserRequest.getPassword());
      return BaseResponse.ok(userService.validateUser(validateUserRequest));
    } catch (Exception e) {
      log.error("Register user failed: {}", e.getMessage(), e);
      return BaseResponse.error(e.getMessage());
    }
  }

  @PutMapping("/edit")
  public BaseResponse<ValidateUserResponse> editUser(@AuthenticationPrincipal User user,
    @Valid @RequestBody EditUserRequest editUserRequest) {
    try {
      return BaseResponse.ok(userService.editUser(user, editUserRequest));
    } catch (Exception e) {
      log.error("Register user failed: {}", e.getMessage(), e);
      return BaseResponse.error(e.getMessage());
    }
  }

  @DeleteMapping("/delete")
  public BaseResponse<Object> deleteUser(@AuthenticationPrincipal User user) {
    try {
      userService.deleteUser(user);
      return BaseResponse.ok(null);
    } catch (Exception e) {
      log.error("Delete user failed: {}", e.getMessage(), e);
      return BaseResponse.error(e.getMessage());
    }
  }
}

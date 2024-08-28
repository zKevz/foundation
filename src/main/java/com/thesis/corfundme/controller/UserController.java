package com.thesis.corfundme.controller;

import com.thesis.corfundme.model.User;
import com.thesis.corfundme.request.EditUserRequest;
import com.thesis.corfundme.request.RegisterUserRequest;
import com.thesis.corfundme.request.ValidateUserRequest;
import com.thesis.corfundme.response.BaseResponse;
import com.thesis.corfundme.response.ValidateUserResponse;
import com.thesis.corfundme.service.IUserService;
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

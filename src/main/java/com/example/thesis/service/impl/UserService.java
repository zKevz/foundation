package com.example.thesis.service.impl;

import com.example.thesis.model.Token;
import com.example.thesis.model.User;
import com.example.thesis.model.UserRole;
import com.example.thesis.repository.TokenRepository;
import com.example.thesis.repository.UserRepository;
import com.example.thesis.request.EditUserRequest;
import com.example.thesis.request.RegisterUserRequest;
import com.example.thesis.request.ValidateUserRequest;
import com.example.thesis.response.ValidateUserResponse;
import com.example.thesis.service.IJwtService;
import com.example.thesis.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class UserService implements IUserService {
  @Autowired
  private UserRepository userRepository;

  @Autowired
  private IJwtService jwtService;

  @Autowired
  private TokenRepository tokenRepository;

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private PasswordEncoder passwordEncoder;

  private boolean isValidUsername(String username) {
    if (Objects.isNull(username)) {
      return false;
    }

    username = username.trim();

    return username.length() >= 3 && username.length() <= 17;
  }

  private boolean isValidPassword(String password) {
    if (Objects.isNull(password)) {
      return false;
    }

    password = password.trim();

    if (password.length() < 6) {
      return false;
    }

    if (password.chars().noneMatch(Character::isUpperCase)) {
      return false;
    }

    return password.chars().anyMatch(x -> !Character.isAlphabetic(x) && !Character.isDigit(x));
  }

  private boolean isValidEmail(String email) {
    if (Objects.isNull(email)) {
      return false;
    }

    email = email.trim();

    int index = email.indexOf('@');
    if (index == -1) {
      return false;
    }

    int indexOfDot = email.indexOf('.', index + 1);
    return indexOfDot != -1;
  }

  private void validateEmailPassword(String email, String password) {
    if (Objects.isNull(password) || !isValidPassword(password)) {
      throw new RuntimeException(
        "Password must be at least 6 characters, contains 1 uppercase letter and 1 special character");
    }

    if (Objects.isNull(email) || !isValidEmail(email)) {
      throw new RuntimeException("Invalid email!");
    }
  }

  @Override
  @Transactional
  public ValidateUserResponse registerUser(RegisterUserRequest registerUserRequest) {
    validateUsername(registerUserRequest.getUsername());
    validateEmailPassword(registerUserRequest.getEmail(), registerUserRequest.getPassword());

    User user = userRepository.save(User
      .builder()
      .email(registerUserRequest.getEmail().trim())
      .name(registerUserRequest.getUsername().trim())
      .password(passwordEncoder.encode(registerUserRequest.getPassword().trim()))
      .role(UserRole.USER)
      .build());

    String jwtToken = jwtService.generateToken(user);
    String refreshToken = jwtService.generateRefreshToken(user);

    saveToken(user, jwtToken);

    return ValidateUserResponse.builder().accessToken(jwtToken).refreshToken(refreshToken).userId(user.getId()).build();
  }

  private void validateUsername(String username) {
    if (!isValidUsername(username)) {
      throw new RuntimeException("Username must be between 3 to 17 characters");
    }
  }

  private void saveToken(User user, String jwtToken) {
    Token token = Token.builder().user(user).token(jwtToken).expired(false).revoked(false).build();
    tokenRepository.save(token);
  }

  @Override
  @Transactional
  public ValidateUserResponse validateUser(ValidateUserRequest validateUserRequest) {
    validateEmailPassword(validateUserRequest.getEmail(), validateUserRequest.getPassword());

    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(validateUserRequest.getEmail(),
      validateUserRequest.getPassword()));

    User user = userRepository
      .findByEmail(validateUserRequest.getEmail())
      .orElseThrow(() -> new RuntimeException("Invalid email or password"));
    String jwtToken = jwtService.generateToken(user);
    String refreshToken = jwtService.generateRefreshToken(user);

    jwtService.expireOrRevokeTokenByUser(user);

    saveToken(user, jwtToken);

    return ValidateUserResponse.builder().accessToken(jwtToken).refreshToken(refreshToken).userId(user.getId()).build();
  }

  @Override
  @Transactional
  public ValidateUserResponse editUser(User user, EditUserRequest editUserRequest) {
    validateUsername(editUserRequest.getUsername());
    validateEmailPassword(editUserRequest.getEmail(), editUserRequest.getPassword());

    user.setEmail(editUserRequest.getEmail());
    user.setName(editUserRequest.getUsername());
    user.setPassword(editUserRequest.getPassword());

    String jwtToken = jwtService.generateToken(user);
    String refreshToken = jwtService.generateRefreshToken(user);

    jwtService.expireOrRevokeTokenByUser(user);

    saveToken(user, jwtToken);

    return ValidateUserResponse.builder().accessToken(jwtToken).refreshToken(refreshToken).userId(user.getId()).build();
  }

  @Override
  public User findById(Integer userId) {
    return userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found???? me suck"));
  }

  @Override
  @Transactional
  public void changeUserRole(Integer userId, UserRole userRole) {
    User user = findById(userId);
    user.setRole(userRole);
  }

  @Override
  public boolean containsRole(UserRole userRole) {
    return userRepository.existsByRole(userRole);
  }
}

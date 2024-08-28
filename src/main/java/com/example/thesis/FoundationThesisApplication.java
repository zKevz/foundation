package com.example.thesis;

import com.example.thesis.model.UserRole;
import com.example.thesis.request.CreateFoundationRequest;
import com.example.thesis.request.RegisterUserRequest;
import com.example.thesis.response.ValidateUserResponse;
import com.example.thesis.service.IFoundationService;
import com.example.thesis.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@Slf4j
@SpringBootApplication
public class FoundationThesisApplication {
  @Autowired
  private IUserService userService;

  @Autowired
  private IFoundationService foundationService;

  public static void main(String[] args) {
    SpringApplication.run(FoundationThesisApplication.class, args);
  }

  @Bean
  public CommandLineRunner preCreateData() {
    return args -> {
      if (userService.containsRole(UserRole.ADMIN)) {
        return;
      }

      ValidateUserResponse admin = userService.registerUser(RegisterUserRequest
        .builder()
        .email("admin@gmail.com")
        .password("!Aa12345")
        .username("Admin")
        .build());
      userService.changeUserRole(admin.getUserId(), UserRole.ADMIN);

      ValidateUserResponse foundation = userService.registerUser(RegisterUserRequest
        .builder()
        .email("foundation@gmail.com")
        .password("!Aa12345")
        .username("Foundation")
        .build());
      foundationService.createFoundation(foundation.getUserId(),
        CreateFoundationRequest
          .builder()
          .address("Test Foundation Addresss")
          .imageUrl("https://flow-user-images.s3.us-west-1.amazonaws.com/avatars/ZVaAm1uhaMAbZGNpuyh6m/1703447951168")
          .build());

      ValidateUserResponse user = userService.registerUser(RegisterUserRequest
        .builder()
        .email("user@gmail.com")
        .password("!Aa12345")
        .username("Johan Liebert")
        .build());

      log.info("Admin token: {}", admin.getAccessToken());
      log.info("Foundation token: {}", foundation.getAccessToken());
      log.info("User token: {}", user.getAccessToken());
    };
  }
}

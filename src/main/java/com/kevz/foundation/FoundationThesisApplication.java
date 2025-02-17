package com.kevz.foundation;

import com.kevz.foundation.config.StorageConfig;
import com.kevz.foundation.request.CreateFoundationRequest;
import com.kevz.foundation.request.RegisterUserRequest;
import com.kevz.foundation.response.ValidateUserResponse;
import com.kevz.foundation.service.IFoundationService;
import com.kevz.foundation.service.IUserService;
import com.kevz.foundation.model.UserRole;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@Slf4j
@EnableScheduling
@SpringBootApplication
@EnableConfigurationProperties(StorageConfig.class)
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

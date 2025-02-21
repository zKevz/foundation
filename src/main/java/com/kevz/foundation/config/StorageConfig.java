package com.kevz.foundation.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("storage")
@Data
public class StorageConfig {
  private String rootLocation;
}

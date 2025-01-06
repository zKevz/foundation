package com.thesis.corfundme.service.impl;

import com.thesis.corfundme.config.StorageConfig;
import com.thesis.corfundme.service.IStorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

@Service
@Slf4j
public class StorageService implements IStorageService {

  private final Path rootPath;

  @Autowired
  public StorageService(StorageConfig storageConfig) {
    if (storageConfig.getRootLocation().trim().isEmpty()) {
      throw new RuntimeException("File upload root location can not be empty.");
    }

    this.rootPath = Path.of(storageConfig.getRootLocation());

    try {
      Files.createDirectories(rootPath);
    } catch (IOException e) {
      log.error("Failed to create storage directory: ", e);
    }
  }

  @Override
  public void store(String filename, MultipartFile file) {
    try {
      if (file.isEmpty()) {
        throw new RuntimeException("Failed to store empty file");
      }

      if (Objects.isNull(file.getOriginalFilename())) {
        throw new RuntimeException("File cannot have empty name");
      }

      Path destinationFile = this.rootPath.resolve(Path.of(filename + getFileExtension(file.getOriginalFilename())));
      try (InputStream inputStream = file.getInputStream()) {
        Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
      }

      log.info("Stored {}", destinationFile);
    } catch (IOException e) {
      throw new RuntimeException("Failed to store file " + e, e);
    }
  }

  @Override
  public Resource load(String filename) {
    try {
      Path file = rootPath.resolve(filename);
      Resource resource = new UrlResource(file.toUri());
      if (resource.exists() || resource.isReadable()) {
        return resource;
      } else {
        throw new RuntimeException("Could not read file: " + filename);

      }
    } catch (MalformedURLException e) {
      throw new RuntimeException("Could not read file: " + filename, e);
    }
  }

  @Override
  public String getFileExtension(String filename) {
    int lastIndexOf = filename.lastIndexOf(".");
    if (lastIndexOf == -1) {
      return ""; // empty extension
    }
    return filename.substring(lastIndexOf);
  }
}

package com.kevz.foundation.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface IStorageService {

  void store(String filename, MultipartFile file);

  Resource load(String filename);

  String getFileExtension(String filename);

}

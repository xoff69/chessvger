package com.xoff.chessvger.service;

import org.springframework.web.multipart.MultipartFile;

public interface IUploadService {
  public int uploadPlayer(MultipartFile file);

  public int uploadPgn(MultipartFile file, long bdId);
}

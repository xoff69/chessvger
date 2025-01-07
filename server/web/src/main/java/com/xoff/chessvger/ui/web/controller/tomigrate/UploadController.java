package com.xoff.chessvger.ui.web.controller.tomigrate;

import com.xoff.chessvger.ui.service.IUploadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Slf4j
public class UploadController {
  @Autowired
  IUploadService iUploadService;

  @PostMapping("/uploadPgn")
  public ResponseEntity<String> uploadPgn(@RequestParam(name = "file") MultipartFile multipartFile,
                                          @RequestParam(name = "bdId") Long bdId,
                                          RedirectAttributes redirectAttributes) {
    iUploadService.uploadPgn(multipartFile, bdId);
    return ResponseEntity.ok("upload pgn");
  }

  @PostMapping("/uploadFidePlayer")
  public ResponseEntity<String> uploadFidePlayer(
      @RequestParam(name = "file") MultipartFile multipartFile,
      RedirectAttributes redirectAttributes) {
    iUploadService.uploadPlayer(multipartFile);
    return ResponseEntity.ok("upload player");
  }
}

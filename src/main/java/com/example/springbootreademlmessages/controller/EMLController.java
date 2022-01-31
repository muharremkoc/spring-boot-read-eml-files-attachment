package com.example.springbootreademlmessages.controller;

import com.example.springbootreademlmessages.model.FileInfo;
import com.example.springbootreademlmessages.service.EMLFileService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EMLController {

    final EMLFileService emlFileService;

    @PostMapping (value = "/eml", consumes = "multipart/form-data")
    public List<FileInfo> getBytesFilesInEMLFile(@RequestPart MultipartFile emlFile) throws IOException, MessagingException {

        return emlFileService.getBytesFilesInEMLFile(emlFile);
    }
    @PostMapping(value = "/eml/directories")
    public void getEMLFilesInDirectory() throws MessagingException, IOException {
      emlFileService.getEMLFilesInDirectory();
    }

    @PostMapping (value = "/eml/multipart", consumes = "multipart/form-data")
    public void getMultipartFileInEMLFile(@RequestPart MultipartFile emlFile) throws IOException, MessagingException {
        emlFileService.display(emlFile);
    }

}

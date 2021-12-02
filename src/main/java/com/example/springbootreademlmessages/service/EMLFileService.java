package com.example.springbootreademlmessages.service;

import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.File;
import java.io.IOException;
import java.util.List;

public interface EMLFileService {
     List<byte[]> getBytesFilesInEMLFile(MultipartFile emlFile) throws IOException, MessagingException;
     void getEMLFilesInDirectory() throws MessagingException, IOException;
}

package com.example.springbootreademlmessages.service;

import com.example.springbootreademlmessages.model.FileInfo;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.File;
import java.io.IOException;
import java.util.List;

public interface EMLFileService {
     List<FileInfo> getBytesFilesInEMLFile(MultipartFile emlFile) throws IOException, MessagingException;
     void getEMLFilesInDirectory() throws MessagingException, IOException;
     void display(MultipartFile file) throws MessagingException, IOException;


}

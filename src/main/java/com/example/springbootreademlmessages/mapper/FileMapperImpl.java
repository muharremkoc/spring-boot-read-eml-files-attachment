package com.example.springbootreademlmessages.mapper;

import com.example.springbootreademlmessages.model.FileInfo;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.internet.MimeUtility;
import java.io.IOException;
import java.net.FileNameMap;
import java.net.URLConnection;

@Slf4j
@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FileMapperImpl implements FileMapper{
    @Override
    public FileInfo bodyPartToFileInfo(BodyPart part) {
        try {
            byte[] data = part.getInputStream().readAllBytes();
            String name = part.getFileName();
            FileNameMap fileNameMap = URLConnection.getFileNameMap();
            String mimeType = fileNameMap.getContentTypeFor(name);
            return FileInfo.builder()
                    .name(MimeUtility.decodeText(part.getFileName()))
                    .type(mimeType)
                    .size(Long.valueOf(data.length))
                    .data(data)
                    .build();

        } catch (MessagingException e) {
            log.error(e.getMessage());
            return FileInfo.builder().build();
        } catch (IOException e) {
            log.error(e.getMessage());
            return FileInfo.builder().build();
        }
    }
}

package com.example.springbootreademlmessages.service;

import com.example.springbootreademlmessages.mapper.FileMapper;
import com.example.springbootreademlmessages.mapper.FileMapperImpl;
import com.example.springbootreademlmessages.model.FileInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.activation.MimetypesFileTypeMap;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.io.*;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EMLFileService {
    private static final String EML_INPUT = "C:\\Users\\P1922\\Desktop\\mailIleti";

    private final Environment environment;
    final FileMapper fileMapper;

    @Override
    public List<FileInfo> getBytesFilesInEMLFile(MultipartFile emlFile) throws IOException, MessagingException {
    return files(emlFile.getBytes());
    }
    private List<FileInfo> files(byte[] bytes) throws MessagingException, IOException {
        FileInfo fileInfo=new FileInfo();
        List<FileInfo> getInfos = new ArrayList<>();
        InputStream source = new ByteArrayInputStream(bytes);

        MimeMessage message = new MimeMessage(null, source);

        System.out.println("Subject : " + message.getSubject());
        //System.out.println("From : " + message.getFrom()[0]);
        Multipart multiPart = (Multipart) message.getContent();

        int numberOfParts = multiPart.getCount();
        for (int partCount = 0; partCount < numberOfParts; partCount++) {
            MimeBodyPart part = (MimeBodyPart) multiPart.getBodyPart(partCount);

            String[] contentTypeParts = part.getContentType().split("\\;");
            String contentType = (contentTypeParts.length>0) ? contentTypeParts[0] : part.getContentType();
            Boolean isSupportedContentType = Arrays.stream(environment.getProperty("supported.content.type", String[].class)).anyMatch(x -> Objects.equals(x, contentType));

            if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition())) {
                if ((part.getFileName() == null && !isSupportedContentType))  {
                    part.setFileName("TestFiles");
                    if (part.getContentType().contains("message/rfc822")) {
                        files(part.getInputStream().readAllBytes()).stream().forEach(fileInfo1 ->{
                            getInfos.add(fileInfo1);
                        });
                    }
                    log.error("This Attachment is Not Supported");
                }

                log.info("file name : "+part.getFileName());
                log.info("file type : "+contentType);

                byte[] data = part.getInputStream().readAllBytes();
/*
              FileInfo fileInfo=FileInfo.builder()
                        .name(MimeUtility.decodeText(part.getFileName()))
                        .type(contentType)
                        .size(Long.valueOf(data.length))
                        .data(data)
                        .build();
 */
                 fileInfo= FileInfo.builder()
                         .name(MimeUtility.decodeText(part.getFileName()))
                         .type(part.getContentType())
                         .size(Long.valueOf(data.length))
                         .data(data)
                         .build();
                getInfos.add(fileInfo);
            }
        }
        return getInfos;
    }

    @Override
    public void getEMLFilesInDirectory() throws MessagingException, IOException {
        List<byte[]> getBytes = new ArrayList<>();

        File folder = new File(EML_INPUT);
        listFilesForFolder(folder);
    }

    @Override
    public void display(MultipartFile file) throws MessagingException, IOException {
        InputStream source = new ByteArrayInputStream(file.getBytes());
        MimeMessage message = new MimeMessage(null,source);


        System.out.println("Subject : " + message.getSubject());
        Multipart multiPart = (Multipart) message.getContent();

        //int numberOfParts = multiPart.getCount();
        for (int partCount = 0; partCount < multiPart.getCount(); partCount++) {
            MimeBodyPart part = (MimeBodyPart) multiPart.getBodyPart(partCount);
            if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition())) {
                String attachment = part.getFileName();
                //part.saveFile(file);
                if (attachment==null&& part.getContentType().contains("message/rfc822")) {
                    log.info(part.getContentType());
                    if (attachment.endsWith("message/rfc822")) {
                        attachment="Test.eml";
                               part.setFileName(attachment);
                    }
                }

                System.out.println("Body : " +attachment);
                byte[] e=IOUtils.toByteArray(part.getInputStream());
                //Files.write(Path.of("./target/" + attahment),e);


                System.out.println();
            }
            System.out.println("--------------");
        }
    }

    private void listFilesForFolder(File folder) throws MessagingException, IOException {

        List<List> allBytesList=new ArrayList<>();
        for (File fileEntry : Objects.requireNonNull(folder.listFiles())) {
            if (fileEntry.isDirectory()) {
                listFilesForFolder(fileEntry);
            } else {
                if (fileEntry.getName().endsWith(".eml")){

                    InputStream source = new FileInputStream(fileEntry);
                    MimeMessage message = new MimeMessage(null, source);

                    System.out.println("Subject : " + message.getSubject());
                    //System.out.println("From : " + message.getFrom()[0]);
                    Multipart multiPart = (Multipart) message.getContent();

                    int numberOfParts = multiPart.getCount();
                    List<byte[]> getBytes = new ArrayList<>();
                    for (int partCount = 0; partCount < numberOfParts; partCount++) {
                        MimeBodyPart part = (MimeBodyPart) multiPart.getBodyPart(partCount);
                        if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition())) {
                            // byte[]  getBytes = IOUtils.toByteArray(part.getInputStream());
                            if (part.getContentType().contains("message/rfc822"))
                            System.out.println(part.getFileName());
                            getBytes.add(IOUtils.toByteArray(part.getInputStream()));

                        }
                    }
                    allBytesList.add(getBytes);
                }
            }
        }
    }

}



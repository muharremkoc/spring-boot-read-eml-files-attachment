package com.example.springbootreademlmessages.service;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
@Service
public class EmailServiceImpl implements EMLFileService {
    private static final String EML_INPUT = "Your_Folder_Path";

    @Override
    public List<byte[]> getBytesFilesInEMLFile(MultipartFile emlFile) throws IOException, MessagingException {
        List<byte[]> getBytes = new ArrayList<>();

        Properties props = System.getProperties();
        props.put("mail.host", "***@***.com"); //your receiver mail
        props.put("mail.transport.protocol", "smtp");

        Session mailSession = Session.getDefaultInstance(props, null);
        InputStream source = new ByteArrayInputStream(emlFile.getBytes());

        MimeMessage message = new MimeMessage(mailSession, source);

        System.out.println("Subject : " + message.getSubject());
        //System.out.println("From : " + message.getFrom()[0]);
        Multipart multiPart = (Multipart) message.getContent();

        int numberOfParts = multiPart.getCount();
        for (int partCount = 0; partCount < numberOfParts; partCount++) {
            MimeBodyPart part = (MimeBodyPart) multiPart.getBodyPart(partCount);
            if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition())) {
                // byte[]  getBytes = IOUtils.toByteArray(part.getInputStream());
                getBytes.add(IOUtils.toByteArray(part.getInputStream()));
                return getBytes;
            }

        }
        return null;
    }

    @Override
    public void getEMLFilesInDirectory() throws MessagingException, IOException {
        List<byte[]> getBytes = new ArrayList<>();

        File folder = new File(EML_INPUT);
        listFilesForFolder(folder);
    }

    private void listFilesForFolder(File folder) throws MessagingException, IOException {

        List<List> allBytesList=new ArrayList<>();
        for (File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                listFilesForFolder(fileEntry);
            } else {
                if (fileEntry.getName().endsWith(".eml")){
                    Properties props = System.getProperties();
                    props.put("mail.host", "***@***.com"); //your receiver mail
                    props.put("mail.transport.protocol", "smtp");

                    Session mailSession = Session.getDefaultInstance(props, null);
                    InputStream source = new FileInputStream(fileEntry);
                    MimeMessage message = new MimeMessage(mailSession, source);

                    System.out.println("Subject : " + message.getSubject());
                    //System.out.println("From : " + message.getFrom()[0]);
                    Multipart multiPart = (Multipart) message.getContent();

                    int numberOfParts = multiPart.getCount();
                    List<byte[]> getBytes = new ArrayList<>();
                    for (int partCount = 0; partCount < numberOfParts; partCount++) {
                        MimeBodyPart part = (MimeBodyPart) multiPart.getBodyPart(partCount);
                        if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition())) {
                            // byte[]  getBytes = IOUtils.toByteArray(part.getInputStream());
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



package com.example.springbootreademlmessages;

import org.apache.commons.io.IOUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;

public class ReceiverEMLFile {
    /*
        private String downloadDirectory;

        public void setSaveDirectory(String dir) {
            this.downloadDirectory = dir;
        }


        public void downloadEmailAttachments(String host, String port, String userName, String password) throws NoSuchProviderException, MessagingException, IOException {
            Properties properties = setMailServerProperties(host, port);
            Store store = setSessionStoreProperties(userName, password, properties);
            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);
            Message[] arrayMessages = inbox.getMessages();
            for (int i = 0; i < arrayMessages.length; i++) {
                Message message = arrayMessages[i];
                Address[] fromAddress = message.getFrom();
                var a=message.getFrom()[0].toString();
               if (a.equals("muharrem.koc@detaysoft.com")){
                   String from = a;
                   String subject = message.getSubject();

                   String sentDate = message.getSentDate().toString();
                   List<String> attachments = new ArrayList<String>();
                   if (message.getContentType().contains("multipart")) {
                       attachments = downloadAttachments(message);
                   }

                   System.out.println("Message #" + (i + 1) + ":");
                   System.out.println(" From: " + from);
                   System.out.println(" Subject: " + subject);
                   System.out.println(" Sent Date: " + sentDate);
                   System.out.println(" Attachments: " + attachments);
               }
            }
            inbox.close(false);
            store.close();
        }


        public List<String> downloadAttachments(Message message) throws IOException, MessagingException {
            List<String> downloadedAttachments = new ArrayList<String>();
            Multipart multiPart = (Multipart) message.getContent();
            int numberOfParts = multiPart.getCount();
            for (int partCount = 0; partCount < numberOfParts; partCount++) {
                MimeBodyPart part = (MimeBodyPart) multiPart.getBodyPart(partCount);
                if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition())) {
                    String file = part.getFileName();
                    part.saveFile(downloadDirectory + File.separator + part.getFileName());
                    downloadedAttachments.add(file);
                }
            }

            return downloadedAttachments;
        }

        public Properties setMailServerProperties(String host, String port) {
            Properties properties = new Properties();

            properties.put("mail.pop3.host", host);
            properties.put("mail.pop3.port", port);

            properties.setProperty("mail.pop3.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            properties.setProperty("mail.pop3.socketFactory.fallback", "false");
            properties.setProperty("mail.pop3.socketFactory.port", String.valueOf(port));
            return properties;
        }

        public Store setSessionStoreProperties(String userName, String password, Properties properties) throws NoSuchProviderException, MessagingException {
            Session session = Session.getDefaultInstance(properties);

            Store store = session.getStore("pop3");
            store.connect(userName, password);
            return store;
        }

     */
    public static void display(File emlFile) throws Exception {
        Properties props = System.getProperties();
   props.put("mail.host", "muharrem.koc@detaysoft.com");
        props.put("mail.transport.protocol", "smtp");

        Session mailSession = Session.getDefaultInstance(props, null);
        InputStream source = new FileInputStream(emlFile);
        MimeMessage message = new MimeMessage(mailSession, source);

        System.out.println("Subject : " + message.getSubject());
        System.out.println("From : " + message.getFrom()[0]);
        Multipart multiPart = (Multipart) message.getContent();

        //int numberOfParts = multiPart.getCount();
        for (int partCount = 0; partCount < multiPart.getCount(); partCount++) {
            MimeBodyPart part = (MimeBodyPart) multiPart.getBodyPart(partCount);
            if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition())) {
                String file = part.getFileName();
             //part.saveFile(file);
                System.out.println("Body : " +file);
                byte[] e=IOUtils.toByteArray(part.getInputStream());
                Files.write(Path.of("./target/" + file),e);


                System.out.println();
            }
            System.out.println("--------------");
        }
    }
        public static void main (String[]args) throws Exception {

    /*
        String host = "pop.gmail.com";
        String port = "995";
        String userName = "";
        String password = "";

        String saveDirectory = "C:\\Users\\P1922\\Desktop\\Attachments";

        ReceiverEMLFile receiver = new ReceiverEMLFile();
        receiver.setSaveDirectory(saveDirectory);
        try {
            receiver.downloadEmailAttachments(host, port, userName, password);
        } catch (NoSuchProviderException ex) {
            System.out.println("No provider for pop3.");
            ex.printStackTrace();
        } catch (MessagingException ex) {
            System.out.println("Could not connect to the message store");
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

     */
            //display(new File("C:\\Users\\P1922\\Desktop\\earsiv.eml"));
            display(new File("C:\\Users\\P1922\\Desktop\\earsiv.eml"));
        }


    }
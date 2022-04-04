package com.example.springbootreademlmessages;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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

public class ReceiverEMLFile {

    public void downloadEmailAttachments(String host, String port, String userName, String password) throws NoSuchProviderException, MessagingException, IOException {
        Properties properties = setMailServerProperties(host, port);
        Store store = setSessionStoreProperties(userName, password, properties);
        Folder inbox = store.getFolder("INBOX");
        inbox.open(Folder.READ_ONLY);
        Message[] arrayMessages = inbox.getMessages();
        for (int i = 0; i < 5; i++) {
            Message message = arrayMessages[i];
            Address[] fromAddress = message.getFrom();
            var a=message.getFrom()[0].toString();

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
                downloadedAttachments.add(file);
            }
        }

        return downloadedAttachments;
    }

    public Properties setMailServerProperties(String host, String port) {
        Properties properties = new Properties();

        properties.put("mail.imap.host", host);
        properties.put("mail.imap.port", port);

        properties.setProperty("mail.imap.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.setProperty("mail.imap.socketFactory.fallback", "false");
        properties.setProperty("mail.imap.socketFactory.port", String.valueOf(port));
        return properties;
    }

    public Store setSessionStoreProperties(String userName, String password, Properties properties) throws NoSuchProviderException, MessagingException {
        Session session = Session.getDefaultInstance(properties);

        Store store = session.getStore("imap");
        store.connect(userName, password);
        return store;
    }
    public static void main(String[] args) {
        String host = "imap.gmail.com";
        String port = "993";
        String userName = "earsivdas";
        String password = "Bilmem.123";


        ReceiverEMLFile receiver = new ReceiverEMLFile();
        try {
            receiver.downloadEmailAttachments(host, port, userName, password);
        } catch (NoSuchProviderException ex) {
            System.out.println("No provider for imap");
            ex.printStackTrace();
        } catch (MessagingException ex) {
            System.out.println("Could not connect to the message store");
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}


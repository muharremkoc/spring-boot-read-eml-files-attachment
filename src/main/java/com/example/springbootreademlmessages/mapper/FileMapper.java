package com.example.springbootreademlmessages.mapper;

import com.example.springbootreademlmessages.model.FileInfo;

import javax.mail.BodyPart;

public interface FileMapper {

    FileInfo bodyPartToFileInfo(BodyPart part);
}

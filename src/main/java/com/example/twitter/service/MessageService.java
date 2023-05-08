package com.example.twitter.service;

import com.example.twitter.model.Message;
import com.example.twitter.model.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

public interface MessageService {
    Message save(Message message);

    Iterable<Message> findAll();

    List<Message> findByTag(String tag);

    Message add(String text, String tag, User user, MultipartFile file);
}

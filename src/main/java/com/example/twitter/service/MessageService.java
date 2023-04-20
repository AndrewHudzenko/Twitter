package com.example.twitter.service;

import com.example.twitter.model.Message;
import java.util.List;

public interface MessageService {
    Message save(Message message);

    Iterable<Message> findAll();

    List<Message> findByTag(String tag);
}

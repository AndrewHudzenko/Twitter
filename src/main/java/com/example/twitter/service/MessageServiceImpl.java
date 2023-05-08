package com.example.twitter.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.twitter.model.Message;
import com.example.twitter.model.User;
import com.example.twitter.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class MessageServiceImpl implements MessageService {
    @Value("${aws.s3.bucket.name}")
    private String bucketName;

    private final AmazonS3 amazonS3;
    private final MessageRepository messageRepository;

    public MessageServiceImpl(AmazonS3 amazonS3, MessageRepository messageRepository) {
        this.amazonS3 = amazonS3;
        this.messageRepository = messageRepository;
    }

    @Override
    public Message save(Message message) {
        return messageRepository.save(message);
    }

    @Override
    public Iterable<Message> findAll() {
        return messageRepository.findAll();
    }

    @Override
    public List<Message> findByTag(String tag) {
        return messageRepository.findByTag(tag);
    }

    @Override
    public Message add(String text, String tag, User user, MultipartFile file) {
        Message message = Message.builder()
                .text(text)
                .tag(tag)
                .author(user)
                .build();

        if (!file.isEmpty() && !file.getOriginalFilename().isEmpty()) {
            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + file.getOriginalFilename();

            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(file.getContentType());
            metadata.setContentLength(file.getSize());
            PutObjectRequest putObjectRequest = null;
            try {
                putObjectRequest = new PutObjectRequest(bucketName, resultFilename, file.getInputStream(), metadata);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            amazonS3.putObject(putObjectRequest);

            message.setFilename(resultFilename);
        }

        return message;

//        if (!file.isEmpty() && !file.getOriginalFilename().isEmpty()) {
//            File uploadDir = new File(uploadPath);
//
//            if (!uploadDir.exists()) {
//                uploadDir.mkdir();
//            }
//            String uuidFile = UUID.randomUUID().toString();
//            String resultFilename = uuidFile + "." + file.getOriginalFilename();
//
//            file.transferTo(new File(uploadPath + "/" + resultFilename));
//
//            message.setFilename(resultFilename);
//        }
    }
}

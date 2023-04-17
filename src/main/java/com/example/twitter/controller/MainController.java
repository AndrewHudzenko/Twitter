package com.example.twitter.controller;

import com.example.twitter.model.Message;
import com.example.twitter.repository.MessageRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.Map;

@Controller
public class MainController {
    private final MessageRepository messageRepository;

    public MainController(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @GetMapping("/main")
    public String main(Map<String, Object> model) {
        Iterable<Message> messages = messageRepository.findAll();

        model.put("messages", messages);

        return "main";
    }

    @PostMapping("/addMessage")
    public String add(@RequestParam String text,
                      @RequestParam String tag,
                      Map<String, Object> model,
                      RedirectAttributes redirectAttributes) {
        Message message = Message.builder()
                .text(text)
                .tag(tag)
                .build();
        messageRepository.save(message);

        Iterable<Message> messages = messageRepository.findAll();
        model.put("messages", messages);
        redirectAttributes.addFlashAttribute("messages", messageRepository.findAll());
        return "redirect:/main";
    }

    @PostMapping("/filter")
    public String filter(@RequestParam String filter, Map<String, Object> model) {
        Iterable<Message> messages;

        if (filter != null && !filter.isEmpty()) {
             messages = messageRepository.findByTag(filter);
        } else {
            messages = messageRepository.findAll();
        }

        model.put("messages", messages);

        return "main";
    }

}
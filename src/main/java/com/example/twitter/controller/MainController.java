package com.example.twitter.controller;

import com.example.twitter.model.Message;
import com.example.twitter.model.User;
import com.example.twitter.service.MessageService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.Map;

@Controller
public class MainController {
    private final MessageService messageService;

    public MainController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/main")
    public String main(@RequestParam(required = false, defaultValue = "") String filter, Model model) {
        Iterable<Message> messages = messageService.findAll();

        if (filter != null && !filter.isEmpty()) {
            messages = messageService.findByTag(filter);
        } else {
            messages = messageService.findAll();
        }

        model.addAttribute("messages", messages);
        model.addAttribute("filter", filter);

        return "main";
    }

    @PostMapping("/addMessage")
    public String add(@AuthenticationPrincipal User user,
            @RequestParam String text,
                      @RequestParam String tag,
                      Map<String, Object> model,
                      RedirectAttributes redirectAttributes) {
        Message message = Message.builder()
                .text(text)
                .tag(tag)
                .author(user)
                .build();
        messageService.save(message);

        Iterable<Message> messages = messageService.findAll();
        model.put("messages", messages);
        redirectAttributes.addFlashAttribute("messages", messageService.findAll());
        return "redirect:/main";
    }
}
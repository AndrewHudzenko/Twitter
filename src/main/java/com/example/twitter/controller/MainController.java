package com.example.twitter.controller;

import com.example.twitter.model.Message;
import com.example.twitter.model.User;
import com.example.twitter.service.MessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Controller
public class MainController {
    private final MessageService messageService;

    public MainController(MessageService messageService) {
        this.messageService = messageService;
    }

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("/main")
    public String main(@RequestParam(required = false, defaultValue = "") String filter, Model model) {
        Iterable<Message> messages;

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
    public String add(
            @AuthenticationPrincipal User user,
            @RequestParam String text,
            @RequestParam String tag,
            @RequestParam("file") MultipartFile file,
            Map<String, Object> model,
            RedirectAttributes redirectAttributes) throws IOException {
        Message message = Message.builder()
                .text(text)
                .tag(tag)
                .author(user)
                .build();

        if (file != null && !file.getOriginalFilename().isEmpty()) {
            File uploadDir = new File(uploadPath);

            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + file.getOriginalFilename();

            file.transferTo(new File(uploadPath + "/" + resultFilename));

            message.setFilename(resultFilename);
        }
        messageService.save(message);

        Iterable<Message> messages = messageService.findAll();
        model.put("messages", messages);
        redirectAttributes.addFlashAttribute("messages", messageService.findAll());
        return "redirect:/main";
    }
}